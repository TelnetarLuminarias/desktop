package com.telnetar.desktop.threads;

import java.util.Iterator;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.telnetar.desktop.model.Luminaria;
import com.telnetar.desktop.services.LuminariaService;
import com.telnetar.interfaces.L0Interface;
import com.telnetar.interfaces.RfInterface;
import com.telnetar.interfaces.core.PaqueteDto;

public class DaemonThread extends Thread {
	private RfInterface rfInterface;
	private L0Interface l0Interface;
	private int intervalo = 300;
	private List<Luminaria> lsLuminarias;

	public DaemonThread(RfInterface rfInterface) {
		super("Daemon thread");
		setRfInterface(rfInterface);
	}

	public DaemonThread(RfInterface rfInterface, L0Interface l0Interface) {
		super("Daemon thread");
		setRfInterface(rfInterface);
		setL0Interface(l0Interface);
	}

	public void run() {
		try {
			@SuppressWarnings("resource")
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			LuminariaService luminariaService = (LuminariaService) context.getBean("luminariaService");
			Luminaria l0 = luminariaService.buscarL0();
			getRfInterface().setL0(l0);
			setLsLuminarias(luminariaService.obtenerLuminarias());
			while (true) {
				// Audito el L0
				if (getRfInterface().getConfiguration().getL0integrado().equals(new Integer(1))) {
					if (getL0Interface() != null) {
						PaqueteDto paqueteDto = new PaqueteDto(new Byte((byte) 0xFF), new Byte((byte) 0xFF),
								new Byte((byte) 0x02), new Byte[] { (new Byte((byte) 0x00)), new Byte((byte) 0x00) });
						getL0Interface().getConnection().send(paqueteDto.getMessage());
						Thread.sleep(intervalo);
					}
				} else {
					if(l0 == null){
						l0 = luminariaService.buscarL0();
					}else{
						PaqueteDto paqueteDto = new PaqueteDto(new Byte(l0.getId().getHightbyte().byteValue()),
								new Byte(l0.getId().getLowbyte().byteValue()), new Byte((byte) 0x02),
								new Byte[] { (new Byte((byte) 0x00)), new Byte((byte) 0x00) });
						getRfInterface().send(paqueteDto);
						Thread.sleep(intervalo);
					}
				}
				// Audito las luminarias
				for (Iterator<Luminaria> iterator = getLsLuminarias().iterator(); iterator.hasNext();) {
					Luminaria luminaria = iterator.next();
					PaqueteDto paqueteDto = new PaqueteDto(new Byte(luminaria.getId().getHightbyte().byteValue()),
							new Byte(luminaria.getId().getLowbyte().byteValue()), new Byte((byte) 0x02),
							new Byte[] { (new Byte((byte) 0x00)), new Byte((byte) 0x00) });
					getRfInterface().send(paqueteDto);
					Thread.sleep(intervalo);
				}

				Thread.sleep((1000 * 60 * getRfInterface().getConfiguration().getFrecuency())
						- (getLsLuminarias().size() * intervalo));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			new DaemonThread(getRfInterface(), getL0Interface());
		}
	}

	public RfInterface getRfInterface() {
		return rfInterface;
	}

	public void setRfInterface(RfInterface rfInterface) {
		this.rfInterface = rfInterface;
	}

	public L0Interface getL0Interface() {
		return l0Interface;
	}

	public void setL0Interface(L0Interface l0Interface) {
		this.l0Interface = l0Interface;
	}

	public List<Luminaria> getLsLuminarias() {
		return lsLuminarias;
	}

	public void setLsLuminarias(List<Luminaria> lsLuminarias) {
		this.lsLuminarias = lsLuminarias;
	}

}
