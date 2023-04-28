package com.jrtp.officeportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jrtp.officeportal.entity.CoursesEntity;

public interface CourseRepo extends JpaRepository<CoursesEntity, Integer> {

}
