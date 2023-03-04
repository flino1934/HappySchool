package com.HappySchool.Project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HappySchool.Project.entities.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
