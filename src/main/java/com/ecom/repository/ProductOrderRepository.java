package com.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecom.model.ProductOrder;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Integer> {

    List<ProductOrder> findByUserId(Integer userId);

    ProductOrder findByOrderId(String orderId);

    @Query("SELECT COALESCE(SUM(o.price * o.quantity), 0) FROM ProductOrder o")
    Double getTotalRevenue();

    @Query("SELECT COUNT(o) FROM ProductOrder o")
    Long getTotalOrders();

    @Query("SELECT MONTH(o.orderDate) AS month, SUM(o.price * o.quantity) AS revenue "
         + "FROM ProductOrder o GROUP BY MONTH(o.orderDate) ORDER BY month ASC")
    List<Object[]> getRevenueByMonth();
    
    @Query("SELECT o.product.title, SUM(o.quantity) as totalSold " +
    	       "FROM ProductOrder o " +
    	       "GROUP BY o.product.title " +
    	       "ORDER BY totalSold DESC")
    	List<Object[]> getTopSellingProducts();

}
