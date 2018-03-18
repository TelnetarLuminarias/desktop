package com.telnetar.interfaces.core;

public enum Message {
    SET_INTENSITY(new Byte((byte)0xFE)),
    ROOM_TEMPERATURE(new Byte((byte)0xFD)),
    PANIC(new Byte((byte)0xFF)), 
    ERROR_FROM_LIGHTING(new Byte((byte)0xEE)), 
    AUDIT_RESPONSE(new Byte((byte)0x00)),
    SIGNAL_RED(new Byte((byte)0xED));
    
    Byte code;
    
    Message(Byte pByte){
	setCode(pByte);
    }

    public Byte getCode() {
        return code;
    }

    public void setCode(Byte code) {
        this.code = code;
    }
}
