package com.HappySchool.Project.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_students")
public class Student implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long matricula;
	@Column(nullable = false, length = 150)
	private String nome;
	@CPF
	@Column(nullable = false, length = 11)
	private String cpf;

	@OneToMany(mappedBy = "id.student")
	private Set<Grades> grades = new HashSet<>();

	public Student() {
		super();
	}
	
	

	public Student(Long matricula) {
		super();
		this.matricula = matricula;
	}



	public Student(Long matricula, String nome, @CPF String cpf) {
		super();
		this.matricula = matricula;
		this.nome = nome;
		this.cpf = cpf;

	}

	public Long getMatricula() {
		return matricula;
	}

	public void setMatricula(Long matricula) {
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

	public Set<Grades> getGrades() {
		return grades;
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
		return "Student [matricula=" + matricula + ", nome=" + nome + ", cpf=" + cpf + "]";
	}

}
