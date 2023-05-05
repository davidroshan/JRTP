package com.jrtp.blogapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jrtp.blogapp.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	public User findByEmail(String email);
	
	@Query("SELECT u FROM User u WHERE u.email =:email AND u.password =:password")
	public User findByEmailAndPassword(@Param("email") String email,@Param("password") String password);

}
