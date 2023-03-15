package com.HappySchool.Project.entities.dto;

public class GradesDTO {

	private Long StudentId;
	private Integer CourseId;
	private Double grades;

	public GradesDTO(Long studentId, Integer courseId, Double grades) {
		super();
		StudentId = studentId;
		CourseId = courseId;
		this.grades = grades;
	}

	public Long getStudentId() {
		return StudentId;
	}

	public void setStudentId(Long studentId) {
		StudentId = studentId;
	}

	public Integer getCourseId() {
		return CourseId;
	}

	public void setCourseId(Integer courseId) {
		CourseId = courseId;
	}

	public Double getGrades() {
		return grades;
	}

	public void setGrades(Double grades) {
		this.grades = grades;
	}

}
