package com.telnetar.interfaces.core;

public class PaqueteDto {
    private Byte high, low;
    private Byte length;
    private Byte[] data = new Byte[32];

    public PaqueteDto() {
    }

    public PaqueteDto(Byte hight, Byte low, Byte lenght, Byte[] data) {
	setHigh(hight);
	setLow(low);
	setLength(lenght);
	setData(data);
    }

    public Byte getHigh() {
	return high;
    }

    public void setHigh(Byte high) {
	this.high = high;
    }

    public Byte getLow() {
	return low;
    }

    public void setLow(Byte low) {
	this.low = low;
    }

    public Byte getLength() {
	return length;
    }

    public void setLength(Byte length) {
	this.length = length;
    }

    public Byte[] getData() {
	return data;
    }

    public void setData(Byte[] data) {
	this.data = data;
    }

    @Override
    public String toString() {
	String string = "Paquete | ";
	if (getHigh() == (byte) 0xFF && getLow() == (byte) 0xFF) {
	    string += "Broadcast";
	} else {
	    string += "High: " + getHigh();
	    string += " - Low: " + getLow();
	}
	string += " - Length: " + getLength();
	for (int i = 0; i < getData().length; i++) {
	    Byte b = getData()[i];
	    if (b != null) {
		string += " - Data " + i + ": " + b;
	    }
	}
	return string;
    }

    public byte[] getMessage() {
	byte[] msg = new byte[3+getData().length];
	msg[0] = getHigh();
	msg[1] = getLow();
	msg[2] = getLength();
	for (int i = 0; i < getData().length; i++) {
	    Byte b = getData()[i];
	    if (b != null) {
		msg[3 + i] = b;
	    }
	}
	return msg;
    }
}