package com.telnetar.desktop.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;


/**
 * The persistent class for the perioddetailschedule database table.
 * 
 */
@Entity
@NamedQuery(name="Perioddetailschedule.findAll", query="SELECT p FROM Perioddetailschedule p")
public class Perioddetailschedule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private int day;

	private BigInteger idPeriod;

	private BigInteger initHour;

	private Integer intensity;
	
	private Boolean proccess;

	public Perioddetailschedule() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getDay() {
		return this.day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public BigInteger getIdPeriod() {
		return this.idPeriod;
	}

	public void setIdPeriod(BigInteger idPeriod) {
		this.idPeriod = idPeriod;
	}

	public BigInteger getInitHour() {
		return this.initHour;
	}

	public void setInitHour(BigInteger initHour) {
		this.initHour = initHour;
	}

	public Integer getIntensity() {
		return this.intensity;
	}

	public void setIntensity(Integer intensity) {
		this.intensity = intensity;
	}

	public Boolean getProccess() {
		return proccess;
	}

	public void setProccess(Boolean proccess) {
		this.proccess = proccess;
	}

}