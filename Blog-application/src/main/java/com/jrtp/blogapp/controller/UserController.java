package com.jrtp.blogapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.jrtp.blogapp.binding.LoginForm;
import com.jrtp.blogapp.binding.SignupForm;
import com.jrtp.blogapp.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/signup")
	public String signupPage(Model model) {
		model.addAttribute("user", new SignupForm());
		return "signup";
	}
	
	
	@PostMapping("/register")
	public String signUp(@ModelAttribute("user") SignupForm form, Model model) {
		boolean status = userService.register(form);
		if(status) {
			model.addAttribute("succMsg", "Registration Successful...Plz Login");
			model.addAttribute("user", form);
			return "success";
		}else {
			model.addAttribute("errMsg", "Plz try again...");
			model.addAttribute("user", form);
			return "signup";
		}
		
	}
	
	@GetMapping("/login")
	public String loginPage(Model model) {
		
        model.addAttribute("login", new LoginForm());		
		return "login";
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute("login") LoginForm form, Model model) {
		System.out.println(form);
		String status = userService.login(form);
		
		if(status.equals("success")) {
			System.out.println(status);
			return "redirect:/dashboard"; 
		}else {
			System.out.println(status);
			model.addAttribute("errMsg", "Bad Creadential");
			return "login";	
		}
	}
	
	@GetMapping("/dashboard")
	public String dashboardPage() {
		return "dashboard";
	}
}
