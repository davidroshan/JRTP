package com.jrtp.insurance.reportgeneration.loader;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.jrtp.insurance.reportgeneration.entity.CitizenPlan;
import com.jrtp.insurance.reportgeneration.repository.CitizenPlanRepository;

@Component
public class DataLoader implements ApplicationRunner{
    @Autowired
	private CitizenPlanRepository repo;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		repo.deleteAll();
		
		//cash plan data
		CitizenPlan c1 = new CitizenPlan();
		c1.setCitizenName("John");
		c1.setGender("Male");
		c1.setPlanName("Cash");
		c1.setPlanStatus("Approved");
		c1.setPlanStartdate(LocalDate.now());
		c1.setPlanEndDate(LocalDate.now().plusMonths(6));
		c1.setBenefitAmt(5000.00);
		
		CitizenPlan c2 = new CitizenPlan();
		c2.setCitizenName("Smith");
		c2.setGender("Male");
		c2.setPlanName("Cash");
		c2.setPlanStatus("Denied");
		c2.setDenialReason("Rental Income");
		
		CitizenPlan c3 = new CitizenPlan();
		c3.setCitizenName("Cathy");
		c3.setGender("Fe-Male");
		c3.setPlanName("Cash");
		c3.setPlanStatus("Terminated");
		c3.setPlanStartdate(LocalDate.now());
		c3.setPlanEndDate(LocalDate.now().plusMonths(6));
		c3.setBenefitAmt(5000.00);
		c3.setTerminatedDate(LocalDate.now());
		c3.setTerminationReason("Employed");
		
		
		//food plan data
		CitizenPlan c4 = new CitizenPlan();
		c4.setCitizenName("Daniel");
		c4.setGender("Male");
		c4.setPlanName("Food");
		c4.setPlanStatus("Approved");
		c4.setPlanStartdate(LocalDate.now());
		c4.setPlanEndDate(LocalDate.now().plusMonths(6));
		c4.setBenefitAmt(6000.00);
				
		CitizenPlan c5 = new CitizenPlan();
		c5.setCitizenName("Tom");
		c5.setGender("Male");
		c5.setPlanName("Food");
		c5.setPlanStatus("Denied");
		c5.setDenialReason("Rental Income");
				
		CitizenPlan c6 = new CitizenPlan();
		c6.setCitizenName("Merlin");
		c6.setGender("Fe-Male");
		c6.setPlanName("Food");
		c6.setPlanStatus("Terminated");
		c6.setPlanStartdate(LocalDate.now());
		c6.setPlanEndDate(LocalDate.now().plusMonths(6));
		c6.setBenefitAmt(6000.00);
		c6.setTerminatedDate(LocalDate.now());
		c6.setTerminationReason("Employed");
		
		
		//medical plan data
		CitizenPlan c7 = new CitizenPlan();
		c7.setCitizenName("Tim");
		c7.setGender("Male");
		c7.setPlanName("Medical");
		c7.setPlanStatus("Approved");
		c7.setPlanStartdate(LocalDate.now());
		c7.setPlanEndDate(LocalDate.now().plusMonths(6));
		c7.setBenefitAmt(8000.00);
				
		CitizenPlan c8 = new CitizenPlan();
		c8.setCitizenName("Stuart");
		c8.setGender("Male");
		c8.setPlanName("Medical");
		c8.setPlanStatus("Denied");
		c8.setDenialReason("Rental Income");
				
		CitizenPlan c9 = new CitizenPlan();
		c9.setCitizenName("Sara");
		c9.setGender("Fe-Male");
		c9.setPlanName("Medical");
		c9.setPlanStatus("Terminated");
		c9.setPlanStartdate(LocalDate.now());
		c9.setPlanEndDate(LocalDate.now().plusMonths(6));
		c9.setBenefitAmt(5000.00);
		c9.setTerminatedDate(LocalDate.now());
		c9.setTerminationReason("Employed");
		
		
		//Employement plan data
		CitizenPlan c10 = new CitizenPlan();
		c10.setCitizenName("David");
		c10.setGender("Male");
		c10.setPlanName("Employement");
		c10.setPlanStatus("Approved");
		c10.setPlanStartdate(LocalDate.now());
		c10.setPlanEndDate(LocalDate.now().plusMonths(6));
		c10.setBenefitAmt(5000.00);
				
		CitizenPlan c11 = new CitizenPlan();
		c11.setCitizenName("Ricky");
		c11.setGender("Male");
		c11.setPlanName("Employement");
		c11.setPlanStatus("Denied");
		c11.setDenialReason("Rental Income");
				
		CitizenPlan c12 = new CitizenPlan();
		c12.setCitizenName("Dakota");
		c12.setGender("Fe-Male");
		c12.setPlanName("Employement");
		c12.setPlanStatus("Terminated");
		c12.setPlanStartdate(LocalDate.now());
		c12.setPlanEndDate(LocalDate.now().plusMonths(6));
		c12.setBenefitAmt(5000.00);
		c12.setTerminatedDate(LocalDate.now());
		c12.setTerminationReason("Govt Job");
		
		
		List<CitizenPlan> list = Arrays.asList(c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12);
		repo.saveAll(list);
		
		
	}

}
