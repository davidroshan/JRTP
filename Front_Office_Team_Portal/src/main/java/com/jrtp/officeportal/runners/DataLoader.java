package com.jrtp.officeportal.runners;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.jrtp.officeportal.entity.CoursesEntity;
import com.jrtp.officeportal.entity.EnquiryStatusEntity;
import com.jrtp.officeportal.repository.CourseRepo;
import com.jrtp.officeportal.repository.EnquiryStatusRepo;

@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private EnquiryStatusRepo enqRepo;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		courseRepo.deleteAll();
		
		CoursesEntity cEntity1 = new CoursesEntity();
        cEntity1.setCourseId(1);
        cEntity1.setCourseName("Java");
        
        CoursesEntity cEntity2 = new CoursesEntity();
        cEntity2.setCourseId(2);
        cEntity2.setCourseName("Python");
		
        CoursesEntity cEntity3 = new CoursesEntity();
        cEntity3.setCourseId(3);
        cEntity3.setCourseName("DevOps");
        
        CoursesEntity cEntity4 = new CoursesEntity();
        cEntity4.setCourseId(4);
        cEntity4.setCourseName("JavaScript");
        
        courseRepo.saveAll(Arrays.asList(cEntity1,cEntity2,cEntity3,cEntity4));
        
        enqRepo.deleteAll();
        
        EnquiryStatusEntity eEntity1 = new EnquiryStatusEntity();
        eEntity1.setStatusId(1);
        eEntity1.setStatusName("New");
        
        EnquiryStatusEntity eEntity2 = new EnquiryStatusEntity();
        eEntity2.setStatusId(2);
        eEntity2.setStatusName("Enrolled");
        
        EnquiryStatusEntity eEntity3 = new EnquiryStatusEntity();
        eEntity3.setStatusId(3);
        eEntity3.setStatusName("Lost");
        
        enqRepo.saveAll(Arrays.asList(eEntity1,eEntity2,eEntity3));
	}

}
