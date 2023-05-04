package com.jrtp.blogapp.service.impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jrtp.blogapp.binding.LoginForm;
import com.jrtp.blogapp.binding.SignupForm;
import com.jrtp.blogapp.entity.User;
import com.jrtp.blogapp.repository.UserRepository;
import com.jrtp.blogapp.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	HttpSession session;
	
	
	@Override
	public boolean register(SignupForm form) {
		User user = new User();
		BeanUtils.copyProperties(form, user);
		User save = userRepo.save(user);
		if(save != null) {
			return true;
		}else {
			return false;
		}
	}


	@Override
	public String login(LoginForm form) {
		User user = userRepo.findByEmailAndPassword(form.getEmail(), form.getPassword());
		if(user != null) {
			session.setAttribute("userId", user.getUserId());
			System.out.println("success");
			return "success";
		}
		return "fail";
	}

}
