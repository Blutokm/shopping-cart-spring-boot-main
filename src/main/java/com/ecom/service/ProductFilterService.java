package com.ecom.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ecom.dto.ProductFilterDTO;
import com.ecom.model.Product;
import com.ecom.repository.ProductRepository;

import jakarta.persistence.criteria.Predicate;

@Service
public class ProductFilterService {

	@Autowired
	private ProductRepository productRepository;

	public Page<Product> applyFilter(ProductFilterDTO filter) {

		if (filter == null) {
			filter = new ProductFilterDTO();
		}

		Double minPrice = filter.getMinPrice() != null ? filter.getMinPrice() : 0.0;
		Double maxPrice = filter.getMaxPrice() != null && filter.getMaxPrice() > 0 ? filter.getMaxPrice()
				: Double.MAX_VALUE;
		String category = filter.getCategory() != null ? filter.getCategory().trim() : "";
		Boolean hasDiscount = filter.getHasDiscount() != null ? filter.getHasDiscount() : false;
		String keyword = filter.getKeyword() != null ? filter.getKeyword().trim() : "";
		Integer pageNo = filter.getPageNo() != null ? filter.getPageNo() : 0;
		String sort = filter.getSort() != null ? filter.getSort() : "";

		Sort sortObject = createSort(sort);

		Pageable pageable = PageRequest.of(pageNo, 12, sortObject);

		Specification<Product> spec = (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<>();

			predicates.add(cb.isTrue(root.get("isActive")));

			predicates.add(cb.between(root.get("discountPrice"), minPrice, maxPrice));

			if (!keyword.isEmpty()) {
				String likeKeyword = "%" + keyword.toLowerCase() + "%";
				Predicate titleMatch = cb.like(cb.lower(root.get("title")), likeKeyword);
				Predicate categoryMatch = cb.like(cb.lower(root.get("category")), likeKeyword);

				predicates.add(cb.or(titleMatch, categoryMatch));
			}

			if (!category.isEmpty()) {
				predicates.add(cb.equal(root.get("category"), category));
			}

			if (hasDiscount) {

				predicates.add(cb.greaterThan(root.get("discount"), 0));
			}

			return cb.and(predicates.toArray(new Predicate[0]));
		};

		return productRepository.findAll(spec, pageable);
	}

	private Sort createSort(String sort) {
		Sort.Order order;
		switch (sort) {
		case "price-asc":
			order = new Sort.Order(Sort.Direction.ASC, "discountPrice");
			break;
		case "price-desc":
			order = new Sort.Order(Sort.Direction.DESC, "discountPrice");
			break;
		case "discount-high":
			order = new Sort.Order(Sort.Direction.DESC, "discount");
			break;
		case "newest":
			order = new Sort.Order(Sort.Direction.DESC, "id");
			break;
		case "oldest":
			order = new Sort.Order(Sort.Direction.ASC, "id");
			break;
		default:
			order = new Sort.Order(Sort.Direction.DESC, "id");
		}
		return Sort.by(order);
	}

	public Double getMinPrice() {
		return 0.0;
	}

	public Double getMaxPrice() {
		return 10000000.0;
	}
}