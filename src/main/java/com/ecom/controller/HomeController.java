package com.ecom.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.dto.ProductFilterDTO;
import com.ecom.model.Category;
import com.ecom.model.Product;
import com.ecom.model.UserDtls;
import com.ecom.repository.CategoryRepository;
import com.ecom.service.CartService;
import com.ecom.service.CategoryService;
import com.ecom.service.ProductFilterService;
import com.ecom.service.ProductService;
import com.ecom.service.UserService;
import com.ecom.util.CommonUtil;

import io.micrometer.common.util.StringUtils;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private CartService cartService;
	
	@Autowired
	private ProductFilterService filterService;

	@Autowired
	private CategoryRepository categoryRepository;

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

	@GetMapping("/")
	public String index(Model m) {

		List<Category> allActiveCategory = categoryService.getAllActiveCategory().stream()
				.sorted((c1, c2) -> c2.getId().compareTo(c1.getId())).limit(6).toList();
		List<Product> allActiveProducts = productService.getAllActiveProducts("").stream()
				.sorted((p1, p2) -> p2.getId().compareTo(p1.getId())).limit(8).toList();
		m.addAttribute("category", allActiveCategory);
		m.addAttribute("products", allActiveProducts);
		return "index";
	}

	@GetMapping("/signin")
	public String login() {
		return "login";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@GetMapping("/products")
	public String products(
	        @RequestParam(defaultValue = "0") Integer pageNo,
	        @RequestParam(defaultValue = "") String category,
	        @RequestParam(defaultValue = "0") Double minPrice,
	        @RequestParam(defaultValue = "10000000") Double maxPrice,
	        @RequestParam(defaultValue = "false") Boolean hasDiscount,
	        @RequestParam(defaultValue = "") String keyword,
	        @RequestParam(defaultValue = "") String sort,
	        Model model) {

	    try {
	        ProductFilterDTO filter = new ProductFilterDTO(
	                category, minPrice, maxPrice, hasDiscount, sort, pageNo, keyword);

	        Page<Product> page = filterService.applyFilter(filter);
	        List<Category> categories = categoryRepository.findAll();

	        model.addAttribute("products", page.getContent());
	        model.addAttribute("productsSize", page.getContent().size());
	        model.addAttribute("totalElements", page.getTotalElements());
	        model.addAttribute("totalPages", page.getTotalPages());
	        model.addAttribute("pageNo", pageNo);
	        model.addAttribute("categories", categories);
	        model.addAttribute("isFirst", page.isFirst());
	        model.addAttribute("isLast", page.isLast());

	        model.addAttribute("category", category);
	        model.addAttribute("minPrice", minPrice);
	        model.addAttribute("maxPrice", maxPrice);
	        model.addAttribute("hasDiscount", hasDiscount);
	        model.addAttribute("keyword", keyword);
	        model.addAttribute("sort", sort);
	        model.addAttribute("minPriceRange", filterService.getMinPrice());
	        model.addAttribute("maxPriceRange", filterService.getMaxPrice());

	        return "product"; 

	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("error", "Có lỗi xảy ra khi tải sản phẩm");
	        return "error";
	    }
	}

	@GetMapping("/product/{id}")
	public String viewProduct(@PathVariable Integer id, Model model) {
	    try {
	        Product product = productService.getProductById(id);
	        if (product != null) {
	            model.addAttribute("product", product);
	            return "view_product"; 
	        }
	        model.addAttribute("error", "Không tìm thấy sản phẩm");
	        return "error";
	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("error", "Có lỗi xảy ra");
	        return "error";
	    }
	}

	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute UserDtls user, @RequestParam("img") MultipartFile file, HttpSession session)
			throws IOException {

		Boolean existsEmail = userService.existsEmail(user.getEmail());

		if (existsEmail) {
			session.setAttribute("errorMsg", "Email already exist");
		} else {
			String imageName = file.isEmpty() ? "default.jpg" : file.getOriginalFilename();
			user.setProfileImage(imageName);
			UserDtls saveUser = userService.saveUser(user);

			if (!ObjectUtils.isEmpty(saveUser)) {
				if (!file.isEmpty()) {
					File saveFile = new ClassPathResource("static/img").getFile();

					Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "profile_img" + File.separator
							+ file.getOriginalFilename());

//					System.out.println(path);
					Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				}
				session.setAttribute("succMsg", "Đăng Ký Thành Công");
			} else {
				session.setAttribute("errorMsg", "Đăng Ký Thất Bại");
			}
		}

		return "redirect:/register";
	}

//	Forgot Password Code 

	@GetMapping("/forgot-password")
	public String showForgotPassword() {
		return "forgot_password.html";
	}

	@PostMapping("/forgot-password")
	public String processForgotPassword(@RequestParam String email, HttpSession session, HttpServletRequest request)
			throws UnsupportedEncodingException, MessagingException {

		UserDtls userByEmail = userService.getUserByEmail(email);

		if (ObjectUtils.isEmpty(userByEmail)) {
			session.setAttribute("errorMsg", "Invalid email");
		} else {

			String resetToken = UUID.randomUUID().toString();
			userService.updateUserResetToken(email, resetToken);


			String url = CommonUtil.generateUrl(request) + "/reset-password?token=" + resetToken;

			Boolean sendMail = commonUtil.sendMail(url, email);

			if (sendMail) {
				session.setAttribute("succMsg", "Please check your email..Password Reset link sent");
			} else {
				session.setAttribute("errorMsg", "Somethong wrong on server ! Email not send");
			}
		}

		return "redirect:/forgot-password";
	}

	@GetMapping("/reset-password")
	public String showResetPassword(@RequestParam String token, HttpSession session, Model m) {

	    UserDtls userByToken = userService.getUserByToken(token);

	    if (userByToken == null) {
	        m.addAttribute("msg", "Liên kết không hợp lệ hoặc đã hết hạn!");
	        return "message";
	    }
	    m.addAttribute("token", token);
	    return "reset_password";
	}

	@PostMapping("/reset-password")
	public String resetPassword(@RequestParam String token, @RequestParam String password, 
	                            HttpSession session, Model m) {

	    UserDtls userByToken = userService.getUserByToken(token);
	    if (userByToken == null) {
	        m.addAttribute("errorMsg", "Liên kết không hợp lệ hoặc đã hết hạn!");
	        return "message";
	    } else {
	        userByToken.setPassword(passwordEncoder.encode(password));
	        userByToken.setResetToken(null);
	        userService.updateUser(userByToken);

	        m.addAttribute("msg", "Đổi mật khẩu thành công!");
	        return "message";
	    }
	}


	@GetMapping("/search")
	public String searchProduct(@RequestParam String ch, Model m) {
		List<Product> searchProducts = productService.searchProduct(ch);
		m.addAttribute("products", searchProducts);
		List<Category> categories = categoryService.getAllActiveCategory();
		m.addAttribute("categories", categories);
		return "product";

	}

}
