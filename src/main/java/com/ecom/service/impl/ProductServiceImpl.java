package com.ecom.service.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.model.Product;
import com.ecom.model.ProductVariant;
import com.ecom.model.UserDtls;
import com.ecom.repository.ProductRepository;
import com.ecom.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Product saveProduct(Product product) {

		if (product.getVariants() != null && !product.getVariants().isEmpty()) {
			for (ProductVariant variant : product.getVariants()) {
				variant.setProduct(product);
			}
		}

		if (product.getDiscount() > 0 && product.getPrice() != null) {
			Double discountAmount = product.getPrice() * (product.getDiscount() / 100.0);
			Double discountPrice = product.getPrice() - discountAmount;
			product.setDiscountPrice(discountPrice);
		}

		return productRepository.save(product);
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Page<Product> getAllProductsPagination(Integer pageNo, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		return productRepository.findAll(pageable);
	}

	@Override
	public Boolean deleteProduct(Integer id) {
		try {
			Product product = productRepository.findById(id).orElse(null);

			if (!ObjectUtils.isEmpty(product)) {
				productRepository.delete(product);
				return true;
			}
		} catch (Exception e) {
			System.err.println("❌ Lỗi khi xóa sản phẩm ID " + id + ": " + e.getMessage());
			return false;
		}
		return false;
	}

	@Override
	public Product getProductById(Integer id) {
		Product product = productRepository.findById(id).orElse(null);
		return product;
	}

	@Override
	public Product updateProduct(Product product, MultipartFile image, MultipartFile[] extraImages) {

		Product dbProduct = getProductById(product.getId());

		String imageName = image.isEmpty() ? dbProduct.getImage() : image.getOriginalFilename();

		dbProduct.setTitle(product.getTitle());
		dbProduct.setDescription(product.getDescription());
		dbProduct.setCategory(product.getCategory());
		dbProduct.setPrice(product.getPrice());
		dbProduct.setImage(imageName);
		dbProduct.setIsActive(product.getIsActive());
		dbProduct.setDiscount(product.getDiscount());

		if (product.getVariants() != null) {
			dbProduct.getVariants().clear();
			for (ProductVariant variant : product.getVariants()) {
				variant.setProduct(dbProduct);
				dbProduct.getVariants().add(variant);
			}
		}

		Double discountAmount = product.getPrice() * (product.getDiscount() / 100.0);
		Double discountPrice = product.getPrice() - discountAmount;
		dbProduct.setDiscountPrice(discountPrice);

		if (extraImages != null && extraImages.length > 0 && !extraImages[0].isEmpty()) {
			if (dbProduct.getExtraImages() != null) {
				dbProduct.getExtraImages().clear();
			} else {
				dbProduct.setExtraImages(new java.util.ArrayList<>());
			}

			for (MultipartFile extraFile : extraImages) {
				if (!extraFile.isEmpty()) {
					com.ecom.model.ProductImage img = new com.ecom.model.ProductImage();
					img.setImageName(extraFile.getOriginalFilename());
					img.setProduct(dbProduct);
					dbProduct.getExtraImages().add(img);
				}
			}
		}

		Product updateProduct = productRepository.save(dbProduct);

		if (!ObjectUtils.isEmpty(updateProduct)) {
			try {
				File saveFileDir = new File("uploads/product_img/" + product.getCategory());

				if (!saveFileDir.exists()) {
					saveFileDir.mkdirs();
				}

				if (!image.isEmpty()) {
					Path path = Paths.get(saveFileDir.getAbsolutePath() + File.separator + image.getOriginalFilename());
					Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				}

				if (extraImages != null && extraImages.length > 0 && !extraImages[0].isEmpty()) {
					for (MultipartFile extraFile : extraImages) {
						if (!extraFile.isEmpty()) {
							Path extraPath = Paths.get(
									saveFileDir.getAbsolutePath() + File.separator + extraFile.getOriginalFilename());
							Files.copy(extraFile.getInputStream(), extraPath, StandardCopyOption.REPLACE_EXISTING);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return updateProduct;
		}
		return null;
	}

	@Override
	public List<Product> getAllActiveProducts(String category) {
		List<Product> products = null;
		if (ObjectUtils.isEmpty(category)) {
			products = productRepository.findByIsActiveTrue();
		} else {
			products = productRepository.findByCategory(category);
		}
		return products;
	}

	@Override
	public List<Product> searchProduct(String ch) {
		return productRepository.findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(ch, ch);
	}

	@Override
	public Page<Product> getAllProductsPagination(Integer pageNo, Integer pageSize, UserDtls user) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);

		if ("ROLE_MANAGER".equals(user.getRole())) {
			return productRepository.findByCreatedBy(user, pageable);
		}
		return productRepository.findAll(pageable);
	}

	@Override
	public Page<Product> searchProductPagination(Integer pageNo, Integer pageSize, String ch, UserDtls user) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);

		if ("ROLE_MANAGER".equals(user.getRole())) {
			return productRepository.findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCaseAndCreatedBy(ch, ch,
					user, pageable);
		}
		return productRepository.findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(ch, ch, pageable);
	}

	@Override
	public Page<Product> searchActiveProductPagination(Integer pageNo, Integer pageSize, String category, String ch) {
		Page<Product> pageProduct = null;
		Pageable pageable = PageRequest.of(pageNo, pageSize);

		pageProduct = productRepository.findByIsActiveTrueAndTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(ch,
				ch, pageable);

		return pageProduct;
	}

}