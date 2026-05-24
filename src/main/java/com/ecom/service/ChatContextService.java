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
}