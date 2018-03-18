package com.telnetar.node.model;

import com.google.gson.Gson;
import com.telnetar.desktop.model.Roomtemperaturehist;

public class UpdateRoomTemperatureData {
    private Long fecha;
    private Integer tempHight, tempLow;
    private String nodoId;

    public UpdateRoomTemperatureData(Roomtemperaturehist roomTemperatureHist, String nodoId) {
//	Gson gson = new Gson();
	this.fecha = roomTemperatureHist.getId().getFecha().getTime()/1000;
	this.tempHight = roomTemperatureHist.getTemphight();
	this.tempLow = roomTemperatureHist.getTemplow();
	this.nodoId = nodoId;
    }
    
    public String getData(){
	Gson gson = new Gson();
	return gson.toJson(this);
    }

    public String getNodoId() {
        return nodoId;
    }

    public void setNodoId(String nodoId) {
        this.nodoId = nodoId;
    }

    public Long getFecha() {
        return fecha;
    }

    public void setFecha(Long fecha) {
        this.fecha = fecha;
    }

    public Integer getTempHight() {
        return tempHight;
    }

    public void setTempHight(Integer tempHight) {
        this.tempHight = tempHight;
    }

    public Integer getTempLow() {
        return tempLow;
    }

    public void setTempLow(Integer tempLow) {
        this.tempLow = tempLow;
    }
}
