package com.ecom.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ecom.model.ProductVariant;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Integer> {
    
    List<ProductVariant> findByProductId(Integer productId);
    
}