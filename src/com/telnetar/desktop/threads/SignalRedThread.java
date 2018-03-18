package com.telnetar.desktop.threads;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.telnetar.desktop.Util;
import com.telnetar.interfaces.RfInterface;
import com.telnetar.interfaces.core.PaqueteDto;

public class SignalRedThread extends Thread {
    private RfInterface rfInterface;
    
    public SignalRedThread(RfInterface rfInterface){
        super("Apagado thread");
        setRfInterface(rfInterface);
    }
    
    @Override
    public void run() {
	try{
	    Boolean primerSeñarDeRed = Boolean.TRUE;
	    while(true){
		DateFormat df = new SimpleDateFormat("HH:mm");
		Date horaInicio = df.parse(df.format(new Date()));
		Date horaEncendido = df.parse(df.format(getRfInterface().getConfiguration().getLighton()));
		Date horaApagado = df.parse(df.format(getRfInterface().getConfiguration().getLightoff()));

		Boolean dia = horaInicio.after(horaApagado) && horaInicio.before(horaEncendido);

		if(dia && Util.addToDate(getRfInterface().getLastPackageRedReceived(), 0, 0, 0, 25).compareTo(new Date()) < 0){
		    PaqueteDto paqueteDto = new PaqueteDto();
		    paqueteDto.setHigh(new Byte((byte) 0xFF));
		    paqueteDto.setLow(new Byte((byte) 0xFF));
		    paqueteDto.setLength(new Byte((byte) 0x02));
		    paqueteDto.setData(new Byte[] { new Byte((byte) 0xED), new Byte((byte) 0x00) });

		    getRfInterface().send(paqueteDto);
		    
		    if(primerSeñarDeRed){
			Thread.sleep(500);
			paqueteDto.setData(new Byte[] { new Byte((byte) 0xFE), new Byte((byte) 0x00) });
			getRfInterface().send(paqueteDto);
			primerSeñarDeRed = Boolean.FALSE;
		    }
		}else{
		    primerSeñarDeRed = Boolean.TRUE;
		}
		Thread.sleep(1000 * 15);
	    }
	}catch(Exception e){
	}
    }

    public RfInterface getRfInterface() {
        return rfInterface;
    }

    public void setRfInterface(RfInterface rfInterface) {
        this.rfInterface = rfInterface;
    }
}
