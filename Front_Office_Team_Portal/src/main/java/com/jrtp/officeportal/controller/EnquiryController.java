package com.jrtp.officeportal.controller;

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

import com.jrtp.officeportal.binding.DashboardResponse;
import com.jrtp.officeportal.binding.EnquiryForm;
import com.jrtp.officeportal.binding.EnquirySearchCriteria;
import com.jrtp.officeportal.entity.StudentEnquiryEntity;
import com.jrtp.officeportal.service.EnquiryService;

@Controller
public class EnquiryController {
    
	@Autowired
	HttpSession session;
	
	@Autowired
	EnquiryService enqService;
	
	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {
		
		Object obj =  session.getAttribute("userId");
        if(obj == null) {
        	return "login";
        }
		
		Integer userId = (Integer)obj;
		System.out.println("userId:"+userId);
		DashboardResponse dashboardResponse = enqService.getDashboardData(userId);
		model.addAttribute("dasboardData", dashboardResponse);
		return "dashboard";
	}
	
	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model) {
		List<String> courses = enqService.getCourseNames();
		List<String> enquiryStatus = enqService.getEnqStatus();
		model.addAttribute("courses", courses);
		model.addAttribute("enqStatus", enquiryStatus);
		return "add-enquiry";
	}
	
	@PostMapping("/enquiry")
	public String addEnquiry(@ModelAttribute("student") EnquiryForm form,Model model) {
		System.out.println(form.getStudentPhno());
		String status = enqService.upsertEnquiry(form);
		if(status.equals("success")) {
		    model.addAttribute("succMsg", "student added successfully");
		}else {
		    model.addAttribute("errMsg", "Something went wrong");
		    }
		return "add-enquiry";
	}
	
	
	
	
	@GetMapping("/enquires")
	public String viewEnquiryPage(@ModelAttribute("enqForm") EnquirySearchCriteria criteria, Model model) {
		
		initform(model);
	    Integer userId = (Integer) session.getAttribute("userId");	
		
		List<StudentEnquiryEntity> studentEnq = enqService.getEnquiries(userId, criteria);
		model.addAttribute("enquires", studentEnq);
		return "view-enquires";
	}
	
	
	public void initform(Model model) {
		List<String> courses = enqService.getCourseNames();
		List<String> enqStatus = enqService.getEnqStatus();
		EnquirySearchCriteria form = new EnquirySearchCriteria();
		
		model.addAttribute("courses", courses);
		model.addAttribute("enqStatus", enqStatus);
		model.addAttribute("enqForm", form);
	}
	
	
	@GetMapping("/filter-enquires")
	public String getFilteredEnquires(@RequestParam("cname") String course,@RequestParam("status") String enqStatus,
			@RequestParam("mode") String classMode,Model model) {
		EnquirySearchCriteria criteria = new EnquirySearchCriteria();
		criteria.setCourseName(course);
		criteria.setEnqStatus(enqStatus);
		criteria.setClassMode(classMode);
		List<StudentEnquiryEntity> enquires = enqService.getFilteredEnquires(criteria);
		model.addAttribute("enquires", enquires);
		return "filter-enquires-page";
	}
	
	@GetMapping("/edit/{id}")
	public String getEditPage(@PathVariable("id") String userId,Model model) {
		System.out.println("Edit UserId:"+userId);
		EnquiryForm form = enqService.getenquiry(Integer.valueOf(userId));
		List<String> courses = enqService.getCourseNames();
		List<String> enqStatus = enqService.getEnqStatus();
		
		model.addAttribute("courses", courses);
		model.addAttribute("enqStatus", enqStatus);
		model.addAttribute("student", form);
		model.addAttribute("enqId", Integer.valueOf(userId));
		return "update-enquiry";
	}
	
	
	@PostMapping("/update")
	public String update(@ModelAttribute("student") EnquiryForm form,@RequestParam("enqId") Integer enqId,Model model) {
		boolean status = enqService.update(form, enqId);
		if(status) {
			return "redirect:/dashboard";
		}
		else{
			model.addAttribute("errMsg", "Something went wrong...Plz update again");
			return "update-enquiry";
		}
	}
	
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "index";
	}
}
