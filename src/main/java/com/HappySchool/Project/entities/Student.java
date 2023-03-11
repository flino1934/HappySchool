package com.HappySchool.Project.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_students")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer matricula;
	@Column(nullable = false, length = 150)
	private String nome;
	@CPF
	@Column(nullable = false, length = 11)
	private String cpf;

	@ManyToMany(mappedBy = "student")
	private Set<Curso> curso = new HashSet<>();

	@OneToMany(mappedBy = "studentId")
	Set<Curso> cursoId = new HashSet<>();

	public Student() {
		super();
	}

	public Student(Integer matricula, String nome, String cpf, Set<Curso> curso) {
		super();
		this.matricula = matricula;
		this.nome = nome;
		this.cpf = cpf;
		this.curso = curso;
	}
	

	

	public Set<Curso> getCurso() {
		return curso;
	}

	public Set<Curso> getCursoId() {
		return cursoId;
	}

	public Integer getMatricula() {
		return matricula;
	}

	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Override
	public int hashCode() {
		return Objects.hash(matricula);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return Objects.equals(matricula, other.matricula);
	}

	@Override
	public String toString() {
		return "Student [matricula=" + matricula + ", nome=" + nome + ", cpf=" + cpf + ", curso=" + curso + "]";
	}

}
