package com.jrtp.officeportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jrtp.officeportal.entity.UserDtlsEntity;

public interface UserDtlsRepo extends JpaRepository<UserDtlsEntity, Integer> {
    
	public UserDtlsEntity findByEmail(String email);
	
	@Query("select u from UserDtlsEntity u where u.email =:email AND u.password =:password")
	public UserDtlsEntity getUserByEmailAndPwd(@Param("email") String email,@Param("password") String password);	
}
