package com.telnetar.desktop.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the luminariahist database table.
 * 
 */
@Entity
@NamedQuery(name="Luminariahist.s", query="SELECT l FROM Luminariahist l")
public class Luminariahist implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LuminariahistPK id;

	private int intensity;

	private int lumiContextH;

	private int lumiContextL;

	private Timestamp sincronized;

	private int temperatureHight;

	private int temperatureLow;

	public Luminariahist() {
	}
	public Luminariahist(LuminariahistPK id, int intensity){
		this.id = id;
		this.intensity = intensity;
	}

	public LuminariahistPK getId() {
		return this.id;
	}

	public void setId(LuminariahistPK id) {
		this.id = id;
	}

	public int getIntensity() {
		return this.intensity;
	}

	public void setIntensity(int intensity) {
		this.intensity = intensity;
	}

	public int getLumiContextH() {
		return this.lumiContextH;
	}

	public void setLumiContextH(int lumiContextH) {
		this.lumiContextH = lumiContextH;
	}

	public int getLumiContextL() {
		return this.lumiContextL;
	}

	public void setLumiContextL(int lumiContextL) {
		this.lumiContextL = lumiContextL;
	}

	public Timestamp getSincronized() {
		return this.sincronized;
	}

	public void setSincronized(Timestamp sincronized) {
		this.sincronized = sincronized;
	}

	public int getTemperatureHight() {
		return this.temperatureHight;
	}

	public void setTemperatureHight(int temperatureHight) {
		this.temperatureHight = temperatureHight;
	}

	public int getTemperatureLow() {
		return this.temperatureLow;
	}

	public void setTemperatureLow(int temperatureLow) {
		this.temperatureLow = temperatureLow;
	}

}