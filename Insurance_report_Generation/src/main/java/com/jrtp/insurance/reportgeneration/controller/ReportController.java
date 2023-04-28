package com.jrtp.insurance.reportgeneration.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jrtp.insurance.reportgeneration.request.SearchRequest;
import com.jrtp.insurance.reportgeneration.service.ReportService;

@Controller
public class ReportController {
    
	@Autowired
	private ReportService reportService;
	
	@RequestMapping("/home")
	public String home(Model model) {
		model.addAttribute("searchRequest",new SearchRequest() );
		init(model);
		return "index";
	}
	
	@PostMapping("/search")
	public String handleSearch(@ModelAttribute("searchRequest") SearchRequest searchRequest,Model model) {
		model.addAttribute("plans",reportService.serch(searchRequest));
		init(model);
		return "index";
	}
	
	public void init(Model model) {
		model.addAttribute("names", reportService.getPlanNames());
		model.addAttribute("status",reportService.getPlanStatuses());
	}
	
	@GetMapping("/excel")
	public void excelExport(HttpServletResponse response) throws Exception {
		response.setContentType("application/octet-stream");
		response.addHeader("Content-Disposition", "attachment;filename=plans.xls");
		reportService.exportExcel(response);
		
	}
}
