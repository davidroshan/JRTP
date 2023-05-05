package com.jrtp.blogapp.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jrtp.blogapp.binding.AddCommentForm;
import com.jrtp.blogapp.binding.AddPostForm;
import com.jrtp.blogapp.binding.EditPostForm;
import com.jrtp.blogapp.entity.Comments;
import com.jrtp.blogapp.entity.Post;
import com.jrtp.blogapp.entity.User;
import com.jrtp.blogapp.repository.CommentRepository;
import com.jrtp.blogapp.repository.PostRepository;
import com.jrtp.blogapp.repository.UserRepository;
import com.jrtp.blogapp.service.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private CommentRepository commentRepo;

	@Override
	public List<Post> getAllUserPost(Integer userId) {
		User user = userRepo.findById(userId).get();
		
		List<Post> posts = user.getPost();
		return posts;
	}


	@Override
	public boolean addPost(AddPostForm form, Integer id) {
		
		Post post = new Post();
		BeanUtils.copyProperties(form, post);
		
		User user = userRepo.findById(id).get();
		post.setUser(user);
		
		postRepo.save(post);
		return true;
	}


	@Override
	public List<Post> getAllPost() {
		
		return postRepo.findAll();
	}


	@Override
	public Post getPost(Integer id) {
		Post post = postRepo.findById(id).get();
		return post;
	}


	@Override
	public boolean addComment(AddCommentForm form, Integer postId) {
		Comments commentEntity = new Comments();
		BeanUtils.copyProperties(form, commentEntity);
		
		Post post = postRepo.findById(postId).get();
		commentEntity.setPost(post);
		commentRepo.save(commentEntity);
		return true;
	}


	@Override
	public List<Comments> getComments(Integer postId) {
		Post post = postRepo.findById(postId).get();
		List<Comments> comments = post.getComments();
		return comments;
	}


	@Override
	public List<Comments> allComments() {
		return commentRepo.findAll();
	}


	@Override
	public List<Post> getFilterPost(String key) {
		List<Post> posts = postRepo.getPostOnKeys(key);
		//System.out.println(posts);
		return posts;
	}


	@Override
	public boolean editPost(EditPostForm form, Integer postId) {
		Post post = postRepo.findById(postId).get();
		BeanUtils.copyProperties(form, post);
		postRepo.save(post);
		return true;
	}

}
