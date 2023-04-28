package com.jrtp.officeportal.service;

import java.util.List;

import com.jrtp.officeportal.binding.DashboardResponse;
import com.jrtp.officeportal.binding.EnquiryForm;
import com.jrtp.officeportal.binding.EnquirySearchCriteria;
import com.jrtp.officeportal.entity.StudentEnquiryEntity;

public interface EnquiryService {
     public List<String> getCourseNames();
     public List<String> getEnqStatus();
     public DashboardResponse getDashboardData(Integer userId);
     public String upsertEnquiry(EnquiryForm enquiryForm);
     public List<StudentEnquiryEntity> getEnquiries(Integer userId, EnquirySearchCriteria criteria);
     
     public List<StudentEnquiryEntity> getFilteredEnquires(EnquirySearchCriteria criteria);
     public EnquiryForm getenquiry(Integer enqId);
     
     public boolean update(EnquiryForm form,Integer enqId);
}
