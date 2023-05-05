package com.jrtp.blogapp.binding;

import lombok.Data;

@Data
public class EditPostForm {
   
	private String title;
	private String description;
	private String content;
}
