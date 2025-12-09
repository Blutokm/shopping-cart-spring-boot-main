package com.ecom.repository;

import com.ecom.model.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<ProductOrder, Integer> {

    @Query("SELECT COALESCE(SUM(o.price * o.quantity), 0) FROM ProductOrder o WHERE o.status = 'Đã giao hàng'")
    Double getTotalRevenue();

    @Query("SELECT MONTH(o.orderDate), COALESCE(SUM(o.price * o.quantity), 0) " +
           "FROM ProductOrder o WHERE o.status = 'Đã giao hàng' " +
           "GROUP BY MONTH(o.orderDate) ORDER BY MONTH(o.orderDate)")
    List<Object[]> getRevenueByMonth();

    @Query("SELECT COUNT(o.id) FROM ProductOrder o")
    Long getTotalOrders();
}
