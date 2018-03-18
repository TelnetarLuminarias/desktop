package com.telnetar.desktop.controlautomatico;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.telnetar.desktop.components.periods.PeriodScheduleDetailDatatable;
import com.telnetar.desktop.components.periods.PeriodScheduleDetailDatatableModel;
import com.telnetar.desktop.model.Perioddetailschedule;
import com.telnetar.desktop.services.PeriodScheduleDetailService;

import javax.swing.SpringLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.configuration.ConfigurationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Esta clase representa el detalle de un periodo. Ejemplo: la lista de entradas del dia Lunes
 * 
 * @author Federico
 *
 */
public class PeriodoDetallePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private int dia;
	private PeriodScheduleDetailDatatable periodScheduleDetailDatatable;
	private JButton btnEliminar;
	private Integer rowPeriodDetailScheduleSelect;
	
	public PeriodoDetallePanel(int dia){
		this.dia = dia;
		initUI();
	}

	private void initUI() {
		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		
		setPeriodScheduleDetailDatatable(new PeriodScheduleDetailDatatable());
		
		getPeriodScheduleDetailDatatable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ListSelectionModel rowMartesSM = getPeriodScheduleDetailDatatable().getSelectionModel();
		rowMartesSM.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				// Ignore extra messages.
				if (e.getValueIsAdjusting())
					return;

				ListSelectionModel lsm = (ListSelectionModel) e.getSource();
				if (lsm.isSelectionEmpty()) {
					System.out.println("No rows are selected.");
				} else {
					rowPeriodDetailScheduleSelect = lsm.getMinSelectionIndex();
//					getAceptarCancelarDetallePeriodoPanel().myUpdateUI(rowPeriodDetailScheduleSelect);
					myUpdateUI();
				}
			}
		});
		JScrollPane scrollPane = new JScrollPane(getPeriodScheduleDetailDatatable());
		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout());
		tablePanel.add(getPeriodScheduleDetailDatatable().getTableHeader(), BorderLayout.NORTH);
		tablePanel.add(scrollPane, BorderLayout.CENTER);
		layout.putConstraint(SpringLayout.NORTH, tablePanel, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, tablePanel, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, tablePanel, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, tablePanel, -40, SpringLayout.SOUTH, this);
		add(tablePanel);
		
		JPanel btnPanel = new JPanel();
		setBtnEliminar(new JButton("Eliminar"));
		btnPanel.add(getBtnEliminar());
		
		layout.putConstraint(SpringLayout.NORTH, btnPanel, 0, SpringLayout.SOUTH, tablePanel);
		layout.putConstraint(SpringLayout.WEST, btnPanel, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, btnPanel, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, btnPanel, 0, SpringLayout.SOUTH, this);
		add(btnPanel);
		
//		GraficoPanel graficoPanel = new GraficoPanel(getPeriodScheduleDetailDatatable());
//		layout.putConstraint(SpringLayout.NORTH, graficoPanel, 5, SpringLayout.SOUTH, btnPanel);
//		layout.putConstraint(SpringLayout.WEST, graficoPanel, 0, SpringLayout.WEST, this);
//		layout.putConstraint(SpringLayout.EAST, graficoPanel, 0, SpringLayout.EAST, this);
//		layout.putConstraint(SpringLayout.SOUTH, graficoPanel, 0, SpringLayout.SOUTH, this);
//		add(graficoPanel);
		
		myUpdateUI();
	}
	
	public void myUpdateUI(){
		Boolean enabled = getRowPeriodDetailScheduleSelect() != null;
		getBtnEliminar().setEnabled(enabled);
	}

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public PeriodScheduleDetailDatatable getPeriodScheduleDetailDatatable() {
		return periodScheduleDetailDatatable;
	}

	public void setPeriodScheduleDetailDatatable(PeriodScheduleDetailDatatable periodScheduleDetailDatatable) {
		this.periodScheduleDetailDatatable = periodScheduleDetailDatatable;
	}

	public JButton getBtnEliminar() {
		return btnEliminar;
	}

	public void setBtnEliminar(JButton btnEliminar) {
		this.btnEliminar = btnEliminar;
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					@SuppressWarnings("resource")
					ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
					PeriodScheduleDetailService periodScheduleDetailService = (PeriodScheduleDetailService) context.getBean("periodScheduleDetailService");
					
					PeriodScheduleDetailDatatableModel model = ((PeriodScheduleDetailDatatableModel)periodScheduleDetailDatatable.getModel());
					Perioddetailschedule perioddetailschedule = 
						model.getRowObject(getRowPeriodDetailScheduleSelect());
					periodScheduleDetailService.eliminar(perioddetailschedule);
					model.deleteData(perioddetailschedule, getRowPeriodDetailScheduleSelect());
					if(model.getRowCount() == 0){
						setRowPeriodDetailScheduleSelect(null);
					}
				} catch (ConfigurationException e1) {
					JOptionPane.showMessageDialog(null, "No es posible acceder a la configuración de la base de datos. Por favor comuniquesé con el técnico especializado.", "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	public Integer getRowPeriodDetailScheduleSelect() {
		return rowPeriodDetailScheduleSelect;
	}

	public void setRowPeriodDetailScheduleSelect(Integer rowPeriodDetailScheduleSelect) {
		this.rowPeriodDetailScheduleSelect = rowPeriodDetailScheduleSelect;
	}
}