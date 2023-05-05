package com.jrtp.blogapp.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jrtp.blogapp.binding.AddPostForm;
import com.jrtp.blogapp.binding.EditPostForm;
import com.jrtp.blogapp.entity.Comments;
import com.jrtp.blogapp.entity.Post;
import com.jrtp.blogapp.service.PostService;


@Controller
public class PostController {
	
    @Autowired
	private PostService postService;
    
    @Autowired
    private HttpSession session;
	
	
	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {
		Integer userId = (Integer) session.getAttribute("userId");
		List<Post> posts = postService.getAllUserPost(userId);
		
		model.addAttribute("posts", posts);
		
		return "dashboard";
	}
	
	@GetMapping("/addpost")
	public String addPostPage(Model model) {
		
		model.addAttribute("newPost", new AddPostForm());
		return "newpost";
	}
	
	
	@PostMapping("/addpost")
	public String addPost(@ModelAttribute("newPost") AddPostForm form,Model model) {
		
		Integer userId = (Integer) session.getAttribute("userId");
		boolean status = postService.addPost(form, userId);
		System.out.println(form);
		return "redirect:/dashboard";
	}
	
	@GetMapping("/comment")
	public String viewComment(Model model) {
		Integer userId = (Integer) session.getAttribute("userId");
		Post post = postService.getPost(userId);
		List<Comments> comments = postService.getComments(post.getPostId());
		model.addAttribute("comments", comments);
		return "comment_page";
	}
	
	
	@GetMapping("/comments")
	public String viewAllComments(Model model) {
		
		List<Comments> comments = postService.allComments();
		model.addAttribute("comments", comments);
		return "comment_page";
	}
	
	@GetMapping("/edit/{id}")
	public String editPostPage(@PathVariable("id") Integer postId, @ModelAttribute("post") EditPostForm form,Model model) {
		Post post = postService.getPost(postId);
		model.addAttribute("post", post);
		return "edit_post";
	}
	
	@PostMapping("/editpost")
	public String editPost(@ModelAttribute("post") EditPostForm form,@RequestParam("postid") Integer postId) {
		Integer userId = (Integer) session.getAttribute("userId");
		boolean status = postService.editPost(form, userId);
		System.out.println(form);
		return "redirect:/dashboard";
	}
}
