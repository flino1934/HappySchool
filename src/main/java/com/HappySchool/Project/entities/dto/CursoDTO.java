package com.HappySchool.Project.entities.dto;

public class CursoDTO {
	
	private Integer id;
	private String nome;
	private String descricao;
	private Integer professorId;
	
	
	
	public CursoDTO(Integer id, String nome, String descricao, Integer professorId) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.professorId = professorId;
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
	public Integer getProfessorId() {
		return professorId;
	}
	public void setProfessor_id(Integer professorId) {
		this.professorId = professorId;
	}
	
	
	

}
