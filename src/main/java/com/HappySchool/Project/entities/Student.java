package com.HappySchool.Project.entities;

import java.io.Serializable;
import java.util.Objects;

<<<<<<< HEAD
import jakarta.persistence.Column;
=======
>>>>>>> 93eb11438814bf161cbfb30cb4c1265a779c0c0e
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

<<<<<<< HEAD


=======
>>>>>>> 93eb11438814bf161cbfb30cb4c1265a779c0c0e
@Entity
@Table(name = "tb_students")
public class Student implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
<<<<<<< HEAD
	private Integer matricula;
	@Column(nullable = false, length = 150)
	private String nome;
	
	

	public Student() {
		super();
	}

	public Student(Integer matricula, String nome) {
=======
	private Long matricula;
	private String nome;

	public Student(Long matricula, String nome) {
>>>>>>> 93eb11438814bf161cbfb30cb4c1265a779c0c0e
		super();
		this.matricula = matricula;
		this.nome = nome;
	}

<<<<<<< HEAD
	public Integer getMatricula() {
		return matricula;
	}

	public void setMatricula(Integer matricula) {
=======
	public Long getMatricula() {
		return matricula;
	}

	public void setMatricula(Long matricula) {
>>>>>>> 93eb11438814bf161cbfb30cb4c1265a779c0c0e
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
		return "Student [matricula=" + matricula + ", nome=" + nome + "]";
	}

}
