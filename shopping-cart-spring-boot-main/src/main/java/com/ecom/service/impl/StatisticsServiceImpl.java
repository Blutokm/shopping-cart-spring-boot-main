package com.ecom.service.impl;

import com.ecom.repository.ProductOrderRepository;
import com.ecom.repository.ProductRepository;
import com.ecom.repository.UserRepository;
import com.ecom.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private ProductOrderRepository orderRepository;


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Map<String, Object> getDashboardData() {
        Map<String, Object> data = new HashMap<>();

        Double revenue = Optional.ofNullable(orderRepository.getTotalRevenue()).orElse(0.0);

        Long totalOrders = Optional.ofNullable(orderRepository.getTotalOrders()).orElse(0L);
        Long totalProducts = productRepository.count();
        Long totalUsers = userRepository.count();

        data.put("revenue", revenue);
        data.put("orders", totalOrders);
        data.put("products", totalProducts);
        data.put("users", totalUsers);

        List<Object[]> monthlyRevenue = orderRepository.getRevenueByMonth();
        Map<Integer, Double> revenueByMonth = new LinkedHashMap<>();
        for (Object[] obj : monthlyRevenue) {
            Integer month = ((Number) obj[0]).intValue();
            Double total = ((Number) obj[1]).doubleValue();
            revenueByMonth.put(month, total);
        }
        data.put("revenueByMonth", revenueByMonth);

        return data;
        
    }
}

