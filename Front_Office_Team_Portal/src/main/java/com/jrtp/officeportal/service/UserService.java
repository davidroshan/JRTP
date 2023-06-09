package com.jrtp.officeportal.service;



import com.jrtp.officeportal.binding.LoginForm;
import com.jrtp.officeportal.binding.ResetForm;
import com.jrtp.officeportal.binding.SignUpForm;
import com.jrtp.officeportal.binding.UnlockForm;

public interface UserService {
    
	public String login(LoginForm loginForm);
	
	public boolean signUp(SignUpForm signUpForm);
	
	public boolean unlockAccount(UnlockForm unlockForm);
	
	public String forgotPassword(String email);
	
	public String resetPassword(ResetForm resetForm);
}
