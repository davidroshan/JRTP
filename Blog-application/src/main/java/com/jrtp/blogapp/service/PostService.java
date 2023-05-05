package com.jrtp.blogapp.service;

import java.util.List;

import com.jrtp.blogapp.binding.AddCommentForm;
import com.jrtp.blogapp.binding.AddPostForm;
import com.jrtp.blogapp.binding.EditPostForm;
import com.jrtp.blogapp.entity.Comments;
import com.jrtp.blogapp.entity.Post;

public interface PostService {

	public List<Post> getAllPost();
	
    public List<Post> getAllUserPost(Integer userId);
	
	public boolean addPost(AddPostForm form, Integer id);
	
	public Post getPost(Integer id);
	
	public boolean addComment(AddCommentForm form, Integer postId);
	
	public List<Comments> getComments(Integer postId);
	
	public List<Comments> allComments();
	
	public List<Post> getFilterPost(String key);
	
	public boolean editPost(EditPostForm form,Integer postId);
}
