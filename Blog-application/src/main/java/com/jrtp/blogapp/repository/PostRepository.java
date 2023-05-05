package com.jrtp.blogapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jrtp.blogapp.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
	
	@Query("SELECT p FROM Post p WHERE p.title LIKE %:key%")
	public List<Post> getPostOnKeys(@Param("key") String key);

}
