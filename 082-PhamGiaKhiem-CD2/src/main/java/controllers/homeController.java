package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import beans.SanPham;
import beans.User;
import dao.sanphamDAO;


@Controller
public class homeController {
	@Autowired
	sanphamDAO dao;

	@GetMapping("/")
	public String showLoginPage() {
		return "login"; // login.jsp
	}

	@PostMapping("/login")
	public String handleLogin(@RequestParam("username") String username, @RequestParam("password") String password,
			Model model) {
		List<User> users = dao.getUsers();
		boolean isValidUser = false;

		for (User user : users) {
			if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
				isValidUser = true;
				break;
			}
		}

		if (isValidUser) {
			return "redirect:/viewSanPham";
		} else {
			model.addAttribute("error", "Sai tên đăng nhập hoặc mật khẩu.");
			return "login";
		}
	}

	@RequestMapping("/viewSanPham")
	public String viewSanPham(Model model) {
		List<SanPham> list = dao.getSanPhams();
		model.addAttribute("list", list);
		return "danhsach";
	}

	@GetMapping("/addSanPham")
	public String showForm(Model model) {
		model.addAttribute("sanpham", new SanPham());
		return "them"; // them.jsp
	}

	@PostMapping("/saveSanPham")
	public String saveSanPham(@ModelAttribute("sanpham") SanPham sp) {
		dao.addSanPham(sp);
		return "redirect:/viewSanPham";
	}

	@GetMapping("/editSanPham/{id}")
	public String editSanPham(@PathVariable int id, Model model) {
		SanPham sp = dao.getSanPhamById(id);
		model.addAttribute("sanpham", sp);
		return "sua"; // sua.jsp
	}

	@PostMapping("/editSaveSanPham")
	public String editSaveSanPham(@ModelAttribute("sanpham") SanPham sp) {
		dao.update(sp);
		return "redirect:/viewSanPham";
	}

	@GetMapping("/deleteSanPham/{id}")
	public String deleteSanPham(@PathVariable int id) {
		dao.deleteSanPham(id);
		return "redirect:/viewSanPham";
	}
}
