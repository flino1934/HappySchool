package com.HappySchool.Project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HappySchool.Project.entities.Grades;
import com.HappySchool.Project.entities.pk.GradesPK;

public interface GradesRepository extends JpaRepository<Grades, GradesPK> {
	


}
