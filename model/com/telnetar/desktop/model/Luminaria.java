package com.telnetar.desktop.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;


/**
 * The persistent class for the luminarias database table.
 * 
 */
@Entity
@Table(name="luminarias")
@NamedQuery(name="Luminaria.findAll", query="SELECT l FROM Luminaria l")
public class Luminaria implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LuminariaPK id;

	private String description;

	private BigInteger idGrupoLuminaria;

	private Integer isNew;

	public Luminaria() {
	}
	public Luminaria(LuminariaPK pk, String descripcion){
		this.id = pk;
		this.description = descripcion;
	}

	public LuminariaPK getId() {
		return this.id;
	}

	public void setId(LuminariaPK id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigInteger getIdGrupoLuminaria() {
		return this.idGrupoLuminaria;
	}

	public void setIdGrupoLuminaria(BigInteger idGrupoLuminaria) {
		this.idGrupoLuminaria = idGrupoLuminaria;
	}

	public Integer getIsNew() {
		return this.isNew;
	}

	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}

}