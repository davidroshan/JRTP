package com.jrtp.officeportal.service.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jrtp.officeportal.binding.DashboardResponse;
import com.jrtp.officeportal.binding.EnquiryForm;
import com.jrtp.officeportal.binding.EnquirySearchCriteria;
import com.jrtp.officeportal.entity.CoursesEntity;
import com.jrtp.officeportal.entity.EnquiryStatusEntity;
import com.jrtp.officeportal.entity.StudentEnquiryEntity;
import com.jrtp.officeportal.entity.UserDtlsEntity;
import com.jrtp.officeportal.repository.CourseRepo;
import com.jrtp.officeportal.repository.EnquiryStatusRepo;
import com.jrtp.officeportal.repository.StudentEnquiryRepo;
import com.jrtp.officeportal.repository.UserDtlsRepo;
import com.jrtp.officeportal.service.EnquiryService;

@Service
public class EnquiryServiceImpl implements EnquiryService {
	
	@Autowired
	private UserDtlsRepo userRepository;
	
	@Autowired
	private CourseRepo cRepo;
	
	@Autowired
	private EnquiryStatusRepo eRepo;
	
	@Autowired
	private StudentEnquiryRepo studentRepo;
	
	@Autowired
	HttpSession session;

	@Override
	public List<String> getCourseNames() {
		
		List<CoursesEntity> courseEntityList = cRepo.findAll();
		List<String> courses = new ArrayList<>();
		
		for(CoursesEntity entity:courseEntityList) {
			courses.add(entity.getCourseName());
		}
		return courses;
	}

	@Override
	public List<String> getEnqStatus() {
		List<EnquiryStatusEntity> enquiryStatusList = eRepo.findAll();
		List<String> enquiryStatus = new ArrayList<>();
		
		for(EnquiryStatusEntity enqEntity : enquiryStatusList) {
			enquiryStatus.add(enqEntity.getStatusName());
		}
		return enquiryStatus;
	}

	@Override
	public DashboardResponse getDashboardData(Integer userId) {
		
		DashboardResponse dashboardResponse = new DashboardResponse();
		
		Optional<UserDtlsEntity> findById = userRepository.findById(userId);
		if(findById.isPresent()) {
			UserDtlsEntity user = findById.get();
			List<StudentEnquiryEntity> enquires = user.getEnquires();
			
			int totalEnquries = enquires.size();
			    
			int lostEnquiry = enquires.stream()	
			          .filter(enquiry->enquiry.getEnqStatus().equals("Lost"))
			          .collect(Collectors.toList()).size();
			
			int enrolledEnquiry = enquires.stream()
					.filter(enquiry->enquiry.getEnqStatus().equals("Enrolled"))
					.collect(Collectors.toList()).size();
			
			dashboardResponse.setTotalEnquires(totalEnquries);
			dashboardResponse.setLostCnt(lostEnquiry);
			dashboardResponse.setEnrolledCnt(enrolledEnquiry);
		}
		
		return dashboardResponse;
	}

	@Override
	public String upsertEnquiry(EnquiryForm enquiryForm) {
		
		System.out.println(enquiryForm);
		StudentEnquiryEntity studentEntity = new StudentEnquiryEntity();
		
		BeanUtils.copyProperties(enquiryForm, studentEntity);
		
		Integer userId = (Integer) session.getAttribute("userId");
		UserDtlsEntity userEntity = userRepository.findById(userId).get();
		studentEntity.setUser(userEntity);
		
		
		
		studentRepo.save(studentEntity);
		return "success";
	}

	@Override
	public List<StudentEnquiryEntity> getEnquiries(Integer userId, EnquirySearchCriteria criteria) {
		UserDtlsEntity entity = userRepository.findById(userId).get();
		List<StudentEnquiryEntity> enquires = entity.getEnquires();
		return enquires;
	}

	@Override
	public EnquiryForm getenquiry(Integer enqId) {
		StudentEnquiryEntity studentEntity = studentRepo.findById(enqId).get();
		EnquiryForm form = new EnquiryForm();
		BeanUtils.copyProperties(studentEntity, form);
		return form;
	}

	@Override
	public List<StudentEnquiryEntity> getFilteredEnquires(EnquirySearchCriteria criteria) {
		
		Integer userId = (Integer) session.getAttribute("userId");
		UserDtlsEntity user = userRepository.findById(userId).get();
		List<StudentEnquiryEntity> enquires = user.getEnquires();
		
		if(criteria.getCourseName()!= null && !criteria.getCourseName().equals("")) {
			enquires = enquires.stream()
			        .filter(enquiry->enquiry.getCourseName().equals(criteria.getCourseName()))
			        .collect(Collectors.toList());
		}
		
		if(criteria.getEnqStatus()!=null && !criteria.getEnqStatus().equals("")) {
			enquires = enquires.stream()
					.filter(enquiry->enquiry.getEnqStatus().equals(criteria.getEnqStatus()))
					.collect(Collectors.toList());
		}
		
		if(criteria.getClassMode()!=null && !criteria.getClassMode().equals("")) {
			enquires = enquires.stream()
					.filter(enquiry->enquiry.getClassMode().equals(criteria.getClassMode()))
					.collect(Collectors.toList());
		}
		
		return enquires;
	}

	@Override
	public boolean update(EnquiryForm form, Integer enqId) {
		StudentEnquiryEntity studentEntity = studentRepo.findById(enqId).get();
		
        if(form.getStudentName()!=null && !form.getStudentName().equals("")) {
			studentEntity.setStudentName(form.getStudentName());
		}
		
        if(form.getStudentPhno()!=null && !form.getStudentPhno().equals("")) {
			studentEntity.setStudentPhno(form.getStudentPhno());
		}
        
		if(form.getClassMode()!=null && !form.getClassMode().equals("")) {
			studentEntity.setClassMode(form.getClassMode());
		}
		
		if(form.getCourseName()!=null && !form.getCourseName().equals("")) {
			studentEntity.setCourseName(form.getCourseName());
		}
		
		if(form.getEnqStatus()!=null && !form.getEnqStatus().equals("")) {
			studentEntity.setEnqStatus(form.getEnqStatus());
		}
		
		studentRepo.save(studentEntity);
		return true;
	}

}
