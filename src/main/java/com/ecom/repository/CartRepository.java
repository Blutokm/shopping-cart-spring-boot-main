package com.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ecom.model.Cart;
import com.ecom.model.UserDtls;

import jakarta.transaction.Transactional;

public interface CartRepository extends JpaRepository<Cart, Integer> {

	public Cart findByProductIdAndUserIdAndColorAndSize(Integer productId, Integer userId, String color, String size);

	public Integer countByUserId(Integer userId);

	public List<Cart> findByUserId(Integer userId);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM Cart c WHERE c.user.id = :userId")
	void deleteByUserId(Integer userId);

	@Transactional
	void deleteByUser(UserDtls user);

}