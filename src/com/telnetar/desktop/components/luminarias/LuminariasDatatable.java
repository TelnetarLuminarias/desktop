package com.telnetar.desktop.components.luminarias;

import java.util.Date;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.apache.commons.configuration.ConfigurationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.telnetar.desktop.services.LuminariahistService;
import com.telnetar.desktop.components.DateRenderer;

public class LuminariasDatatable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("resource")
	public LuminariasDatatable() throws ConfigurationException {
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		LuminariahistService luminariahistService = (LuminariahistService) context.getBean("luminariahistService");

		super.setModel(new LuminariasDatatableModel(luminariahistService.obtenerUltimosRegistrosPorLuminarias()));

		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getModel().addTableModelListener(this);
		setDefaultRenderer(Date.class, new DateRenderer());
		setAutoCreateRowSorter(true);
	}

}
