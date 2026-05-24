package com.ecom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.model.Product;
import com.ecom.repository.ProductRepository;

@Service
public class ChatContextService {

    @Autowired
    private ProductRepository productRepository;

    public String getProductListText() {
        StringBuilder sb = new StringBuilder();
        try {
            List<Product> products = productRepository.findByIsActiveTrue(); 
            
            if (products.isEmpty()) {
                return "Hiện tại cửa hàng chưa có sản phẩm nào.";
            }

            for (Product p : products) {
                sb.append("- ").append(p.getTitle())
                  .append(" | Giá: ").append(String.format("%,.0f", p.getDiscountPrice()))
                  .append(" VND\n");
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy dữ liệu cho Chatbot: " + e.getMessage());
            return "";
        }
        
        return sb.toString();
    }
    
    public String getProductDetailText(Long productId) { 
        try {
            if (productId == null) {
                return null;
            }
            
            Product product = productRepository.findById(productId.intValue()).orElse(null);
            
            if (product == null) {
                return null;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("- Tên: ").append(product.getTitle()).append("\n");
            sb.append("- Giá gốc: ").append(String.format("%,.0f", product.getPrice())).append(" VND\n");
            sb.append("- Giá khuyến mãi: ").append(String.format("%,.0f", product.getDiscountPrice())).append(" VND\n");
            
            if (product.getDescription() != null && !product.getDescription().isBlank()) {
                sb.append("- Mô tả: ").append(product.getDescription()).append("\n");
            }
            
            return sb.toString();
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy chi tiết sản phẩm cho Chatbot: " + e.getMessage());
            return null;
        }
    }
}