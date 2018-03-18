package com.telnetar.node.model;

import com.google.gson.Gson;
import com.telnetar.desktop.model.Luminariahist;

public class UpdateDataDto {
    private String data;
    private String sendToWeb;
    private String[] target;

    public UpdateDataDto(UpdateDataLumiHistDto updateDataLumiHistDto, String sendToWeb){
	Gson gson = new Gson();
	this.data = gson.toJson(updateDataLumiHistDto);
	this.sendToWeb = sendToWeb;
    }
    public UpdateDataDto(UpdateDataLumiHistDto updateDataLumiHistDto) {
	Gson gson = new Gson();
	this.data = gson.toJson(updateDataLumiHistDto);
    }
    public UpdateDataDto(UpdateDataLumiHistDto updateDataLumiHistDto, String sendToWeb, String[] targets){
	Gson gson = new Gson();
	this.data = gson.toJson(updateDataLumiHistDto);
	this.sendToWeb = sendToWeb;
	this.target = targets;
    }
    
    public UpdateDataDto(Luminariahist luminariaHist) {
	Gson gson = new Gson();
	this.data = gson.toJson(luminariaHist);
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
