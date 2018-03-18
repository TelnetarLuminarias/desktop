package com.telnetar.interfaces.core;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;
import java.io.InputStream;

public abstract class SerialReader implements SerialPortEventListener {
    private InputStream in;

    public SerialReader(InputStream in) {
	this.in = in;
    }

    @Override
    public void serialEvent(SerialPortEvent arg0) {
	int data;

	try {
	    PaqueteDto paqueteDto = new PaqueteDto();
	    int len = 0;
	    int indice = 0;
	    while ((data = in.read()) > -1) {
		// if (!(data == '\n')) {
		switch (indice) {
		case 0:
		    paqueteDto.setHigh(new Byte((byte) data));
		    break;
		case 1:
		    paqueteDto.setLow(new Byte((byte) data));
		    break;
		case 2:
		    paqueteDto.setLength(new Byte((byte) data));
		    break;
		default:
		    paqueteDto.getData()[len++] = new Byte((byte) data);
		    break;
		}
		indice++;
		if (paqueteDto.getLength() != null
			&& paqueteDto.getLength() == len) {
		    onDataReceived(paqueteDto);
		    paqueteDto = new PaqueteDto();
		    len = 0;
		    indice = 0;
		}
		// }
	    }
	} catch (IOException e) {
	    System.exit(-1);
	}
    }

    public abstract void onDataReceived(PaqueteDto paqueteDto);
}
