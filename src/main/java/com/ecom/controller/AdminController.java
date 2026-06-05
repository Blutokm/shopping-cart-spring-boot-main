package com.ecom.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecom.model.Category;
import com.ecom.model.Product;
import com.ecom.model.ProductImage;
import com.ecom.model.ProductOrder;
import com.ecom.model.ProductVariant;
import com.ecom.model.UserDtls;
import com.ecom.repository.ProductRepository;
import com.ecom.service.CartService;
import com.ecom.service.CategoryService;
import com.ecom.service.OrderService;
import com.ecom.service.ProductService;
import com.ecom.service.StatisticsService;
import com.ecom.service.UserService;
import com.ecom.util.CommonUtil;
import com.ecom.util.OrderStatus;
import java.util.stream.Collectors;
import com.ecom.model.ProductImage;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;

	@Autowired
	private CartService cartService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ProductRepository productRepository;

	@ModelAttribute
	public void getUserDetails(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			UserDtls userDtls = userService.getUserByEmail(email);
			m.addAttribute("user", userDtls);
			Integer countCart = cartService.getCountCart(userDtls.getId());
			m.addAttribute("countCart", countCart);
		}

		List<Category> allActiveCategory = categoryService.getAllActiveCategory();
		m.addAttribute("categorys", allActiveCategory);
	}

	@GetMapping("/loadAddProduct")
	public String loadAddProduct(Model m) {
		List<Category> categories = categoryService.getAllCategory();
		m.addAttribute("categories", categories);
		return "admin/add_product";
	}

	@GetMapping("/category")
	public String category(Model m, @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "4") Integer pageSize) {
		Page<Category> page = categoryService.getAllCategorPagination(pageNo, pageSize);
		List<Category> categorys = page.getContent();
		m.addAttribute("categorys", categorys);

		m.addAttribute("pageNo", page.getNumber());
		m.addAttribute("pageSize", pageSize);
		m.addAttribute("totalElements", page.getTotalElements());
		m.addAttribute("totalPages", page.getTotalPages());
		m.addAttribute("isFirst", page.isFirst());
		m.addAttribute("isLast", page.isLast());

		return "admin/category";
	}

	@PostMapping("/saveCategory")
	public String saveCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file,
			HttpSession session) throws IOException {

		String imageName = file != null ? file.getOriginalFilename() : "default.jpg";
		category.setImageName(imageName);

		Boolean existCategory = categoryService.existCategory(category.getName());

		if (existCategory) {
			session.setAttribute("errorMsg", "Tên Danh Mục Đã Tồn Tại");
		} else {

			Category saveCategory = categoryService.saveCategory(category);

			if (ObjectUtils.isEmpty(saveCategory)) {
				session.setAttribute("errorMsg", "Lỗi ! Danh Mục Chưa Được Lưu");
			} else {

				File saveFileDir = new File("uploads/category_img");
				if (!saveFileDir.exists()) {
					saveFileDir.mkdirs();
				}
				Path path = Paths.get(saveFileDir.getAbsolutePath() + File.separator + file.getOriginalFilename());

				// System.out.println(path);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				session.setAttribute("succMsg", "Lưu Thành Công");
			}
		}

		return "redirect:/admin/category";
	}

	@GetMapping("/deleteCategory/{id}")
	public String deleteCategory(@PathVariable int id, HttpSession session) {
		Boolean deleteCategory = categoryService.deleteCategory(id);

		if (deleteCategory) {
			session.setAttribute("succMsg", "Xóa Thành Công");
		} else {
			session.setAttribute("errorMsg", "Xóa Thất Bại");
		}

		return "redirect:/admin/category";
	}

	@GetMapping("/loadEditCategory/{id}")
	public String loadEditCategory(@PathVariable int id, Model m) {
		m.addAttribute("category", categoryService.getCategoryById(id));
		return "admin/edit_category";
	}

	@PostMapping("/updateCategory")
	public String updateCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file,
			HttpSession session) throws IOException {

		Category oldCategory = categoryService.getCategoryById(category.getId());
		String imageName = file.isEmpty() ? oldCategory.getImageName() : file.getOriginalFilename();

		if (!ObjectUtils.isEmpty(category)) {

			oldCategory.setName(category.getName());
			oldCategory.setIsActive(category.getIsActive());
			oldCategory.setImageName(imageName);
		}

		Category updateCategory = categoryService.saveCategory(oldCategory);

		if (!ObjectUtils.isEmpty(updateCategory)) {

			if (!file.isEmpty()) {
				File saveFileDir = new File("uploads/category_img");
				if (!saveFileDir.exists()) {
				    saveFileDir.mkdirs();
				}
				Path path = Paths.get(saveFileDir.getAbsolutePath() + File.separator + file.getOriginalFilename());

				// System.out.println(path);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			}

			session.setAttribute("succMsg", "Danh Mục Cập Nhật Thành Công");
		} else {
			session.setAttribute("errorMsg", "Danh Mục Cập Nhật Thất Bại");
		}

		return "redirect:/admin/loadEditCategory/" + category.getId();
	}

	@PostMapping("/saveProduct")
	public String saveProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile image,
			@RequestParam(value = "extraImageFiles", required = false) MultipartFile[] extraImages,
			@RequestParam(value = "colors", required = false) List<String> colors,
			@RequestParam(value = "sizes", required = false) List<String> sizes,
			@RequestParam(value = "stocks", required = false) List<Integer> stocks, HttpSession session,
			Principal principal) throws IOException {

		UserDtls loggedInUser = userService.getUserByEmail(principal.getName());

		if ("ROLE_MANAGER".equals(loggedInUser.getRole())) {
			long currentCount = productRepository.countByCreatedBy(loggedInUser);
			if (loggedInUser.getMaxProductLimit() != null && currentCount >= loggedInUser.getMaxProductLimit()) {
				session.setAttribute("errorMsg", "Bạn đã đạt giới hạn thêm " + loggedInUser.getMaxProductLimit()
						+ " sản phẩm. Vui lòng liên hệ Admin!");
				return "redirect:/admin/loadAddProduct";
			}
		}

		product.setCreatedBy(loggedInUser);

		String imageName = image.isEmpty() ? "default.jpg" : image.getOriginalFilename();
		product.setImage(imageName);
		product.setDiscount(0);
		product.setDiscountPrice(product.getPrice());

		List<ProductVariant> variants = new ArrayList<>();
		if (colors != null && sizes != null && stocks != null) {
			for (int i = 0; i < colors.size(); i++) {
				ProductVariant variant = new ProductVariant();
				variant.setColor(colors.get(i));
				variant.setSize(sizes.get(i));
				variant.setStock(stocks.get(i) < 0 ? 0 : stocks.get(i));
				variant.setProduct(product);
				variants.add(variant);
			}
		}
		product.setVariants(variants);

		if (extraImages != null && extraImages.length > 0) {
			List<ProductImage> imageList = new ArrayList<>();
			for (MultipartFile extraFile : extraImages) {
				if (!extraFile.isEmpty()) {
					ProductImage img = new ProductImage();
					img.setImageName(extraFile.getOriginalFilename());
					img.setProduct(product);
					imageList.add(img);
				}
			}
			product.setExtraImages(imageList);
		}

		Product saveProduct = productService.saveProduct(product);

		if (!ObjectUtils.isEmpty(saveProduct)) {
			try {
				File saveFileDir = new File("uploads/product_img/" + product.getCategory());
				
				if (!saveFileDir.exists()) {
					saveFileDir.mkdirs();
				}

				if (!image.isEmpty()) {
					Path mainPath = Paths.get(saveFileDir.getAbsolutePath() + File.separator + image.getOriginalFilename());
					Files.copy(image.getInputStream(), mainPath, StandardCopyOption.REPLACE_EXISTING);
				}

				if (extraImages != null && extraImages.length > 0) {
					for (MultipartFile extraFile : extraImages) {
						if (!extraFile.isEmpty()) {
							Path extraPath = Paths.get(saveFileDir.getAbsolutePath() + File.separator + extraFile.getOriginalFilename());
							Files.copy(extraFile.getInputStream(), extraPath, StandardCopyOption.REPLACE_EXISTING);
						}
					}
				}

				session.setAttribute("succMsg", "Lưu Sản Phẩm Thành Công");
			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute("errorMsg", "Lưu Sản Phẩm Thành Công nhưng lỗi lưu ảnh!");
			}
		} else {
			session.setAttribute("errorMsg", "Lưu Sản Phẩm Thất Bại");
		}

		return "redirect:/admin/loadAddProduct";
	}

	@GetMapping("/products")
	public String loadViewProduct(Model m, @RequestParam(defaultValue = "") String ch,
			@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, Principal principal) {

		UserDtls loggedInUser = userService.getUserByEmail(principal.getName());
		Page<Product> page = null;

		if (ch != null && ch.length() > 0) {
			page = productService.searchProductPagination(pageNo, pageSize, ch, loggedInUser);
		} else {
			page = productService.getAllProductsPagination(pageNo, pageSize, loggedInUser);
		}

		m.addAttribute("products", page.getContent());
		m.addAttribute("pageNo", page.getNumber());
		m.addAttribute("pageSize", pageSize);
		m.addAttribute("totalElements", page.getTotalElements());
		m.addAttribute("totalPages", page.getTotalPages());
		m.addAttribute("isFirst", page.isFirst());
		m.addAttribute("isLast", page.isLast());

		return "admin/products";
	}

	@GetMapping("/deleteProduct/{id}")
	public String deleteProduct(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		boolean isDeleted = productService.deleteProduct(id);

		if (isDeleted) {
			redirectAttributes.addFlashAttribute("successMsg", "Xóa sản phẩm thành công!");
		} else {
			redirectAttributes.addFlashAttribute("errorMsg", "Không thể xóa sản phẩm vì đang tồn tại trong đơn hàng!");
		}

		return "redirect:/admin/products";
	}

	@GetMapping("/editProduct/{id}")
	public String editProduct(@PathVariable int id, Model m) {
		m.addAttribute("product", productService.getProductById(id));
		m.addAttribute("categories", categoryService.getAllCategory());
		return "admin/edit_product";
	}

	@PostMapping("/updateProduct")
	public String updateProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile image,
			@RequestParam(value = "extraImageFiles", required = false) MultipartFile[] extraImages,
			@RequestParam(value = "colors", required = false) List<String> colors,
			@RequestParam(value = "sizes", required = false) List<String> sizes,
			@RequestParam(value = "stocks", required = false) List<Integer> stocks, HttpSession session, Model m) {

		if (product.getDiscount() < 0 || product.getDiscount() > 100) {
			session.setAttribute("errorMsg", "invalid Discount");
		} else {

			List<ProductVariant> newVariants = new ArrayList<>();
			if (colors != null && sizes != null && stocks != null) {
				for (int i = 0; i < colors.size(); i++) {
					ProductVariant variant = new ProductVariant();
					variant.setColor(colors.get(i));
					variant.setSize(sizes.get(i));
					variant.setStock(stocks.get(i) < 0 ? 0 : stocks.get(i));
					newVariants.add(variant);
				}
			}
			product.setVariants(newVariants);

			Product updateProduct = productService.updateProduct(product, image, extraImages);

			if (!ObjectUtils.isEmpty(updateProduct)) {
				session.setAttribute("succMsg", "Cập Nhật Sản Phẩm Thành Công");
			} else {
				session.setAttribute("errorMsg", "Cập Nhật Sản Phẩm Thất Bại");
			}
		}
		return "redirect:/admin/editProduct/" + product.getId();
	}

	@GetMapping("/users")
	public String getAllUsers(Model m, @RequestParam Integer type) {
		List<UserDtls> users = null;
		if (type == 1) {
			users = userService.getUsers("ROLE_USER");
		} else {
			users = userService.getUsers("ROLE_MANAGER");
		}
		m.addAttribute("userType", type);
		m.addAttribute("users", users);
		return "/admin/users";
	}

	@GetMapping("/updateSts")
	public String updateUserAccountStatus(@RequestParam Boolean status, @RequestParam Integer id,
			@RequestParam Integer type, HttpSession session) {
		Boolean f = userService.updateAccountStatus(id, status);
		if (f) {
			session.setAttribute("succMsg", "Cập Nhật Trang Thái Thành Công");
		} else {
			session.setAttribute("errorMsg", "Cập Nhật Sản Phẩm Thất Bại");
		}
		return "redirect:/admin/users?type=" + type;
	}

	@GetMapping("/orders")
	public String getAllOrders(Model m, @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, Principal principal) {

		UserDtls loggedInUser = userService.getUserByEmail(principal.getName());
		List<ProductOrder> allOrders = orderService.getAllOrders();

		if ("ROLE_MANAGER".equals(loggedInUser.getRole())) {
			allOrders = allOrders.stream()
					.filter(o -> o.getProduct().getCreatedBy() != null
							&& o.getProduct().getCreatedBy().getId().equals(loggedInUser.getId()))
					.collect(Collectors.toList());
		}

		allOrders.sort((o1, o2) -> o2.getId().compareTo(o1.getId()));

		Map<String, List<ProductOrder>> groupedOrders = new LinkedHashMap<>();
		for (ProductOrder o : allOrders) {
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
			orderMap.put("orderAddress", representative.getOrderAddress());

			summaryOrders.add(orderMap);
		}

		int totalElements = summaryOrders.size();
		int totalPages = (int) Math.ceil((double) totalElements / pageSize);
		if (totalPages == 0)
			totalPages = 1;
		int start = pageNo * pageSize;
		int end = Math.min(start + pageSize, totalElements);

		List<Map<String, Object>> pagedSummary = new ArrayList<>();
		if (start < totalElements) {
			pagedSummary = summaryOrders.subList(start, end);
		}

		m.addAttribute("orders", pagedSummary);
		m.addAttribute("srch", false);
		m.addAttribute("pageNo", pageNo);
		m.addAttribute("pageSize", pageSize);
		m.addAttribute("totalElements", totalElements);
		m.addAttribute("totalPages", totalPages);
		m.addAttribute("isFirst", pageNo == 0);
		m.addAttribute("isLast", pageNo >= totalPages - 1);

		return "/admin/orders";
	}

	@PostMapping("/update-order-status")
	public String updateOrderStatus(@RequestParam String orderId, @RequestParam Integer st, HttpSession session) {

		OrderStatus[] values = OrderStatus.values();
		String status = null;

		for (OrderStatus orderSt : values) {
			if (orderSt.getId().equals(st)) {
				status = orderSt.getName();
			}
		}

		boolean isUpdated = false;

		List<ProductOrder> allOrders = orderService.getAllOrders();
		for (ProductOrder o : allOrders) {
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

		if (isUpdated) {
			session.setAttribute("succMsg", "Trạng Thái Đã Được Cập Nhật");
		} else {
			session.setAttribute("errorMsg", "Cập Nhật Trạng Thái Thất Bại");
		}
		return "redirect:/admin/orders";
	}

	@GetMapping("/search-order")
	public String searchProduct(@RequestParam String orderId, Model m, HttpSession session,
			@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

		if (orderId != null && orderId.trim().length() > 0) {
			List<ProductOrder> allOrders = orderService.getAllOrders();
			List<ProductOrder> orderDetails = allOrders.stream().filter(o -> o.getOrderId().equals(orderId.trim()))
					.collect(Collectors.toList());

			if (orderDetails.isEmpty()) {
				session.setAttribute("errorMsg", "Không tìm thấy mã đơn hàng");
				m.addAttribute("orderDtls", null);
			} else {
				ProductOrder representative = orderDetails.get(0);
				double totalAmount = orderDetails.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
				int totalQuantity = orderDetails.stream().mapToInt(ProductOrder::getQuantity).sum();

				Map<String, Object> orderMap = new HashMap<>();
				orderMap.put("orderId", orderId.trim());
				orderMap.put("orderDate", representative.getOrderDate());
				orderMap.put("status", representative.getStatus());
				orderMap.put("totalAmount", totalAmount);
				orderMap.put("totalQuantity", totalQuantity);
				orderMap.put("orderAddress", representative.getOrderAddress());

				m.addAttribute("orderDtls", orderMap);
			}
			m.addAttribute("srch", true);
		} else {
			return "redirect:/admin/orders";
		}
		return "/admin/orders";
	}

	@GetMapping("/view-order")
	public String viewOrderDetailsAdmin(@RequestParam String orderId, Model model, Principal principal) {
		UserDtls loggedInUser = userService.getUserByEmail(principal.getName());
		List<ProductOrder> allOrders = orderService.getAllOrders();

		List<ProductOrder> orderDetails = allOrders.stream().filter(o -> o.getOrderId().equals(orderId))
				.filter(o -> "ROLE_SUPERADMIN".equals(loggedInUser.getRole()) || (o.getProduct().getCreatedBy() != null
						&& o.getProduct().getCreatedBy().getId().equals(loggedInUser.getId())))
				.collect(Collectors.toList());

		if (orderDetails.isEmpty()) {
			return "redirect:/admin/orders";
		}

		double totalAmount = orderDetails.stream().mapToDouble(o -> o.getPrice() * o.getQuantity()).sum();
		model.addAttribute("orderDetails", orderDetails);
		model.addAttribute("orderInfo", orderDetails.get(0));
		model.addAttribute("totalAmount", totalAmount);

		return "admin/view_order";
	}

	@GetMapping("/add-admin")
	public String loadAdminAdd() {
		return "/admin/add_admin";
	}

	@PostMapping("/save-admin")
	public String saveAdmin(@ModelAttribute UserDtls user, @RequestParam("img") MultipartFile file,
			@RequestParam(value = "maxProductLimit", defaultValue = "50") Integer limit, HttpSession session)
			throws IOException {

		user.setRole("ROLE_MANAGER");
		user.setMaxProductLimit(limit);

		String imageName = file.isEmpty() ? "default.jpg" : file.getOriginalFilename();
		user.setProfileImage(imageName);
		UserDtls saveUser = userService.saveAdmin(user);

		if (!ObjectUtils.isEmpty(saveUser)) {
			if (!file.isEmpty()) {
				File saveFileDir = new File("uploads/profile_img");
				if (!saveFileDir.exists()) {
				    saveFileDir.mkdirs();
				}
				Path path = Paths.get(saveFileDir.getAbsolutePath() + File.separator + file.getOriginalFilename());

//				System.out.println(path);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			}
			session.setAttribute("succMsg", "Đăng Ký Thành Công");
		} else {
			session.setAttribute("errorMsg", "Đăng Ký Thất Bại");
		}

		return "redirect:/admin/add-admin";
	}

	@GetMapping("/profile")
	public String profile() {
		return "/admin/profile";
	}

	@PostMapping("/update-profile")
	public String updateProfile(@ModelAttribute UserDtls user, @RequestParam MultipartFile img, HttpSession session) {
		UserDtls updateUserProfile = userService.updateUserProfile(user, img);
		if (ObjectUtils.isEmpty(updateUserProfile)) {
			session.setAttribute("errorMsg", "Hồ Sơ Chưa Cập Nhật");
		} else {
			session.setAttribute("succMsg", "Hồ Sơ Đã Cập Nhật");
		}
		return "redirect:/admin/profile";
	}

	@PostMapping("/change-password")
	public String changePassword(@RequestParam String currentPassword, @RequestParam String newPassword,
			@RequestParam String confirmPassword, Principal p, HttpSession session) {

		UserDtls user = commonUtil.getLoggedInUserDetails(p);
		if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
			session.setAttribute("errorMsg", "Mật khẩu hiện tại không đúng!");
			return "redirect:/admin/profile";
		}
		if (!newPassword.equals(confirmPassword)) {
			session.setAttribute("errorMsg", "Xác nhận mật khẩu không khớp!");
			return "redirect:/admin/profile";
		}
		user.setPassword(passwordEncoder.encode(newPassword));
		userService.updateUser(user);

		session.setAttribute("succMsg", "Đổi mật khẩu thành công!");
		return "redirect:/admin/profile";
	}

	@GetMapping({ "", "/" })
	public String adminHome(Model model, Principal principal) {
		if (principal == null)
			return "redirect:/signin";

		UserDtls user = userService.getUserByEmail(principal.getName());
		model.addAttribute("user", user);

		List<Product> allProducts = productService.getAllProducts();
		List<ProductOrder> allOrders = orderService.getAllOrders();

		if ("ROLE_MANAGER".equals(user.getRole())) {
			allProducts = allProducts.stream()
					.filter(p -> p.getCreatedBy() != null && p.getCreatedBy().getId().equals(user.getId()))
					.collect(Collectors.toList());

			allOrders = allOrders.stream().filter(o -> o.getProduct().getCreatedBy() != null
					&& o.getProduct().getCreatedBy().getId().equals(user.getId())).collect(Collectors.toList());
		}

		model.addAttribute("products", allProducts.size());

		if ("ROLE_SUPERADMIN".equals(user.getRole())) {
			model.addAttribute("users", userService.getUsers("ROLE_USER").size());
		} else {
			model.addAttribute("users", 0);
		}

		double totalRevenue = 0.0;
		java.util.Set<String> uniqueOrders = new java.util.HashSet<>();

		Map<Integer, Double> revenueByMonth = new java.util.TreeMap<>();
		for (int i = 1; i <= 12; i++) {
			revenueByMonth.put(i, 0.0);
		}

		for (ProductOrder o : allOrders) {
			uniqueOrders.add(o.getOrderId());

			if (!"Cancelled".equalsIgnoreCase(o.getStatus())) {
				double orderTotal = o.getPrice() * o.getQuantity();
				totalRevenue += orderTotal;

				if (o.getOrderDate() != null) {
					int month = o.getOrderDate().getMonthValue();
					revenueByMonth.put(month, revenueByMonth.get(month) + orderTotal);
				}
			}
		}

		model.addAttribute("orders", uniqueOrders.size());
		model.addAttribute("revenue", totalRevenue);

		List<String> revenueLabels = new ArrayList<>();
		List<Double> revenueValues = new ArrayList<>();
		for (Map.Entry<Integer, Double> entry : revenueByMonth.entrySet()) {
			revenueLabels.add("Tháng " + entry.getKey());
			revenueValues.add(entry.getValue());
		}
		model.addAttribute("chartRevenueLabels", revenueLabels);
		model.addAttribute("chartRevenueData", revenueValues);

		Map<String, Long> categoryCountMap = allProducts.stream().filter(p -> p.getCategory() != null)
				.collect(Collectors.groupingBy(Product::getCategory, Collectors.counting()));

		List<String> categoryLabels = new ArrayList<>(categoryCountMap.keySet());
		List<Long> categoryData = new ArrayList<>(categoryCountMap.values());

		model.addAttribute("chartCategoryLabels", categoryLabels);
		model.addAttribute("chartCategoryData", categoryData);

		return "admin/index";
	}

}
