package com.telnetar.node.model;

import com.google.gson.Gson;

public class ChanelPanicButtonDto {
    private String data;
    private String sendToWeb;
    private String[] target;

    public ChanelPanicButtonDto(ChanelPanicButtonDataDto chanelPanicoDataDto, String sendToWeb){
	Gson gson = new Gson();
	this.data = gson.toJson(chanelPanicoDataDto);
	this.sendToWeb = sendToWeb;
    }
    
    public ChanelPanicButtonDto(ChanelPanicButtonDataDto chanelPanicoDataDto, String sendToWeb, String[] targets){
	Gson gson = new Gson();
	this.data = gson.toJson(chanelPanicoDataDto);
	this.sendToWeb = sendToWeb;
	this.target = targets;
    }
    
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSendToWeb() {
        return sendToWeb;
    }

    public void setSendToWeb(String sendToWeb) {
        this.sendToWeb = sendToWeb;
    }

    public String[] getTarget() {
        return target;
    }

    public void setTarget(String[] target) {
        this.target = target;
    }
}
