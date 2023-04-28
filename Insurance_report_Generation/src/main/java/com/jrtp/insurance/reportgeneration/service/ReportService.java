package com.jrtp.insurance.reportgeneration.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.jrtp.insurance.reportgeneration.entity.CitizenPlan;
import com.jrtp.insurance.reportgeneration.request.SearchRequest;

public interface ReportService {
    public List<String> getPlanNames();
    public List<String> getPlanStatuses();
    public List<CitizenPlan> serch(SearchRequest searchRequest);
    public boolean exportPdf();
    public boolean exportExcel(HttpServletResponse response) throws Exception;
}
