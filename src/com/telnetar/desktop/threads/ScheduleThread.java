package com.telnetar.desktop.threads;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.telnetar.desktop.Util;
import com.telnetar.desktop.model.Luminaria;
import com.telnetar.desktop.model.Perioddetailschedule;
import com.telnetar.desktop.model.Periodschedule;
import com.telnetar.desktop.services.LuminariaService;
import com.telnetar.interfaces.RfInterface;
import com.telnetar.interfaces.core.PaqueteDto;

public class ScheduleThread extends Thread {
	private RfInterface rfInterface;
	private List<Periodschedule> lsActivePeriods;

	public ScheduleThread(RfInterface rfInterface) {
		super("Schedule thread");
		ScheduleUpdateThread scheduleUpdateThread = new ScheduleUpdateThread(this);
		scheduleUpdateThread.start();
		RfInterface.panicoActive = Boolean.FALSE;
		// Enviar algún mensaje a mi cuenta de email
		setRfInterface(rfInterface);
	}

	@Override
	public void run() {
		try {
			@SuppressWarnings("resource")
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			LuminariaService luminariaService = (LuminariaService) context.getBean("luminariaService");
			while (true) {
				if (getLsActivePeriods() != null) {
					// Si hay un solo periodo vigente y este no pertenece a
					// ningún grupo, hago un broadcasting
					if (getLsActivePeriods().size() == 1 && getLsActivePeriods().get(0).getIdGrupoLuminaria() == null) {
						Perioddetailschedule periodDetailSchedule = findActualPeriodDetail(getLsActivePeriods().get(0));
						if (periodDetailSchedule != null && (periodDetailSchedule.getProccess() == null || !periodDetailSchedule.getProccess())) {
							PaqueteDto paqueteDto = new PaqueteDto();
							paqueteDto.setHigh(new Byte((byte) 0xFF));
							paqueteDto.setLow(new Byte((byte) 0xFF));
							paqueteDto.setLength(new Byte((byte) 0x02));
							Integer intensity = periodDetailSchedule.getIntensity().compareTo(new Integer(1)) > 0
								&& periodDetailSchedule.getIntensity().compareTo(new Integer(7)) < 0
									? new Integer(7) : periodDetailSchedule.getIntensity();
							paqueteDto.setData(
								new Byte[] { 
									new Byte((byte) 0xFE),
									new Byte((byte) intensity.intValue()) 
								}
							);

							getRfInterface().send(paqueteDto);

							periodDetailSchedule.setProccess(Boolean.TRUE);
						}
					} else {
						// Caso contrario, recorro los periodos vigentes y voy
						// configurando la intensidad a cada una de las
						// luminarias
						for (Iterator<Periodschedule> iterator = getLsActivePeriods().iterator(); iterator.hasNext();) {
							Periodschedule periodSchedule = iterator.next();
							Perioddetailschedule periodDetailSchedule = findActualPeriodDetail(periodSchedule);
							if (periodDetailSchedule != null && !periodDetailSchedule.getProccess()) {
								
								List<Luminaria> lsLuminarias = luminariaService.obtenerLuminariasPorGrupo(periodSchedule.getIdGrupoLuminaria().longValue());
								
								for (Iterator<Luminaria> iterator2 = lsLuminarias.iterator(); iterator2.hasNext();) {
									Luminaria luminaria = iterator2.next();
									PaqueteDto paqueteDto = new PaqueteDto();
									paqueteDto.setHigh(new Byte(luminaria.getId().getHightbyte().byteValue()));
									paqueteDto.setLow(new Byte(luminaria.getId().getLowbyte().byteValue()));
									paqueteDto.setLength(new Byte((byte) 0x02));
									Integer intensity = periodDetailSchedule.getIntensity()
											.compareTo(new Integer(1)) > 0
											&& periodDetailSchedule.getIntensity().compareTo(new Integer(7)) < 0
													? new Integer(7) : periodDetailSchedule.getIntensity();
									int xIntensity = intensity.equals(new Integer(0)) ? 1 : Util.getVirtualIntensity(intensity).intValue(); 
									paqueteDto.setData(new Byte[] { new Byte((byte) 0xFE),
											new Byte((byte) xIntensity) });

									getRfInterface().send(paqueteDto);
									Thread.sleep(600);
								}
								periodDetailSchedule.setProccess(Boolean.TRUE);
							}
						}
					}
					Thread.sleep(1000 * 60 * 1);
				} else {
					Thread.sleep(1000 * 60 * 60 * 12);
				}
				// PeriodScheduleDto periodScheduleDto = findActualPeriod();
				// if(periodScheduleDto != null){
				// PeriodDetailScheduleDto periodDetailScheduleDto =
				// findActualPeriodDetail(periodScheduleDto);
				// if(periodDetailScheduleDto != null &&
				// !periodDetailScheduleDto.getProccess()){
				// PaqueteDto paqueteDto = new PaqueteDto();
				// paqueteDto.setHigh(new Byte((byte) 0xFF));
				// paqueteDto.setLow(new Byte((byte) 0xFF));
				// paqueteDto.setLength(new Byte((byte) 0x02));
				// paqueteDto.setData(new Byte[] {new Byte((byte) 0xFE),new
				// Byte((byte)
				// Util.obtainCustomIntensity(periodDetailScheduleDto.getIntensity()))
				// });
				//
				// getRfInterface().send(paqueteDto);
				//
				// periodDetailScheduleDto.setProccess(Boolean.TRUE);
				// }
				// }
				// Thread.sleep(1000*60*1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			new ScheduleThread(getRfInterface());
		}
	}

	private Perioddetailschedule findActualPeriodDetail(Periodschedule periodSchedule) throws ParseException {
		Perioddetailschedule period1 = null;
		Perioddetailschedule period2 = null;
		
		List<Perioddetailschedule> lsDetalle = null;
		Calendar cal = Calendar.getInstance();
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		switch (dayOfWeek) {
		case Calendar.SUNDAY:
			lsDetalle = periodSchedule.getDetalleSabado();
			break;
		case Calendar.MONDAY:
			lsDetalle = periodSchedule.getDetalleLunes();
			break; 
		case Calendar.TUESDAY:
			lsDetalle = periodSchedule.getDetalleMartes();
			break;
		case Calendar.WEDNESDAY:
			lsDetalle = periodSchedule.getDetalleMiercoles();
			break;
		case Calendar.THURSDAY:
			lsDetalle = periodSchedule.getDetalleJueves();
			break;
		case Calendar.FRIDAY:
			lsDetalle = periodSchedule.getDetalleViernes();
			break;
		case Calendar.SATURDAY:
			lsDetalle = periodSchedule.getDetalleSabado();
			break;
		default:
			break;
		}
		
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		Date horaActual = df.parse(df.format(new Date()));
		
		for (Iterator<Perioddetailschedule> iterator = lsDetalle.iterator(); iterator.hasNext();) {
			period1 = iterator.next();
			if (horaActual.after(df.parse(df.format(period1.getInitHour()))))
				period2 = period1;
		}
		return period2;
	}

	public RfInterface getRfInterface() {
		return rfInterface;
	}

	public void setRfInterface(RfInterface rfInterface) {
		this.rfInterface = rfInterface;
	}

	public List<Periodschedule> getLsActivePeriods() {
		return lsActivePeriods;
	}

	public void setLsActivePeriods(List<Periodschedule> lsActivePeriods) {
		this.lsActivePeriods = lsActivePeriods;
	}
}
