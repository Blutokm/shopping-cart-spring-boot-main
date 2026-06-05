package com.ecom.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.model.Product;
import com.ecom.model.UserDtls;

public interface ProductService {

	public Product saveProduct(Product product);

	public List<Product> getAllProducts();

	public Boolean deleteProduct(Integer id);

	public Product getProductById(Integer id);

	public Product updateProduct(Product product, MultipartFile image, MultipartFile[] extraImages);

	public List<Product> getAllActiveProducts(String category);

	public List<Product> searchProduct(String ch);

	public Page<Product> getAllProductsPagination(Integer pageNo, Integer pageSize, UserDtls user);

	public Page<Product> searchProductPagination(Integer pageNo, Integer pageSize, String ch, UserDtls user);

	public Page<Product> getAllProductsPagination(Integer pageNo, Integer pageSize);

	public Page<Product> searchActiveProductPagination(Integer pageNo, Integer pageSize, String category, String ch);

}
