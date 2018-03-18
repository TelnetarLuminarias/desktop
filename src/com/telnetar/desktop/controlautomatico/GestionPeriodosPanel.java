package com.telnetar.desktop.controlautomatico;

import javax.swing.JPanel;
import javax.swing.SpringLayout;

import com.telnetar.desktop.components.DateLabelFormatter;
import com.telnetar.desktop.components.periods.PeriodScheduleDatatable;
import com.telnetar.desktop.components.periods.PeriodScheduleDatatableModel;
import com.telnetar.desktop.model.Periodschedule;
import com.telnetar.desktop.services.PeriodScheduleService;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import javax.swing.border.LineBorder;

import org.apache.commons.configuration.ConfigurationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.awt.event.ActionEvent;

public class GestionPeriodosPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Periodschedule periodschedule;
	private int rowSelected;
	private PeriodScheduleDatatable periodScheduleDatatable;
	
	private JTextField descripcionPeriodo;
	private JCheckBox grupoNuevo;
	private UtilDateModel model; 
	private JDatePickerImpl datePicker;
	
	private JButton btnAceptar, btnCancelar, btnEliminar;
	
	public GestionPeriodosPanel(PeriodScheduleDatatable periodScheduleDatatable) {
		this.periodScheduleDatatable = periodScheduleDatatable;
		setBorder(new LineBorder(new Color(0, 0, 0)));
		initUI();
	}

	private void initUI() {
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		model = new UtilDateModel();
		Calendar calendar = GregorianCalendar.getInstance();
		model.setValue(calendar.getTime());
		JDatePanelImpl datePanel = new JDatePanelImpl(model);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.getJFormattedTextField().setText("Ingrese una fecha de inicio");
		
		springLayout.putConstraint(SpringLayout.NORTH, datePicker, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, datePicker, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, datePicker, -10, SpringLayout.EAST, this);
		this.add(datePicker);
		
		JLabel lblDescripcin = new JLabel("Descripci\u00F3n:");
		springLayout.putConstraint(SpringLayout.NORTH, lblDescripcin, 6, SpringLayout.SOUTH, datePicker);
		springLayout.putConstraint(SpringLayout.WEST, lblDescripcin, 0, SpringLayout.WEST, datePicker);
		add(lblDescripcin);
		
		descripcionPeriodo = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, descripcionPeriodo, 0, SpringLayout.NORTH, lblDescripcin);
		springLayout.putConstraint(SpringLayout.WEST, descripcionPeriodo, 20, SpringLayout.EAST, lblDescripcin);
		springLayout.putConstraint(SpringLayout.EAST, descripcionPeriodo, 0, SpringLayout.EAST, datePicker);
		add(descripcionPeriodo);
		descripcionPeriodo.setColumns(10);
		
		JLabel lblGrupo = new JLabel("Grupo nuevo:");
		springLayout.putConstraint(SpringLayout.NORTH, lblGrupo, 6, SpringLayout.SOUTH, descripcionPeriodo);
		springLayout.putConstraint(SpringLayout.WEST, lblGrupo, 0, SpringLayout.WEST, datePicker);
		add(lblGrupo);
		
		grupoNuevo = new JCheckBox("");
		grupoNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAceptar.setEnabled(grupoNuevo.isSelected());
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, grupoNuevo, 0, SpringLayout.NORTH, lblGrupo);
		springLayout.putConstraint(SpringLayout.WEST, grupoNuevo, 0, SpringLayout.WEST, descripcionPeriodo);
		springLayout.putConstraint(SpringLayout.EAST, grupoNuevo, 0, SpringLayout.EAST, descripcionPeriodo);
		add(grupoNuevo);
		
		JPanel panel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel, 6, SpringLayout.SOUTH, grupoNuevo);
		springLayout.putConstraint(SpringLayout.WEST, panel, 0, SpringLayout.WEST, datePicker);
		springLayout.putConstraint(SpringLayout.EAST, panel, 0, SpringLayout.EAST, datePicker);
		add(panel);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(!descripcionPeriodo.getText().isEmpty()){
						periodschedule = grupoNuevo.isSelected() ? new Periodschedule() : periodschedule;
						@SuppressWarnings("resource")
						ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
						PeriodScheduleService periodScheduleService = (PeriodScheduleService) context.getBean("periodScheduleService");
						periodschedule.setInitDate(new BigInteger(new Long(((Date)datePicker.getModel().getValue()).getTime()).toString()));
						periodschedule.setDescription(descripcionPeriodo.getText());
	//					periodschedule.setIdGrupoLuminaria(idGrupoLuminaria);
	
						if(grupoNuevo.isSelected()){
							periodScheduleService.insertarPeriodo(periodschedule);
							((PeriodScheduleDatatableModel)periodScheduleDatatable.getModel()).addData(periodschedule);
						}else{
							periodScheduleService.modificarPeriodo(periodschedule);
							((PeriodScheduleDatatableModel)periodScheduleDatatable.getModel()).updateData(periodschedule, rowSelected);
						}
					}else{
						JOptionPane.showMessageDialog(null, "Debe seleccionar una descripción", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} catch (ConfigurationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel.add(btnAceptar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Calendar gregorianCalendar = GregorianCalendar.getInstance(); 
				model.setValue(gregorianCalendar.getTime());
				descripcionPeriodo.setText("");
				grupoNuevo.setSelected(false);
				setPeriodschedule(null);
			}
		});
		panel.add(btnCancelar);
		btnAceptar.setEnabled(periodschedule != null);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					@SuppressWarnings("resource")
					ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
					PeriodScheduleService periodScheduleService = (PeriodScheduleService) context.getBean("periodScheduleService");
					periodScheduleService.eliminarPeriodo(periodschedule);
					((PeriodScheduleDatatableModel)periodScheduleDatatable.getModel()).deleteData(periodschedule, rowSelected);
					periodschedule = null;
					btnAceptar.setEnabled(false);
					btnEliminar.setEnabled(false);
					descripcionPeriodo.setText("");
					grupoNuevo.setSelected(false);
				} catch (ConfigurationException e1) {
					JOptionPane.showMessageDialog(null, "No es posible acceder a la configuración de la base de datos. Por favor comuniquesé con el técnico especializado.", "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel.add(btnEliminar);
		btnEliminar.setEnabled(periodschedule != null);
	}

	public Periodschedule getPeriodschedule() {
		return periodschedule;
	}
	public void setPeriodschedule(Periodschedule periodschedule) {
		this.periodschedule = periodschedule;
		getDescripcionPeriodo().setText(periodschedule != null ? periodschedule.getDescription() : "");
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTimeInMillis(periodschedule != null ? periodschedule.getInitDate().longValue() : new Date().getTime());
		model.setValue(calendar.getTime());
		btnAceptar.setEnabled(periodschedule != null);
		btnEliminar.setEnabled(periodschedule != null);
	}

	public JDatePickerImpl getDatePicker() {
		return datePicker;
	}

	public void setDatePicker(JDatePickerImpl datePicker) {
		this.datePicker = datePicker;
	}

	public JCheckBox getGrupoNuevo() {
		return grupoNuevo;
	}

	public void setGrupoNuevo(JCheckBox grupoNuevo) {
		this.grupoNuevo = grupoNuevo;
	}

	public JTextField getDescripcionPeriodo() {
		return descripcionPeriodo;
	}

	public void setDescripcionPeriodo(JTextField descripcionPeriodo) {
		this.descripcionPeriodo = descripcionPeriodo;
	}

	public PeriodScheduleDatatable getPeriodScheduleDatatable() {
		return periodScheduleDatatable;
	}

	public void setPeriodScheduleDatatable(PeriodScheduleDatatable periodScheduleDatatable) {
		this.periodScheduleDatatable = periodScheduleDatatable;
	}

	public int getRowSelected() {
		return rowSelected;
	}

	public void setRowSelected(int rowSelected) {
		this.rowSelected = rowSelected;
	}

	public JButton getBtnAceptar() {
		return btnAceptar;
	}

	public void setBtnAceptar(JButton btnAceptar) {
		this.btnAceptar = btnAceptar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(JButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public JButton getBtnEliminar() {
		return btnEliminar;
	}

	public void setBtnEliminar(JButton btnEliminar) {
		this.btnEliminar = btnEliminar;
	}
}
