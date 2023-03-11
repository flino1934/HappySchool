package com.HappySchool.Project.entities;

import java.util.ArrayList;
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
@Table(name = "tb_professor")
public class Professor {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer matricula;
	@Column(nullable = false, length = 150)
	private String nome;
	@CPF
	@Column(nullable = false, length = 11)
	private String cpf;
	@Column(nullable = false, length = 225)
	private String especialidade;

	@OneToMany(mappedBy = "professor")
	Set<Curso> curso = new HashSet<>();

	public Professor() {
		super();
	}

	

	public Professor(Integer matricula, String nome, @CPF String cpf, String especialidade, Set<Curso> curso) {
		super();
		this.matricula = matricula;
		this.nome = nome;
		this.cpf = cpf;
		this.especialidade = especialidade;
		this.curso = curso;
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

	public String getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}

	public Set<Curso> getCurso() {
		return curso;
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
		Professor other = (Professor) obj;
		return Objects.equals(matricula, other.matricula);
	}

	@Override
	public String toString() {
		return "Professor [matricula=" + matricula + ", nome=" + nome + ", cpf=" + cpf + ", especialidade="
				+ especialidade + "]";
	}

}
