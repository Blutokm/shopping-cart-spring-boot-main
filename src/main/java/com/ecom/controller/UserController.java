package com.ecom.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.model.Cart;
import com.ecom.model.Category;
import com.ecom.model.OrderRequest;
import com.ecom.model.ProductOrder;
import com.ecom.model.UserDtls;
import com.ecom.service.CartService;
import com.ecom.service.CategoryService;
import com.ecom.service.OrderService;
import com.ecom.service.UserService;
import com.ecom.util.CommonUtil;
import com.ecom.util.OrderStatus;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CartService cartService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/")
	public String home() {
		return "user/home";
	}

	@ModelAttribute
	public void getUserDetails(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			UserDtls userDtls = userService.getUserByEmail(email);
			m.addAttribute("user", userDtls);
			Integer countCart = cartService.getCountCart(userDtls.getId());
			m.addAttribute("countCart", countCart);

			boolean hasPending = false;
			List<ProductOrder> orders = orderService.getOrdersByUser(userDtls.getId());
			for (ProductOrder order : orders) {
				if (order.getStatus() == null || order.getStatus().equalsIgnoreCase("Pending")
						|| order.getStatus().equalsIgnoreCase("Chờ xác nhận")
						|| order.getStatus().equalsIgnoreCase("Đang xử lý")) {
					hasPending = true;
					break;
				}
			}
			m.addAttribute("hasPendingOrders", hasPending);
			// ----------------------------------------------
		}

		List<Category> allActiveCategory = categoryService.getAllActiveCategory();
		m.addAttribute("categorys", allActiveCategory);
	}

	@PostMapping("/addCart")
	public String addToCart(@RequestParam Integer pid, @RequestParam Integer uid, @RequestParam Integer variantId,
			HttpSession session) {

		Cart saveCart = cartService.saveCart(pid, uid, variantId);

		if (ObjectUtils.isEmpty(saveCart)) {
			session.setAttribute("errorMsg", "Thêm Vào Giỏ Hàng Thất Bại");
		} else {
			session.setAttribute("succMsg", "Thêm Vào Giỏ Hàng Thành Công");
		}
		return "redirect:/product/" + pid;
	}

	@GetMapping("/cart")
	public String loadCartPage(Principal p, Model m) {

		UserDtls user = getLoggedInUserDetails(p);
		List<Cart> carts = cartService.getCartsByUser(user.getId());
		m.addAttribute("carts", carts);
		if (carts.size() > 0) {
			Double totalOrderPrice = carts.get(carts.size() - 1).getTotalOrderPrice();
			m.addAttribute("totalOrderPrice", totalOrderPrice);
		}
		return "/user/cart";
	}

	@GetMapping("/cartQuantityUpdate")
	public String updateCartQuantity(@RequestParam String sy, @RequestParam Integer cid) {
		cartService.updateQuantity(sy, cid);
		return "redirect:/user/cart";
	}

	@GetMapping("/deleteCartItem") 
	public String deleteCartItem(@RequestParam Integer cid, HttpSession session) {
		cartService.deleteCartItem(cid);

		session.setAttribute("succMsg", "Đã xóa sản phẩm khỏi giỏ hàng!");
		return "redirect:/user/cart";
	}

	@GetMapping("/clearCart")
	public String clearCart(Principal p, HttpSession session) {
		if (p != null) {
			String email = p.getName();
			UserDtls user = userService.getUserByEmail(email);

			cartService.clearCartByUser(user.getId());

			session.setAttribute("succMsg", "Đã dọn sạch giỏ hàng!");
		}
		return "redirect:/user/cart";
	}

	private UserDtls getLoggedInUserDetails(Principal p) {
		String email = p.getName();
		UserDtls userDtls = userService.getUserByEmail(email);
		return userDtls;
	}

	@GetMapping("/orders")
	public String orderPage(Principal p, Model m) {
		UserDtls user = getLoggedInUserDetails(p);
		List<Cart> carts = cartService.getCartsByUser(user.getId());
		m.addAttribute("carts", carts);
		if (carts.size() > 0) {
			Double orderPrice = carts.get(carts.size() - 1).getTotalOrderPrice();
			Double totalOrderPrice = carts.get(carts.size() - 1).getTotalOrderPrice() + 250 + 100;
			m.addAttribute("orderPrice", orderPrice);
			m.addAttribute("totalOrderPrice", totalOrderPrice);
		}
		return "/user/order";
	}

	@PostMapping("/save-order")
	public Object saveOrder(@ModelAttribute OrderRequest request,
			@RequestParam(name = "paymentType", required = false) String paymentType,
			@RequestParam(name = "amount", required = false) Double amount, Principal p,
			HttpServletRequest servletRequest, Model model) throws Exception {

		UserDtls user = getLoggedInUserDetails(p);
		List<Cart> carts = cartService.getCartsByUser(user.getId());

		String orderId = "HD" + System.currentTimeMillis();

		double total = 0;
		if (!carts.isEmpty()) {
			total = carts.get(carts.size() - 1).getTotalOrderPrice() + 250 + 100;
		}

		orderService.saveOrder(user.getId(), request);

		model.addAttribute("orderId", orderId);
		model.addAttribute("user", user);
		model.addAttribute("paymentType",
				"VNPAY".equalsIgnoreCase(paymentType) ? "Thanh toán VNPay" : "Thanh toán khi nhận hàng");
		model.addAttribute("totalOrderPrice", total);
		model.addAttribute("carts", carts);

		boolean isAjax = "XMLHttpRequest".equalsIgnoreCase(servletRequest.getHeader("X-Requested-With"));

		if ("VNPAY".equalsIgnoreCase(paymentType)) {
			if (amount == null || amount <= 0) {
				if (isAjax) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid_amount");
				} else {
					return "redirect:/user/order?error=invalid_amount";
				}
			}

			String paymentUrl = PaymentController.buildVnPayUrl(servletRequest, amount.longValue());
			if (isAjax) {
				return ResponseEntity.ok(paymentUrl);
			} else {
				return "redirect:" + paymentUrl;
			}
		} else {
			cartService.clearCartByUser(user.getId());
			model.addAttribute("countCart", 0);
		}

		return "user/success";
	}

	@GetMapping("/success")
	public String loadSuccess(Principal p, Model model) {
		UserDtls user = getLoggedInUserDetails(p);
		List<Cart> carts = cartService.getCartsByUser(user.getId());

		double total = 0;
		if (!carts.isEmpty()) {
			total = carts.get(carts.size() - 1).getTotalOrderPrice() + 250 + 100;
		}

		model.addAttribute("orderId", "HD" + System.currentTimeMillis());
		model.addAttribute("user", user);
		model.addAttribute("paymentType", "Thanh toán VNPay / Khi nhận hàng");
		model.addAttribute("totalOrderPrice", total);
		model.addAttribute("carts", carts);

		cartService.clearCartByUser(user.getId());

		model.addAttribute("countCart", 0);

		return "user/success";
	}

	@GetMapping("/update-status")
	public String updateOrderStatus(@RequestParam(required = false) Integer id,
			@RequestParam(required = false) String orderId, @RequestParam Integer st, Principal principal,
			HttpSession session) {

		OrderStatus[] values = OrderStatus.values();
		String status = null;

		for (OrderStatus orderSt : values) {
			if (orderSt.getId().equals(st)) {
				status = orderSt.getName();
			}
		}

		boolean isUpdated = false;

		if (orderId != null && principal != null) {
			UserDtls user = getLoggedInUserDetails(principal);
			List<ProductOrder> allUserOrders = orderService.getOrdersByUser(user.getId());
			for (ProductOrder o : allUserOrders) {
				if (o.getOrderId().equals(orderId)) {
					ProductOrder updateOrder = orderService.updateOrderStatus(o.getId(), status);
					if (updateOrder != null) {
						isUpdated = true;
						try {
							commonUtil.sendMailForProductOrder(updateOrder, status);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} else if (id != null) {
			ProductOrder updateOrder = orderService.updateOrderStatus(id, status);
			if (updateOrder != null) {
				isUpdated = true;
				try {
					commonUtil.sendMailForProductOrder(updateOrder, status);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		if (isUpdated) {
			session.setAttribute("succMsg", "Cập Nhật Trạng Thái Thành Công");
		} else {
			session.setAttribute("errorMsg", "Cập Nhật Trạng Thái Thất Bại");
		}
		return "redirect:/user/user-orders";
	}

	@GetMapping("/profile")
	public String profile() {
		return "/user/profile";
	}

	@PostMapping("/update-profile")
	public String updateProfile(@ModelAttribute UserDtls user, @RequestParam MultipartFile img, HttpSession session) {
		UserDtls updateUserProfile = userService.updateUserProfile(user, img);
		if (ObjectUtils.isEmpty(updateUserProfile)) {
			session.setAttribute("errorMsg", "Hồ Sơ Chưa Được Cập Nhật");
		} else {
			session.setAttribute("succMsg", "Hồ Sơ Cập Nhật Thành Công");
		}
		return "redirect:/user/profile";
	}

	@PostMapping("/change-password")
	public String changePassword(@RequestParam String newPassword, @RequestParam String currentPassword, Principal p,
			HttpSession session) {
		UserDtls loggedInUserDetails = getLoggedInUserDetails(p);

		boolean matches = passwordEncoder.matches(currentPassword, loggedInUserDetails.getPassword());

		if (matches) {
			String encodePassword = passwordEncoder.encode(newPassword);
			loggedInUserDetails.setPassword(encodePassword);
			UserDtls updateUser = userService.updateUser(loggedInUserDetails);
			if (ObjectUtils.isEmpty(updateUser)) {
				session.setAttribute("errorMsg", "Mật Khẩu Cập Nhật Thất Bại");
			} else {
				session.setAttribute("succMsg", "Mật Khẩu Cập Nhật Thành Công");
			}
		} else {
			session.setAttribute("errorMsg", "Mật Khẩu Cập Nhật Thất Bại");
		}

		return "redirect:/user/profile";
	}

	@GetMapping("/order")
	public String orderPage(@RequestParam(value = "error", required = false) String error, Model model, Principal p) {
		UserDtls user = getLoggedInUserDetails(p);
		List<Cart> carts = cartService.getCartsByUser(user.getId());
		model.addAttribute("carts", carts);
		if (!carts.isEmpty()) {
			Double orderPrice = carts.get(carts.size() - 1).getTotalOrderPrice();
			Double totalOrderPrice = orderPrice + 250 + 100;
			model.addAttribute("orderPrice", orderPrice);
			model.addAttribute("totalOrderPrice", totalOrderPrice);
		}

		if ("payment".equals(error)) {
			model.addAttribute("errorMessage",
					"❌ Thanh toán VNPay thất bại. Vui lòng thử lại hoặc chọn phương thức khác.");
		}

		return "user/order";
	}

	@GetMapping("/user-orders")
	public String userOrders(Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/signin";
		}

		UserDtls user = getLoggedInUserDetails(principal);

		List<ProductOrder> orders = new ArrayList<>(orderService.getOrdersByUser(user.getId()));

		orders.sort((o1, o2) -> o2.getId().compareTo(o1.getId()));

		Map<String, List<ProductOrder>> groupedOrders = new LinkedHashMap<>();
		for (ProductOrder o : orders) {
			groupedOrders.computeIfAbsent(o.getOrderId(), k -> new ArrayList<>()).add(o);
		}

		List<Map<String, Object>> summaryOrders = new ArrayList<>();
		for (Map.Entry<String, List<ProductOrder>> entry : groupedOrders.entrySet()) {
			String orderId = entry.getKey();
			List<ProductOrder> items = entry.getValue();

			ProductOrder representative = items.get(0);

			double totalAmount = items.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();

			int totalQuantity = items.stream().mapToInt(ProductOrder::getQuantity).sum();

			Map<String, Object> orderMap = new HashMap<>();
			orderMap.put("orderId", orderId);
			orderMap.put("orderDate", representative.getOrderDate());
			orderMap.put("status", representative.getStatus());
			orderMap.put("totalAmount", totalAmount);
			orderMap.put("totalQuantity", totalQuantity);

			summaryOrders.add(orderMap);
		}

		model.addAttribute("orders", summaryOrders);
		model.addAttribute("user", user);

		return "user/my_orders";
	}

	@GetMapping("/view-order")
	public String viewOrderDetails(@RequestParam String orderId, Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/signin";
		}

		UserDtls user = getLoggedInUserDetails(principal);
		List<ProductOrder> allUserOrders = orderService.getOrdersByUser(user.getId());

		List<ProductOrder> orderDetails = allUserOrders.stream().filter(o -> o.getOrderId().equals(orderId)).toList();

		if (orderDetails.isEmpty()) {
			return "redirect:/user/user-orders";
		}

		double totalAmount = orderDetails.stream().mapToDouble(o -> o.getPrice() * o.getQuantity()).sum();

		model.addAttribute("orderDetails", orderDetails);
		model.addAttribute("orderInfo", orderDetails.get(0));
		model.addAttribute("totalAmount", totalAmount);

		return "user/view_order";
	}

}
