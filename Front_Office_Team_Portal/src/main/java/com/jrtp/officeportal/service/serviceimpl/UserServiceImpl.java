package com.jrtp.officeportal.service.serviceimpl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jrtp.officeportal.binding.LoginForm;
import com.jrtp.officeportal.binding.ResetForm;
import com.jrtp.officeportal.binding.SignUpForm;
import com.jrtp.officeportal.binding.UnlockForm;
import com.jrtp.officeportal.entity.UserDtlsEntity;
import com.jrtp.officeportal.repository.UserDtlsRepo;
import com.jrtp.officeportal.service.UserService;
import com.jrtp.officeportal.util.EmailUtils;

@Service
public class UserServiceImpl implements UserService {
    
	@Autowired
	private UserDtlsRepo userRepo;
	@Autowired
	private EmailUtils emailUtil;
	
	@Autowired
	HttpSession session;
	
	private Map<String, String> generatedCode = new HashMap<>();
	
	@Override
	public String login(LoginForm loginForm) {
		UserDtlsEntity user = userRepo.getUserByEmailAndPwd(loginForm.getEmail(), loginForm.getPwd()) ;
		//System.out.println(user);
		if(user==null) {
			return "Invalid Credential";
		}
		if(user.getAccStatus().equals("LOCKED")) {
			return "Your account is locked, please check your mail and unlock your account";
		}
		
		session.setAttribute("userId", user.getUserId());
		return "success";
	}

	@Override
	public boolean signUp(SignUpForm signUpForm) {
		
		//Checking email is already available in db
		UserDtlsEntity entity = userRepo.findByEmail(signUpForm.getEmail());
		if(entity!=null) {
			return false;
		}
		 
		//Copy data from binding object to entity object
		 UserDtlsEntity userEntity = new UserDtlsEntity();
		 BeanUtils.copyProperties(signUpForm, userEntity);
		 System.out.println(userEntity);
		
		// TODO To generate random password and set to the object
		 String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		 String tempPwd = RandomStringUtils.random( 7, characters );
		 System.out.println( tempPwd );
		 userEntity.setPassword(tempPwd);
		
		//TODO set account status as locked
		 userEntity.setAccStatus("LOCKED");
		 
		 System.out.println(userEntity);
		
		//TODO insert record
		 userRepo.save(userEntity);
		 
		//TODO send email to unlock the account
		String to = signUpForm.getEmail();
		String subject = "<h1>Unlock Your Account</h1>";
		StringBuffer body = new StringBuffer();
		body.append("<h2>Use below temporary password to unlock your account</h2>");
		body.append("Temporary password: "+tempPwd);
		body.append("<br/>");
		body.append("<a href=\"http://localhost:8080/unlock?email="+to+"\">Click Here To Unlock Account</a>");
		emailUtil.sendEmail(to, subject, body.toString());
		return true;
	}

	@Override
	public boolean unlockAccount(UnlockForm unlockForm) {
		// TODO check the temp password is correct or not
		UserDtlsEntity user = userRepo.findByEmail(unlockForm.getEmail());
		if(user.getPassword().equals(unlockForm.getTempPwd())) {
			user.setPassword(unlockForm.getConfirmPwd());
			user.setAccStatus("UNLOCKED");
			userRepo.save(user);
			return true;
		}else {
			return false;
		}
		
	}

	@Override
	public String forgotPassword(String email) {
		boolean status = false;
		UserDtlsEntity user = userRepo.findByEmail(email);
		System.out.println(user);
		if(user == null) {
			return "Plz give valid mail id";
		}
		if(user.getAccStatus().equals("LOCKED")) {
			return "Plz unlock your account first";
		}
		String characters = "0123456789";
		String code = RandomStringUtils.random( 6, characters );
		
		generatedCode.put("tempCode", code);
		
		String to = email;
		String subject = "<h1> Change Your password </h1>";
		StringBuffer body = new StringBuffer();
		body.append("\"<h2>Use below temporary password to unlock your account</h2>");
		body.append("Temporary password: "+code);
		body.append("<br/>");
		body.append("<a href=\"http://localhost:8080/reset?email="+to+"\">Click Here To reset your password</a>");
		status = emailUtil.sendEmail(to, subject, body.toString());
		if(status) {
			return "valid";
		}else {
			return "server issue....please try again";
		}
		
	}

	@Override
	public String resetPassword(ResetForm resetForm) {
		System.out.println(generatedCode);
		UserDtlsEntity user = userRepo.findByEmail(resetForm.getEmail());
		if(!resetForm.getCode().equals(generatedCode.get("tempCode"))) {
			return "plz enter valid code";
		}
		
		user.setPassword(resetForm.getConfirmPwd());
		userRepo.save(user);
		return "success";
	}

}
