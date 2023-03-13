package com.HappySchool.Project.entities.dto;

public class GradesDTO {

	private Integer StudentId;
	private Integer CourseId;
	private Double grades;

	public GradesDTO(Integer studentId, Integer courseId, Double grades) {
		super();
		StudentId = studentId;
		CourseId = courseId;
		this.grades = grades;
	}

	public Integer getStudentId() {
		return StudentId;
	}

	public void setStudentId(Integer studentId) {
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
