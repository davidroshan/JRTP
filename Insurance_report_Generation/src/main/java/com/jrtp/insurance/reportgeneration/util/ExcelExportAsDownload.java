package com.jrtp.insurance.reportgeneration.util;


import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import com.jrtp.insurance.reportgeneration.entity.CitizenPlan;

@Component
public class ExcelExportAsDownload {
     
	public boolean excelExportDownload(List<CitizenPlan> citizenPlanList,HttpServletResponse response,File f) throws Exception {
		
		int dataRowIndex = 1;
		
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("plans-data");
		Row headerRow = sheet.createRow(0);
		
		headerRow.createCell(0).setCellValue("ID");
		headerRow.createCell(1).setCellValue("Citizen Name");
		headerRow.createCell(2).setCellValue("Plan Name");
		headerRow.createCell(3).setCellValue("Plan Status");
		headerRow.createCell(4).setCellValue("Plan Start Date");
		headerRow.createCell(5).setCellValue("Plan End Date");
		headerRow.createCell(6).setCellValue("Benefit Amount");
		
		
		for(CitizenPlan citizenPlan:citizenPlanList) {
			Row dataRow = sheet.createRow(dataRowIndex);
			dataRow.createCell(0).setCellValue(citizenPlan.getCitizenId());
			dataRow.createCell(1).setCellValue(citizenPlan.getCitizenName());
			dataRow.createCell(2).setCellValue(citizenPlan.getPlanName());
			dataRow.createCell(3).setCellValue(citizenPlan.getPlanStatus());
			if(null != citizenPlan.getPlanStartdate()) {
			      dataRow.createCell(4).setCellValue(citizenPlan.getPlanStartdate());
			}else {
				dataRow.createCell(4).setCellValue("N/A");  
			}
			if(null != citizenPlan.getPlanEndDate()) {
			      dataRow.createCell(5).setCellValue(citizenPlan.getPlanEndDate());
			}else {
				dataRow.createCell(5).setCellValue("N/A");  
			}
			if(null != citizenPlan.getBenefitAmt()) {
			      dataRow.createCell(6).setCellValue(citizenPlan.getBenefitAmt());
			}else {
				dataRow.createCell(6).setCellValue("N/A");
			}
			dataRowIndex++;
		}
		
		FileOutputStream fStream = new FileOutputStream(f);
		workbook.write(fStream);
		fStream.close();
		
		ServletOutputStream stream = response.getOutputStream();
		workbook.write(stream);
		workbook.close();
		return true;
	}
}
