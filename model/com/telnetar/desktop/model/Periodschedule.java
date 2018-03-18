package com.telnetar.desktop.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;


/**
 * The persistent class for the periodschedule database table.
 * 
 */
@Entity
@NamedQuery(name="Periodschedule.findAll", query="SELECT p FROM Periodschedule p")
public class Periodschedule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;

	private String description;

	private BigInteger idGrupoLuminaria;

	private BigInteger initDate;
	
	private List<Perioddetailschedule> detalleLunes, detalleMartes, detalleMiercoles, detalleJueves, detalleViernes, detalleSabado, detalleDomingo;

	public Periodschedule() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
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

	public BigInteger getInitDate() {
		return this.initDate;
	}

	public void setInitDate(BigInteger initDate) {
		this.initDate = initDate;
	}

	public List<Perioddetailschedule> getDetalleLunes() {
		return detalleLunes;
	}

	public void setDetalleLunes(List<Perioddetailschedule> detalleLunes) {
		this.detalleLunes = detalleLunes;
	}

	public List<Perioddetailschedule> getDetalleMartes() {
		return detalleMartes;
	}

	public void setDetalleMartes(List<Perioddetailschedule> detalleMartes) {
		this.detalleMartes = detalleMartes;
	}

	public List<Perioddetailschedule> getDetalleMiercoles() {
		return detalleMiercoles;
	}

	public void setDetalleMiercoles(List<Perioddetailschedule> detalleMiercoles) {
		this.detalleMiercoles = detalleMiercoles;
	}

	public List<Perioddetailschedule> getDetalleJueves() {
		return detalleJueves;
	}

	public void setDetalleJueves(List<Perioddetailschedule> detalleJueves) {
		this.detalleJueves = detalleJueves;
	}

	public List<Perioddetailschedule> getDetalleViernes() {
		return detalleViernes;
	}

	public void setDetalleViernes(List<Perioddetailschedule> detalleViernes) {
		this.detalleViernes = detalleViernes;
	}

	public List<Perioddetailschedule> getDetalleSabado() {
		return detalleSabado;
	}

	public void setDetalleSabado(List<Perioddetailschedule> detalleSabado) {
		this.detalleSabado = detalleSabado;
	}

	public List<Perioddetailschedule> getDetalleDomingo() {
		return detalleDomingo;
	}

	public void setDetalleDomingo(List<Perioddetailschedule> detalleDomingo) {
		this.detalleDomingo = detalleDomingo;
	}

}