package com.jrtp.insurance.reportgeneration.service;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.jrtp.insurance.reportgeneration.entity.CitizenPlan;
import com.jrtp.insurance.reportgeneration.repository.CitizenPlanRepository;
import com.jrtp.insurance.reportgeneration.request.SearchRequest;
import com.jrtp.insurance.reportgeneration.util.EmailUtil;
import com.jrtp.insurance.reportgeneration.util.ExcelExportAsDownload;


@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	private CitizenPlanRepository repository;
    
	@Autowired
	private ExcelExportAsDownload excelexport;
	
	@Autowired
	private EmailUtil emailUtil;
	
	@Override
	public List<String> getPlanNames() {
		return repository.getPlanNames();
	}

	@Override
	public List<String> getPlanStatuses() {
		return repository.getPlanStatus();
	}

	@Override
	public List<CitizenPlan> serch(SearchRequest searchRequest) {
		System.out.println(searchRequest.getPlanName());
		System.out.println(searchRequest.getPlanStatus());
		System.out.println(searchRequest);
		CitizenPlan entity = new CitizenPlan();
		System.out.println(entity.getCitizenName());
		//BeanUtils.copyProperties(searchRequest, entity);
		if(null !=searchRequest.getPlanName() && !"".equals(searchRequest.getPlanName())) {
			entity.setPlanName(searchRequest.getPlanName());
		}
		
		if(null !=searchRequest.getPlanStatus()&& !"".equals(searchRequest.getPlanStatus())) {
			entity.setPlanStatus(searchRequest.getPlanStatus());
		}
		if(null !=searchRequest.getGender() && !"".equals(searchRequest.getGender())) {
			entity.setGender(searchRequest.getGender());
		}
		System.out.println(entity);
		List<CitizenPlan> list=repository.findAll(Example.of(entity));
		System.out.println(list);
		//return repository.findAll(Example.of(entity));
		return list;
	}

	@Override
	public boolean exportPdf() {
		
		return false;
	}

	@Override
	public boolean exportExcel(HttpServletResponse response)throws Exception {
		File f = new File("plans.xls");
		List<CitizenPlan> citizenPlanList = repository.findAll();
		boolean result = excelexport.excelExportDownload(citizenPlanList,response,f);
		
		String subject = "Test mail subject";
		String body = "<h1>Test mail body</h1>";
		String to = "davidroshanphotography@gmail.com";
		
		emailUtil.sendEmail(subject, body, to, f);
		
		f.delete();
		return result;
	}

}
