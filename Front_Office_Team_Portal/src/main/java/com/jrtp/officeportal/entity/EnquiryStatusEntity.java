package com.jrtp.officeportal.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "AIT_ENQUIRY_STATUS")
@Data
public class EnquiryStatusEntity {
    @Id
	private Integer statusId;
	private String statusName;
}
