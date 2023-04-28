package com.jrtp.officeportal.binding;

import lombok.Data;

@Data
public class DashboardResponse {
    private Integer totalEnquires;
    private Integer enrolledCnt;
    private Integer lostCnt;
}
