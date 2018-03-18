package com.telnetar.desktop.components.periods;

import java.util.Date;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.apache.commons.configuration.ConfigurationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.telnetar.desktop.components.DateRenderer;
import com.telnetar.desktop.services.PeriodScheduleService;

public class PeriodScheduleDatatable extends JTable {

	private static final long serialVersionUID = 1L;
	private ApplicationContext context;
	
	public PeriodScheduleDatatable() throws ConfigurationException {
		context = new ClassPathXmlApplicationContext("Beans.xml");

		PeriodScheduleService periodScheduleService = (PeriodScheduleService) context.getBean("periodScheduleService");

		super.setModel(new PeriodScheduleDatatableModel(periodScheduleService.obtenerPeriodosVigentes()));

		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getModel().addTableModelListener(this);
		setDefaultRenderer(Date.class, new DateRenderer());
		setAutoCreateRowSorter(true);
	}
	
	
}
