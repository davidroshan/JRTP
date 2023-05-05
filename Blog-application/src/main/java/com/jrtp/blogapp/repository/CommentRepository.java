package com.jrtp.blogapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jrtp.blogapp.entity.Comments;

public interface CommentRepository extends JpaRepository<Comments, Integer> {

}
