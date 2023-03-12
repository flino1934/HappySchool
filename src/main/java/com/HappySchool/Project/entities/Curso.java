package com.HappySchool.Project.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_curso")
public class Curso {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String nome;
	private String descricao;

	@ManyToOne
	@JoinColumn(name = "professor_id")
	private Professor professor;

	@OneToMany(mappedBy = "id.curso")
	private Set<Grades> grades = new HashSet<>();

	public Curso() {

	}

	public Curso(Integer id, String nome, String descricao, Professor professor) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.professor = professor;
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

	public Professor getProfessor() {
		return professor;
	}

	public Set<Grades> getGrades() {
		return grades;
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
		return "Curso [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", nota=" + ", professor="
				+ professor + "]";
	}

}
