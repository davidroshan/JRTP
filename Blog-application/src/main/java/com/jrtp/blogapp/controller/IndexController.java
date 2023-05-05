package com.jrtp.blogapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jrtp.blogapp.binding.AddCommentForm;
import com.jrtp.blogapp.entity.Post;
import com.jrtp.blogapp.service.PostService;

@Controller
public class IndexController {
    
	@Autowired
	private PostService postService;
	
	@GetMapping("/")
	public String indexPage(Model model) {
		List<Post> posts = postService.getAllPost();
		model.addAttribute("posts", posts);
		return "index";
	}
	
	@GetMapping("/read/{postId}")
	public String readBlog(@PathVariable("postId") Integer postId, Model model) {
		Post post = postService.getPost(postId);
		model.addAttribute("post", post);
		model.addAttribute("comment", new AddCommentForm());
		model.addAttribute("postId", postId);
		return "readblog";
	}
	
	
	@PostMapping("/addcomment")
	public String addComment(@ModelAttribute("comment") AddCommentForm form,@RequestParam("postid") Integer postId, Model model) {
		System.out.println(form);
		boolean status = postService.addComment(form, postId);
		Post post = postService.getPost(postId);
		model.addAttribute("post", post);
		return "readblog";
	}
	
	@PostMapping("/filter")
	public String filterKeySearch(@RequestParam("key") String key,Model model) {
		System.out.println(key);
		List<Post> posts = postService.getFilterPost(key);
		model.addAttribute("posts", posts);
		
		return "index";
	}
	
	
}
