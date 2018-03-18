package com.telnetar.node.model;

public class ChanelPanicButtonDataDto {
    private Integer hightByte, lowByte;
    private String nodo;
    
    public ChanelPanicButtonDataDto(Integer hightByte, Integer lowByte,
	    String nodo) {
	super();
	this.hightByte = hightByte;
	this.lowByte = lowByte;
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
    public String getNodo() {
        return nodo;
    }
    public void setNodo(String nodo) {
        this.nodo = nodo;
    }
}
