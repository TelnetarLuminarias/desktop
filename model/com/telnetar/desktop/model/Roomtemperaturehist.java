package com.telnetar.desktop.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the roomtemperaturehist database table.
 * 
 */
@Entity
@NamedQuery(name="Roomtemperaturehist.findAll", query="SELECT r FROM Roomtemperaturehist r")
public class Roomtemperaturehist implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RoomtemperaturehistPK id;

	private Timestamp sincronized;

	private int temphight;

	private int templow;

	public Roomtemperaturehist() {
	}

	public RoomtemperaturehistPK getId() {
		return this.id;
	}

	public void setId(RoomtemperaturehistPK id) {
		this.id = id;
	}

	public Timestamp getSincronized() {
		return this.sincronized;
	}

	public void setSincronized(Timestamp sincronized) {
		this.sincronized = sincronized;
	}

	public int getTemphight() {
		return this.temphight;
	}

	public void setTemphight(int temphight) {
		this.temphight = temphight;
	}

	public int getTemplow() {
		return this.templow;
	}

	public void setTemplow(int templow) {
		this.templow = templow;
	}

}