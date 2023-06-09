package com.jrtp.officeportal.controller;



import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jrtp.officeportal.binding.LoginForm;
import com.jrtp.officeportal.binding.ResetForm;
import com.jrtp.officeportal.binding.SignUpForm;
import com.jrtp.officeportal.binding.UnlockForm;
import com.jrtp.officeportal.service.UserService;

@Controller
public class UserController {
    @Autowired
	private UserService userService;
    
    @Autowired
    HttpSession session;
    
    
	@GetMapping("/signup")
	public String signUpPage(Model model) {
	    model.addAttribute("user", new SignUpForm());
		return "signup";
	}
	
	@PostMapping("/register")
	public String registerUser(@ModelAttribute("user")SignUpForm form, Model model) {
		System.out.println(form);
		boolean status = userService.signUp(form);
		if(status) {
			model.addAttribute("succMsg", "Account Created Check Your Mail");
		}else {
			model.addAttribute("errMsg", "Email already exist");
		}
		return "signup";
	}
	
	
	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("login", new LoginForm());
		return "login";
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute("login") LoginForm form,Model model) {
		System.out.println(form);
		String status = userService.login(form);
		System.out.println(status);
		if(status.equals("success")) {
			System.out.println(session.getAttribute("userId"));
			return "redirect:/dashboard";
		}else {
			model.addAttribute("errMsg", status);
			return "login";
		}
		
	}
	
	
	@GetMapping("/unlock")
	public String unlockPage(Model model,@RequestParam String email) {
		System.out.println("usermail:"+email);
		UnlockForm form = new UnlockForm();
		form.setEmail(email);
		model.addAttribute("unlock", form);
		return "unlock";
	}
	
	@PostMapping("/unlock")
	public String unlock(@ModelAttribute("unlock") UnlockForm form,Model model) {
		System.out.println(form);
		if(!form.getNewPwd().equals(form.getConfirmPwd())) {
			System.out.println("not matched");
			model.addAttribute("errMsg", "New password and Confirm password must be same");
			return "unlock";
		}
		else {
			System.out.println("matched");
			boolean status = userService.unlockAccount(form);
			if(status) {
				model.addAttribute("succMsg", "Account Unlocked successfully, plz login to your account");
				return "unlock";
			}else {
				model.addAttribute("errMsg", "Plz enter correct temporary password");
				return "unlock";
			}
		}
	}
	
	
	
	@GetMapping("/forgot")
	public String forgotPwdPage() {
		return "forgot";
	}
	
	@PostMapping("/forgot")
	public String forgotPwd(@RequestParam("email") String email,Model model) {
		System.out.println("email"+email);
		String status = userService.forgotPassword(email);
		System.out.println(status);
		if(status.equals("valid")) {
			model.addAttribute("succMsg", "Plz check your mail for code");
			return "forgot";
			//return "redirect:/reset";
		}else {
			model.addAttribute("errMsg", status);
			return "forgot";
		}
		
	}
	
	@GetMapping("/reset")
	public String resetPage(@RequestParam("email") String email, Model model) {
		ResetForm form = new ResetForm();
		form.setEmail(email);
		model.addAttribute("reset", form);
		return "reset";
	}
	
	@PostMapping("/reset")
	public String reset(@ModelAttribute("reset") ResetForm form,Model model) {
		String status = userService.resetPassword(form);
		if(status.equals("success")) {
			model.addAttribute("succMsg", "Plz login with your new password");
			return "reset";
		}else {
			model.addAttribute("errMsg", status);
			return "reset";
		}
		
	}
}
