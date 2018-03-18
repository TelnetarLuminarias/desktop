package com.telnetar.desktop.components.periods;

import java.util.Date;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.apache.commons.configuration.ConfigurationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.telnetar.desktop.components.DateRenderer;
import com.telnetar.desktop.model.Periodschedule;
import com.telnetar.desktop.services.PeriodScheduleDetailService;

public class PeriodScheduleDetailDatatable extends JTable {

	private static final long serialVersionUID = 1L;
	private ApplicationContext context;
	private Periodschedule periodschedule;
	private int day;
	
	public PeriodScheduleDetailDatatable(){}
	
	public PeriodScheduleDetailDatatable(Periodschedule periodschedule, int day) throws ConfigurationException {
		establecerModelo(periodschedule, day);
	}

	public void establecerModelo(Periodschedule periodschedule, int day) throws ConfigurationException {
		setPeriodschedule(periodschedule);
		context = new ClassPathXmlApplicationContext("Beans.xml");

		PeriodScheduleDetailService periodScheduleDetailService = (PeriodScheduleDetailService) context.getBean("periodScheduleDetailService");

		super.setModel(new PeriodScheduleDetailDatatableModel(periodScheduleDetailService.obtenerDetallePeriodo(getPeriodschedule(), day)));

		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getModel().addTableModelListener(this);
		setDefaultRenderer(Date.class, new DateRenderer());
		setAutoCreateRowSorter(true);
	}

	public Periodschedule getPeriodschedule() {
		return periodschedule;
	}

	public void setPeriodschedule(Periodschedule periodschedule) {
		this.periodschedule = periodschedule;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}
	
	
}
