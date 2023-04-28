package com.jrtp.officeportal.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "AIT_COURSES")
@Data
public class CoursesEntity {
    @Id
	private Integer courseId;
    private String courseName;
}
