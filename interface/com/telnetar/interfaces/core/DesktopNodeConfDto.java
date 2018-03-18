package com.telnetar.interfaces.core;

public class DesktopNodeConfDto {
    private String id;
    private String type;
    
    public DesktopNodeConfDto() {
	super();
    }
    public DesktopNodeConfDto(String id, String type) {
	super();
	this.id = id;
	this.type = type;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
