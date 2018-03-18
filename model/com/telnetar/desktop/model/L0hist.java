package com.telnetar.desktop.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the l0hist database table.
 * 
 */
@Entity
@NamedQuery(name="L0hist.findAll", query="SELECT l FROM L0hist l")
public class L0hist implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	private Integer intensity;

	public L0hist() {
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Integer getIntensity() {
		return this.intensity;
	}

	public void setIntensity(Integer intensity) {
		this.intensity = intensity;
	}

}