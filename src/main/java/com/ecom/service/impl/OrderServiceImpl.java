package com.ecom.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecom.model.Cart;
import com.ecom.model.OrderAddress;
import com.ecom.model.OrderRequest;
import com.ecom.model.ProductOrder;
import com.ecom.model.ProductVariant;
import com.ecom.model.UserDtls;
import com.ecom.repository.CartRepository;
import com.ecom.repository.ProductOrderRepository;
import com.ecom.repository.ProductVariantRepository;
import com.ecom.service.OrderService;
import com.ecom.util.CommonUtil;
import com.ecom.util.OrderStatus;

@Service
public class OrderServiceImpl implements OrderService {

	private static final Logger logger = Logger.getLogger(OrderServiceImpl.class.getName());

	@Autowired
	private ProductOrderRepository orderRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ProductVariantRepository variantRepository;

	@Autowired
	private CommonUtil commonUtil;

	@Override
	public String saveOrder(Integer userid, OrderRequest orderRequest) throws Exception {

		List<Cart> carts = cartRepository.findByUserId(userid);

		if (carts == null || carts.isEmpty()) {
			throw new Exception("Giỏ hàng trống. Vui lòng thêm sản phẩm trước khi đặt hàng.");
		}

		for (Cart cart : carts) {
			if (!validateAndCheckStock(cart)) {
				throw new Exception("Số lượng sản phẩm '" + cart.getProduct().getTitle() 
					+ "' (Size: " + cart.getSize() + ", Color: " + cart.getColor() 
					+ ") không đủ. Hiện có: " + getAvailableStock(cart));
			}
		}

		String commonOrderId = UUID.randomUUID().toString();

		for (Cart cart : carts) {
			ProductOrder order = new ProductOrder();
			order.setOrderId(commonOrderId);
			order.setOrderDate(LocalDate.now());
			order.setProduct(cart.getProduct());
			order.setPrice(cart.getProduct().getDiscountPrice());
			order.setQuantity(cart.getQuantity());
			order.setUser(cart.getUser());
			order.setStatus(OrderStatus.IN_PROGRESS.getName());
			order.setPaymentType(orderRequest.getPaymentType());
			order.setColor(cart.getColor());
			order.setSize(cart.getSize());

			updateProductVariantStock(cart);

			OrderAddress address = new OrderAddress();
			address.setFirstName(orderRequest.getFirstName());
			address.setLastName(orderRequest.getLastName());
			address.setEmail(orderRequest.getEmail());
			address.setMobileNo(orderRequest.getMobileNo());
			address.setAddress(orderRequest.getAddress());
			address.setCity(orderRequest.getCity());
			address.setState(orderRequest.getState());
			address.setPincode(orderRequest.getPincode());

			order.setOrderAddress(address);

			ProductOrder saveOrder = orderRepository.save(order);
			commonUtil.sendMailForProductOrder(saveOrder, "success");
		}

		/*
		 * if (!carts.isEmpty()) { resetCart(carts.get(0).getUser()); }
		 */
		return commonOrderId;
	}

	private boolean validateAndCheckStock(Cart cart) {
		if (cart.getProduct().getVariants() == null || cart.getProduct().getVariants().isEmpty()) {
			return false;
		}

		for (ProductVariant variant : cart.getProduct().getVariants()) {
			if (variant.getColor().equals(cart.getColor()) && variant.getSize().equals(cart.getSize())) {
				return variant.getStock() >= cart.getQuantity();
			}
		}
		return false;
	}

	private Integer getAvailableStock(Cart cart) {
		if (cart.getProduct().getVariants() == null) {
			return 0;
		}

		for (ProductVariant variant : cart.getProduct().getVariants()) {
			if (variant.getColor().equals(cart.getColor()) && variant.getSize().equals(cart.getSize())) {
				return variant.getStock();
			}
		}
		return 0;
	}

	private void updateProductVariantStock(Cart cart) {
		if (cart.getProduct().getVariants() == null) {
			return;
		}

		for (ProductVariant variant : cart.getProduct().getVariants()) {
			if (variant.getColor().equals(cart.getColor()) && variant.getSize().equals(cart.getSize())) {
				int newStock = variant.getStock() - cart.getQuantity();
				variant.setStock(newStock < 0 ? 0 : newStock);
				variantRepository.save(variant);
				break;
			}
		}
	}

	private void resetCart(UserDtls user) {
		cartRepository.deleteByUser(user);
	}

	@Override
	public List<ProductOrder> getOrdersByUser(Integer userId) {
		return orderRepository.findByUserId(userId);
	}

	@Override
	public ProductOrder updateOrderStatus(Integer id, String status) {
		Optional<ProductOrder> findById = orderRepository.findById(id);
		if (findById.isPresent()) {
			ProductOrder productOrder = findById.get();
			productOrder.setStatus(status);
			return orderRepository.save(productOrder);
		}
		return null;
	}

	@Override
	public List<ProductOrder> getAllOrders() {
		return orderRepository.findAll();
	}

	@Override
	public Page<ProductOrder> getAllOrdersPagination(Integer pageNo, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		return orderRepository.findAll(pageable);
	}

	@Override
	public ProductOrder getOrdersByOrderId(String orderId) {
		return orderRepository.findByOrderId(orderId);
	}

}