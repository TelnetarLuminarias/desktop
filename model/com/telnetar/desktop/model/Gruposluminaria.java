package com.telnetar.desktop.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the gruposluminarias database table.
 * 
 */
@Entity
@Table(name="gruposluminarias")
@NamedQuery(name="Gruposluminaria.findAll", query="SELECT g FROM Gruposluminaria g")
public class Gruposluminaria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idGrupoLuminaria;

	private String descripcion;

	public Gruposluminaria() {
	}

	public String getIdGrupoLuminaria() {
		return this.idGrupoLuminaria;
	}

	public void setIdGrupoLuminaria(String idGrupoLuminaria) {
		this.idGrupoLuminaria = idGrupoLuminaria;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}