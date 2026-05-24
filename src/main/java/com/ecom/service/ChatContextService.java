package com.ecom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.repository.ProductRepository;

import java.util.List;

@Service
public class ChatContextService {
	
    @Autowired
    private ProductRepository productRepository; 

    public String getProductList() {
        try {
            List<?> products = productRepository.findAll();
            if (products.isEmpty()) return "";

            StringBuilder sb = new StringBuilder();
            for (Object obj : products) {
            }
            return sb.toString();
        } catch (Exception e) {
            System.err.println("[ChatContextService] Cannot load products: " + e.getMessage());
            return "";
        }
    }

    public String getProductDetail(Long productId) {
        try {
            return productRepository.findById(productId).map(p -> {
                return "";
            }).orElse("");
        } catch (Exception e) {
            return "";
        }
    }

}
