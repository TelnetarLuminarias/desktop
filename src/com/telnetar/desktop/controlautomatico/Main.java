package com.telnetar.desktop.controlautomatico;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.configuration.ConfigurationException;

import com.telnetar.desktop.components.periods.PeriodScheduleDatatable;
import com.telnetar.desktop.components.periods.PeriodScheduleDatatableModel;
import com.telnetar.desktop.model.Periodschedule;

import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private PeriodScheduleDatatable periodScheduleDatatable;
	private GestionPeriodosPanel gestionPeriodosPanel;
	private GestionPeriodosDetallePanel gestionPeriodosDetallePanel;
	private PeriodoDetallePanel lunesPanel, martesPanel, miercolesPanel, juevesPanel, viernesPanel, sabadoPanel, domingoPanel;

	/**
	 * Create the frame.
	 * @throws ConfigurationException 
	 */
	public Main() throws ConfigurationException {
		setTitle("Control automatico");
		setBounds(10, 10, 900, 600);
		setMinimumSize(new Dimension(900, 600));
		
		contentPane = new JPanel();
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		contentPane.setLayout(new BorderLayout(0, 0));
		SpringLayout springLayout = new SpringLayout();
		contentPane.setLayout(springLayout);
		setContentPane(contentPane);
		
		JPanel tablePanel = new JPanel(new BorderLayout());
		periodScheduleDatatable = new PeriodScheduleDatatable();
		periodScheduleDatatable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ListSelectionModel rowSM = periodScheduleDatatable.getSelectionModel();
		rowSM.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				try{
					// Ignore extra messages.
					if (e.getValueIsAdjusting())
						return;
	
					ListSelectionModel lsm = (ListSelectionModel) e.getSource();
					if (lsm.isSelectionEmpty()) {
						System.out.println("No rows are selected.");
					} else {
						int rowPeriodScheduleSelect = lsm.getMinSelectionIndex();
	//					getControlPanel().setLuminariahist(((LuminariasDatatableModel)lsLuminarias.getModel()).getRowObject(selectedRow));
						Periodschedule periodschedule = 
							((PeriodScheduleDatatableModel)periodScheduleDatatable.getModel()).getRowObject(rowPeriodScheduleSelect);
						getLunesPanel().getPeriodScheduleDetailDatatable().establecerModelo(periodschedule, Calendar.MONDAY);
						getMartesPanel().getPeriodScheduleDetailDatatable().establecerModelo(periodschedule, Calendar.TUESDAY);
						getMiercolesPanel().getPeriodScheduleDetailDatatable().establecerModelo(periodschedule, Calendar.WEDNESDAY);
						getJuevesPanel().getPeriodScheduleDetailDatatable().establecerModelo(periodschedule, Calendar.THURSDAY);
						getViernesPanel().getPeriodScheduleDetailDatatable().establecerModelo(periodschedule, Calendar.FRIDAY);
						getSabadoPanel().getPeriodScheduleDetailDatatable().establecerModelo(periodschedule, Calendar.SATURDAY);
						getDomingoPanel().getPeriodScheduleDetailDatatable().establecerModelo(periodschedule, Calendar.SUNDAY);
						gestionPeriodosPanel.setPeriodschedule(periodschedule);
						gestionPeriodosPanel.setRowSelected(rowPeriodScheduleSelect);
						getGestionPeriodosDetallePanel().myUpdateUI(rowPeriodScheduleSelect, periodScheduleDatatable);
					}
				}catch(ConfigurationException ex){
					JOptionPane.showMessageDialog(null, "No es posible acceder a la configuración de la base de datos. Por favor comuniquesé con el técnico especializado.", "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(periodScheduleDatatable);
		tablePanel.add(scrollPane, BorderLayout.CENTER);
		tablePanel.add(periodScheduleDatatable.getTableHeader(), BorderLayout.NORTH);
		
//		contentPane.add(scrollPane, BorderLayout.NORTH);
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.NORTH, contentPane);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, contentPane);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, -390, SpringLayout.EAST, contentPane);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, 150, SpringLayout.NORTH, contentPane);
		contentPane.add(scrollPane);
		
		gestionPeriodosPanel = new GestionPeriodosPanel(periodScheduleDatatable);
		springLayout.putConstraint(SpringLayout.NORTH, gestionPeriodosPanel, 0, SpringLayout.NORTH, scrollPane);
		springLayout.putConstraint(SpringLayout.WEST, gestionPeriodosPanel, 5, SpringLayout.EAST, scrollPane);
		springLayout.putConstraint(SpringLayout.EAST, gestionPeriodosPanel, -10, SpringLayout.EAST, contentPane);
		springLayout.putConstraint(SpringLayout.SOUTH, gestionPeriodosPanel, 0, SpringLayout.SOUTH, scrollPane);
		contentPane.add(gestionPeriodosPanel);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		springLayout.putConstraint(SpringLayout.NORTH, tabbedPane, 5, SpringLayout.SOUTH, scrollPane);
		springLayout.putConstraint(SpringLayout.WEST, tabbedPane, 0, SpringLayout.WEST, scrollPane);
		springLayout.putConstraint(SpringLayout.EAST, tabbedPane, 0, SpringLayout.EAST, scrollPane);
		springLayout.putConstraint(SpringLayout.SOUTH, tabbedPane, -5, SpringLayout.SOUTH, contentPane);
		contentPane.add(tabbedPane);
		
		gestionPeriodosDetallePanel = new GestionPeriodosDetallePanel();
		springLayout.putConstraint(SpringLayout.NORTH, gestionPeriodosDetallePanel, 0, SpringLayout.NORTH, tabbedPane);
		springLayout.putConstraint(SpringLayout.WEST, gestionPeriodosDetallePanel, 0, SpringLayout.WEST, gestionPeriodosPanel);
		springLayout.putConstraint(SpringLayout.EAST, gestionPeriodosDetallePanel, 0, SpringLayout.EAST, gestionPeriodosPanel);
		springLayout.putConstraint(SpringLayout.SOUTH, gestionPeriodosDetallePanel, 0, SpringLayout.SOUTH, tabbedPane);
		contentPane.add(gestionPeriodosDetallePanel);
		
		setLunesPanel(new PeriodoDetallePanel(Calendar.MONDAY));
		getGestionPeriodosDetallePanel().setLunesDatatable(getLunesPanel().getPeriodScheduleDetailDatatable());
		tabbedPane.addTab("Lunes", null, getLunesPanel(), null);
		
		setMartesPanel(new PeriodoDetallePanel(Calendar.TUESDAY));
		getGestionPeriodosDetallePanel().setMartesDatatable(getMartesPanel().getPeriodScheduleDetailDatatable());
		tabbedPane.addTab("Martes", null, getMartesPanel(), null);
		
		setMiercolesPanel(new PeriodoDetallePanel(Calendar.WEDNESDAY));
		getGestionPeriodosDetallePanel().setMiercolesDatatable(getMiercolesPanel().getPeriodScheduleDetailDatatable());
		tabbedPane.addTab("Miercoles", null, getMiercolesPanel(), null);
		
		setJuevesPanel(new PeriodoDetallePanel(Calendar.THURSDAY));
		getGestionPeriodosDetallePanel().setJuevesDatatable(getJuevesPanel().getPeriodScheduleDetailDatatable());
		tabbedPane.addTab("Jueves", null, getJuevesPanel(), null);
		
		setViernesPanel(new PeriodoDetallePanel(Calendar.FRIDAY));
		getGestionPeriodosDetallePanel().setViernesDatatable(getViernesPanel().getPeriodScheduleDetailDatatable());
		tabbedPane.addTab("Viernes", null, getViernesPanel(), null);
		
		setSabadoPanel(new PeriodoDetallePanel(Calendar.SATURDAY));
		getGestionPeriodosDetallePanel().setSabadoDatatable(getSabadoPanel().getPeriodScheduleDetailDatatable());
		tabbedPane.addTab("Sábado", null, getSabadoPanel(), null);
		
		setDomingoPanel(new PeriodoDetallePanel(Calendar.SUNDAY));
		getGestionPeriodosDetallePanel().setDomingoDatatable(getDomingoPanel().getPeriodScheduleDetailDatatable());
		tabbedPane.addTab("Domingo", null, getDomingoPanel(), null);
	}

	public GestionPeriodosPanel getGestionPeriodosPanel() {
		return gestionPeriodosPanel;
	}

	public void setGestionPeriodosPanel(GestionPeriodosPanel gestionPeriodosPanel) {
		this.gestionPeriodosPanel = gestionPeriodosPanel;
	}

	public GestionPeriodosDetallePanel getGestionPeriodosDetallePanel() {
		return gestionPeriodosDetallePanel;
	}

	public void setGestionPeriodosDetallePanel(GestionPeriodosDetallePanel gestionPeriodosDetallePanel) {
		this.gestionPeriodosDetallePanel = gestionPeriodosDetallePanel;
	}

	public PeriodoDetallePanel getLunesPanel() {
		return lunesPanel;
	}

	public void setLunesPanel(PeriodoDetallePanel lunesPanel) {
		this.lunesPanel = lunesPanel;
	}

	public PeriodoDetallePanel getMartesPanel() {
		return martesPanel;
	}

	public void setMartesPanel(PeriodoDetallePanel martesPanel) {
		this.martesPanel = martesPanel;
	}

	public PeriodoDetallePanel getMiercolesPanel() {
		return miercolesPanel;
	}

	public void setMiercolesPanel(PeriodoDetallePanel miercolesPanel) {
		this.miercolesPanel = miercolesPanel;
	}

	public PeriodoDetallePanel getJuevesPanel() {
		return juevesPanel;
	}

	public void setJuevesPanel(PeriodoDetallePanel juevesPanel) {
		this.juevesPanel = juevesPanel;
	}

	public PeriodoDetallePanel getViernesPanel() {
		return viernesPanel;
	}

	public void setViernesPanel(PeriodoDetallePanel viernesPanel) {
		this.viernesPanel = viernesPanel;
	}

	public PeriodoDetallePanel getSabadoPanel() {
		return sabadoPanel;
	}

	public void setSabadoPanel(PeriodoDetallePanel sabadoPanel) {
		this.sabadoPanel = sabadoPanel;
	}

	public PeriodoDetallePanel getDomingoPanel() {
		return domingoPanel;
	}

	public void setDomingoPanel(PeriodoDetallePanel domingoPanel) {
		this.domingoPanel = domingoPanel;
	}

}
