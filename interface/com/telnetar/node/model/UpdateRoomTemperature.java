package com.telnetar.node.model;

import com.google.gson.Gson;

public class UpdateRoomTemperature {
    private UpdateRoomTemperatureData roomTemperatureData;

    public UpdateRoomTemperature(UpdateRoomTemperatureData roomTemperatureData){
	this.roomTemperatureData = roomTemperatureData;
    }
    
    public String getData(){
	Gson gson = new Gson();
	return gson.toJson(this);
    }
    
    public UpdateRoomTemperatureData getRoomTemperatureData() {
        return roomTemperatureData;
    }

    public void setRoomTemperatureData(UpdateRoomTemperatureData roomTemperatureData) {
        this.roomTemperatureData = roomTemperatureData;
    }
}
