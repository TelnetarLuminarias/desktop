package com.telnetar.desktop.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * The primary key class for the luminariahist database table.
 * 
 */
@Embeddable
public class LuminariahistPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer hightbyte;

	private Integer lowbyte;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	public LuminariahistPK() {
	}
	public LuminariahistPK(Integer hightbyte, Integer lowbyte, Date fecha){
		this.hightbyte = hightbyte;
		this.lowbyte = lowbyte;
		this.fecha = fecha; 
	}
	public Integer getHightbyte() {
		return this.hightbyte;
	}
	public void setHightbyte(Integer hightbyte) {
		this.hightbyte = hightbyte;
	}
	public Integer getLowbyte() {
		return this.lowbyte;
	}
	public void setLowbyte(Integer lowbyte) {
		this.lowbyte = lowbyte;
	}
	public java.util.Date getFecha() {
		return this.fecha;
	}
	public void setFecha(java.util.Date fecha) {
		this.fecha = fecha;
	}
	public String getPk(){
	    return getHightbyte() + " - " + getLowbyte();
	}
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof LuminariahistPK)) {
			return false;
		}
		LuminariahistPK castOther = (LuminariahistPK)other;
		return 
			(this.hightbyte == castOther.hightbyte)
			&& (this.lowbyte == castOther.lowbyte)
			&& this.fecha.equals(castOther.fecha);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.hightbyte;
		hash = hash * prime + this.lowbyte;
		hash = hash * prime + this.fecha.hashCode();
		
		return hash;
	}
}