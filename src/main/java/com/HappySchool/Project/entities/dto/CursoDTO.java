package com.HappySchool.Project.entities.dto;

public class CursoDTO {

	private String nome;
	private String descricao;
	private Integer professorId;

	public CursoDTO() {
		super();
	}

	public CursoDTO(String nome, String descricao, Integer professorId) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.professorId = professorId;
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

	public Integer getProfessorId() {
		return professorId;
	}

	public void setProfessorId(Integer professorId) {
		this.professorId = professorId;
	}

}
