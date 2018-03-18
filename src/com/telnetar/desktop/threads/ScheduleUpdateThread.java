package com.telnetar.desktop.threads;

import java.util.Calendar;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.telnetar.desktop.model.Periodschedule;
import com.telnetar.desktop.services.PeriodScheduleDetailService;
import com.telnetar.desktop.services.PeriodScheduleService;

public class ScheduleUpdateThread extends Thread {
	private ScheduleThread scheduleThread;

	public ScheduleUpdateThread(ScheduleThread scheduleThread) {
		super("Schedule Update Thread");
		setScheduleThread(scheduleThread);
	}

	public void run() {
		try {
			while (true) {
				System.out.println("update thread");
				@SuppressWarnings("resource")
				ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
				PeriodScheduleService periodScheduleService = (PeriodScheduleService) context.getBean("periodScheduleService");
				PeriodScheduleDetailService periodScheduleDetailService = (PeriodScheduleDetailService) context.getBean("periodScheduleDetailService");

				getScheduleThread().setLsActivePeriods(periodScheduleService.obtenerPeriodosVigentes());
				for (Iterator<Periodschedule> iterator = getScheduleThread().getLsActivePeriods().iterator(); iterator.hasNext();) {
					Periodschedule	periodschedule = iterator.next();
					periodschedule.setDetalleLunes(periodScheduleDetailService.obtenerDetallePeriodo(periodschedule, Calendar.MONDAY));
					periodschedule.setDetalleMartes(periodScheduleDetailService.obtenerDetallePeriodo(periodschedule, Calendar.TUESDAY));
					periodschedule.setDetalleMiercoles(periodScheduleDetailService.obtenerDetallePeriodo(periodschedule, Calendar.WEDNESDAY));
					periodschedule.setDetalleJueves(periodScheduleDetailService.obtenerDetallePeriodo(periodschedule, Calendar.THURSDAY));
					periodschedule.setDetalleViernes(periodScheduleDetailService.obtenerDetallePeriodo(periodschedule, Calendar.FRIDAY));
					periodschedule.setDetalleSabado(periodScheduleDetailService.obtenerDetallePeriodo(periodschedule, Calendar.SATURDAY));
					periodschedule.setDetalleDomingo(periodScheduleDetailService.obtenerDetallePeriodo(periodschedule, Calendar.SUNDAY));
				}
				Thread.sleep(1000 * 60 * 60 * 12);
			}
		} catch (Exception e) {
			new ScheduleUpdateThread(getScheduleThread());
		}
	}

	public ScheduleThread getScheduleThread() {
		return scheduleThread;
	}

	public void setScheduleThread(ScheduleThread scheduleThread) {
		this.scheduleThread = scheduleThread;
	}
}
