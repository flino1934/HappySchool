package com.HappySchool.Project.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_curso")
public class Curso {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String nome;
	private String descricao;
	private Double nota;

	@ManyToMany
	@JoinTable(name = "tb_cursos", joinColumns = @JoinColumn(name = "curso_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
	private Set<Student> student = new HashSet<>();

	@ManyToOne
	@JoinColumn(name="professor_id")
	private Professor professor;
	
	@ManyToOne
	@JoinColumn(name="student_id")
	private Student studentId;

	

	public Curso(Integer id, String nome, String descricao, Double nota, Set<Student> student, Professor professor) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.nota = nota;
		this.student = student;
		this.professor = professor;
	}
	
	

	public Curso(Integer id, String nome, String descricao, Double nota, Professor professor, Student studentId) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.nota = nota;
		this.professor = professor;
		this.studentId = studentId;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getNota() {
		return nota;
	}

	public void setNota(Double nota) {
		this.nota = nota;
	}

	public Set<Student> getStudent() {
		return student;
	}

	public void setStudent(Set<Student> student) {
		this.student = student;
	}

	public Professor getProfessor() {
		return professor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Curso other = (Curso) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Curso [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", nota=" + nota + ", student="
				+ student + ", professor=" + professor + "]";
	}

}
