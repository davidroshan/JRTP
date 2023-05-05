package com.jrtp.blogapp.service;




import com.jrtp.blogapp.binding.LoginForm;
import com.jrtp.blogapp.binding.SignupForm;


public interface UserService {

	public boolean register(SignupForm form);
	
	public String login(LoginForm form);
	
	
}
