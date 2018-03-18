package com.telnetar.desktop.threads;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.telnetar.desktop.Util;
import com.telnetar.desktop.model.L0hist;
import com.telnetar.desktop.services.L0HistService;
import com.telnetar.interfaces.RfInterface;
import com.telnetar.interfaces.core.PaqueteDto;

public class PanicoThread extends Thread {
	private RfInterface rfInterface;
	private Date horaInicio;

	public PanicoThread(RfInterface rfInterface) {
		super("Pánico thread");
		RfInterface.panicoActive = Boolean.TRUE;
		// Enviar algún mensaje a mi cuenta de email
		setRfInterface(rfInterface);
	}

	@Override
	public void run() {
		try {
			int repeticiones = 20;
			setHoraInicio(new Date());

			PaqueteDto paqueteDto = new PaqueteDto();
			paqueteDto.setHigh(new Byte((byte) 0xFF));
			paqueteDto.setLow(new Byte((byte) 0xFF));
			paqueteDto.setLength(new Byte((byte) 0x02));

			// El último mensaje lo envio con el último valor registrado por L0
			@SuppressWarnings("resource")
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			L0HistService l0HistService = (L0HistService) context.getBean("l0HistService");

			L0hist l0hist = l0HistService.obtenerUltimoRegistro();
			Integer intensity = l0hist == null ? new Integer(100) : l0hist.getIntensity();

			Integer intensidad = new Integer(0);
			for (int i = 0; i < repeticiones; i++) {
				// Intensidad a enviar
				if ((i % 2) == 0){
					intensidad = new Integer(25);
				}else{
					intensidad = new Integer(100);
				}
				int xIntensity = intensidad.equals(new Integer(0)) ? 1 : Util.getVirtualIntensity(intensidad).intValue();
				paqueteDto.setData(new Byte[] { new Byte((byte) 0xFE),
						new Byte((byte) xIntensity) });
				getRfInterface().send(paqueteDto);

				// Espero medio segundo
				Thread.sleep(500);
			}
			intensity = intensity.equals(new Long(0)) ? new Integer(1) : intensity;
			paqueteDto.setData(new Byte[] { new Byte((byte) 0xFE), new Byte((byte) intensity.intValue()) });

			getRfInterface().send(paqueteDto);
		} catch (InterruptedException e) {
			System.out.println("InterruptedException PANICO");
		} catch (Exception e) {
			System.out.println("Exception PANICO");
		} finally {
			RfInterface.panicoActive = Boolean.FALSE;
		}
	}

	public RfInterface getRfInterface() {
		return rfInterface;
	}

	public void setRfInterface(RfInterface rfInterface) {
		this.rfInterface = rfInterface;
	}

	public Date getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}
}
