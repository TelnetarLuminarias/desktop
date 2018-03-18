package com.telnetar.node.model;

import com.telnetar.desktop.model.Luminariahist;

public class UpdateDataLumiHistDto {
    private Integer hightByte, lowByte;
    private Long fecha;
    private Integer intensity;
    private Integer temperatureHight, temperatureLow;
    private Integer lumiContextH, lumiContextL;
    private String nodo;
    
    public UpdateDataLumiHistDto(Luminariahist luminariaHist){
	this.hightByte = luminariaHist.getId().getHightbyte();
	this.lowByte = luminariaHist.getId().getLowbyte();
	this.fecha = new Long(luminariaHist.getId().getFecha().getTime());
	this.intensity = luminariaHist.getIntensity();
	this.temperatureHight = luminariaHist.getTemperatureHight();
	this.temperatureLow = luminariaHist.getTemperatureLow();
	this.lumiContextH = luminariaHist.getLumiContextH();
	this.lumiContextL = luminariaHist.getLumiContextL();
    }
    public UpdateDataLumiHistDto(Integer hightByte, Integer lowByte,
	    Long fecha, Integer intensity, Integer temperatureHight,
	    Integer temperatureLow, Integer lumiContextH, Integer lumiContextL) {
	super();
	this.hightByte = hightByte;
	this.lowByte = lowByte;
	this.fecha = fecha;
	this.intensity = intensity;
	this.temperatureHight = temperatureHight;
	this.temperatureLow = temperatureLow;
	this.lumiContextH = lumiContextH;
	this.lumiContextL = lumiContextL;
    }
    public UpdateDataLumiHistDto(Integer hightByte, Integer lowByte,
	    Long fecha, Integer intensity, Integer temperatureHight,
	    Integer temperatureLow, Integer lumiContextH, Integer lumiContextL, String nodo) {
	super();
	this.hightByte = hightByte;
	this.lowByte = lowByte;
	this.fecha = fecha;
	this.intensity = intensity;
	this.temperatureHight = temperatureHight;
	this.temperatureLow = temperatureLow;
	this.lumiContextH = lumiContextH;
	this.lumiContextL = lumiContextL;
	this.nodo = nodo;
    }
    public UpdateDataLumiHistDto(Luminariahist luminariaHist, String nodo) {
	super();
	this.hightByte = luminariaHist.getId().getHightbyte();
	this.lowByte = luminariaHist.getId().getLowbyte();
	this.fecha = new Long(luminariaHist.getId().getFecha().getTime())/1000;
	this.intensity = luminariaHist.getIntensity();
	this.temperatureHight = luminariaHist.getTemperatureHight();
	this.temperatureLow = luminariaHist.getTemperatureLow();
	this.lumiContextH = luminariaHist.getLumiContextH();
	this.lumiContextL = luminariaHist.getLumiContextL();
	this.nodo = nodo;
    }
    public Integer getHightByte() {
        return hightByte;
    }
    public void setHightByte(Integer hightByte) {
        this.hightByte = hightByte;
    }
    public Integer getLowByte() {
        return lowByte;
    }
    public void setLowByte(Integer lowByte) {
        this.lowByte = lowByte;
    }
    public Long getFecha() {
        return fecha;
    }
    public void setFecha(Long fecha) {
        this.fecha = fecha;
    }
    public Integer getIntensity() {
        return intensity;
    }
    public void setIntensity(Integer intensity) {
        this.intensity = intensity;
    }
    public Integer getTemperatureHight() {
        return temperatureHight;
    }
    public void setTemperatureHight(Integer temperatureHight) {
        this.temperatureHight = temperatureHight;
    }
    public Integer getTemperatureLow() {
        return temperatureLow;
    }
    public void setTemperatureLow(Integer temperatureLow) {
        this.temperatureLow = temperatureLow;
    }
    public Integer getLumiContextH() {
        return lumiContextH;
    }
    public void setLumiContextH(Integer lumiContextH) {
        this.lumiContextH = lumiContextH;
    }
    public Integer getLumiContextL() {
        return lumiContextL;
    }
    public void setLumiContextL(Integer lumiContextL) {
        this.lumiContextL = lumiContextL;
    }
    public String getNodo() {
        return nodo;
    }
    public void setNodo(String nodo) {
        this.nodo = nodo;
    }
}
