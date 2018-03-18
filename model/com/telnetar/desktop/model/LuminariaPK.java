package com.telnetar.desktop.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the luminarias database table.
 * 
 */
@Embeddable
public class LuminariaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer hightbyte;

	private Integer lowbyte;

	public LuminariaPK() {
	}
	public LuminariaPK(Integer hightbyte, Integer lowbyte){
		this.hightbyte = hightbyte;
		this.lowbyte = lowbyte;
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof LuminariaPK)) {
			return false;
		}
		LuminariaPK castOther = (LuminariaPK)other;
		return 
			(this.hightbyte == castOther.hightbyte)
			&& (this.lowbyte == castOther.lowbyte);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.hightbyte;
		hash = hash * prime + this.lowbyte;
		
		return hash;
	}
}