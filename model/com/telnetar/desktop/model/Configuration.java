package com.telnetar.desktop.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the configurations database table.
 * 
 */
@Entity
@Table(name="configurations")
@NamedQuery(name="Configuration.findAll", query="SELECT c FROM Configuration c")
public class Configuration implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="configuration_id")
	private String configurationId;

	private int delay;

	private int frecuency;

	private Timestamp fromdate;

	private int intensityMax;

	private int intensityMin;

	private Integer l0integrado;

	private Timestamp lightoff;

	private Timestamp lighton;

	private Integer nodejsport;

	private String nodejsserver;

	private String nodejstype;

	private String nodename;

	private String puertoComL0;

	private String puertoComMaster;

	public Configuration() {
	}

	public String getConfigurationId() {
		return this.configurationId;
	}

	public void setConfigurationId(String configurationId) {
		this.configurationId = configurationId;
	}

	public int getDelay() {
		return this.delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public int getFrecuency() {
		return this.frecuency;
	}

	public void setFrecuency(int frecuency) {
		this.frecuency = frecuency;
	}

	public Timestamp getFromdate() {
		return this.fromdate;
	}

	public void setFromdate(Timestamp fromdate) {
		this.fromdate = fromdate;
	}

	public int getIntensityMax() {
		return this.intensityMax;
	}

	public void setIntensityMax(int intensityMax) {
		this.intensityMax = intensityMax;
	}

	public int getIntensityMin() {
		return this.intensityMin;
	}

	public void setIntensityMin(int intensityMin) {
		this.intensityMin = intensityMin;
	}

	public Integer getL0integrado() {
		return this.l0integrado;
	}

	public void setL0integrado(Integer l0integrado) {
		this.l0integrado = l0integrado;
	}

	public Timestamp getLightoff() {
		return this.lightoff;
	}

	public void setLightoff(Timestamp lightoff) {
		this.lightoff = lightoff;
	}

	public Timestamp getLighton() {
		return this.lighton;
	}

	public void setLighton(Timestamp lighton) {
		this.lighton = lighton;
	}

	public Integer getNodejsport() {
		return this.nodejsport;
	}

	public void setNodejsport(Integer nodejsport) {
		this.nodejsport = nodejsport;
	}

	public String getNodejsserver() {
		return this.nodejsserver;
	}

	public void setNodejsserver(String nodejsserver) {
		this.nodejsserver = nodejsserver;
	}

	public String getNodejstype() {
		return this.nodejstype;
	}

	public void setNodejstype(String nodejstype) {
		this.nodejstype = nodejstype;
	}

	public String getNodename() {
		return this.nodename;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}

	public String getPuertoComL0() {
		return this.puertoComL0;
	}

	public void setPuertoComL0(String puertoComL0) {
		this.puertoComL0 = puertoComL0;
	}

	public String getPuertoComMaster() {
		return this.puertoComMaster;
	}

	public void setPuertoComMaster(String puertoComMaster) {
		this.puertoComMaster = puertoComMaster;
	}

}