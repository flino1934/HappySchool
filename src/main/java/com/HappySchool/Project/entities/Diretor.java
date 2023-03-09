package com.HappySchool.Project.entities;

import java.util.Objects;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_diretor")
public class Diretor {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer matricula;
	@Column(nullable = false, length = 150)
	private String nome;
	@CPF
	@Column(nullable = false)
	private String cpf;
	
	
	public Diretor() {
		
	}
	
	
	public Diretor(Integer matricula, String nome, String cpf) {
		super();
		this.matricula = matricula;
		this.nome = nome;
		this.cpf = cpf;
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
		Diretor other = (Diretor) obj;
		return Objects.equals(matricula, other.matricula);
	}


	@Override
	public String toString() {
		return "Diretor [matricula=" + matricula + ", nome=" + nome + ", cpf=" + cpf + "]";
	}


	
	
	
	
	
	

}
