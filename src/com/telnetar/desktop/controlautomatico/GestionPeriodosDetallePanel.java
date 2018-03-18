package com.telnetar.desktop.controlautomatico;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.apache.commons.configuration.ConfigurationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.telnetar.desktop.Util;
import com.telnetar.desktop.components.periods.PeriodScheduleDatatable;
import com.telnetar.desktop.components.periods.PeriodScheduleDatatableModel;
import com.telnetar.desktop.components.periods.PeriodScheduleDetailDatatable;
import com.telnetar.desktop.components.periods.PeriodScheduleDetailDatatableModel;
import com.telnetar.desktop.model.Perioddetailschedule;
import com.telnetar.desktop.services.PeriodScheduleDetailService;

import java.awt.Color;
import javax.swing.SpringLayout;
import javax.swing.JSpinner;
import java.awt.Font;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.awt.event.ActionEvent;

public class GestionPeriodosDetallePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JSpinner spnHora, spnMin, spnIntensidad;
	private JToggleButton btnDomingo, btnLunes, btnMartes, btnMiercoles, btnJueves, btnViernes, btnSabado;
	private PeriodScheduleDatatable periodScheduleDatatable;
	private PeriodScheduleDetailDatatable lunesDatatable, martesDatatable, miercolesDatatable, juevesDatatable, viernesDatatable, sabadoDatatable, domingoDatatable;
	private int rowPeriodSelected, rowDetailSelected;
	private JButton btnCancelar, btnAceptar;
	
	public GestionPeriodosDetallePanel() {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		setSpnHora(new JSpinner());
		getSpnHora().setModel(new SpinnerNumberModel(new Integer(0), null, new Integer(23), 1));
		getSpnHora().setFont(new Font("Tahoma", Font.PLAIN, 60));
		springLayout.putConstraint(SpringLayout.NORTH, getSpnHora(), 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, getSpnHora(), 40, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, getSpnHora(), 90, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, getSpnHora(), 160, SpringLayout.WEST, this);
		add(getSpnHora());
		
		setSpnMin(new JSpinner());
		getSpnMin().setModel(new SpinnerNumberModel(new Integer(0), null, new Integer(59), new Long(1)));
		getSpnMin().setFont(new Font("Tahoma", Font.PLAIN, 60));
		springLayout.putConstraint(SpringLayout.NORTH, getSpnMin(), 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, getSpnMin(), -160, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, getSpnMin(), 90, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, getSpnMin(), -40, SpringLayout.EAST, this);
		add(getSpnMin());
		
		JPanel panel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel, 6, SpringLayout.SOUTH, getSpnHora());
		springLayout.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, panel, -10, SpringLayout.EAST, this);
		add(panel);
		
		setBtnDomingo(new JToggleButton("D"));
		panel.add(getBtnDomingo());
		
		setBtnLunes(new JToggleButton("L"));
		panel.add(getBtnLunes());
		
		setBtnMartes(new JToggleButton("Ma"));
		panel.add(getBtnMartes());
		
		setBtnMiercoles(new JToggleButton("Mi"));
		panel.add(getBtnMiercoles());
		
		setBtnJueves(new JToggleButton("J"));
		panel.add(getBtnJueves());
		
		setBtnViernes(new JToggleButton("V"));
		panel.add(getBtnViernes());
		
		setBtnSabado(new JToggleButton("S"));
		panel.add(getBtnSabado());
		
		JLabel lblIntensidad = new JLabel("Intensidad:");
		lblIntensidad.setFont(new Font("Tahoma", Font.PLAIN, 14));
		springLayout.putConstraint(SpringLayout.NORTH, lblIntensidad, 6, SpringLayout.SOUTH, panel);
		springLayout.putConstraint(SpringLayout.WEST, lblIntensidad, 0, SpringLayout.WEST, panel);
		add(lblIntensidad);
		
		setSpnIntensidad(new JSpinner());
		getSpnIntensidad().setModel(new SpinnerNumberModel(0, 0, 100, 1));
		springLayout.putConstraint(SpringLayout.NORTH, getSpnIntensidad(), 6, SpringLayout.SOUTH, panel);
		springLayout.putConstraint(SpringLayout.WEST, getSpnIntensidad(), 6, SpringLayout.EAST, lblIntensidad);
		springLayout.putConstraint(SpringLayout.EAST, getSpnIntensidad(), 0, SpringLayout.EAST, panel);
		add(getSpnIntensidad());
		
		setBtnAceptar(new JButton("Aceptar"));
		getBtnAceptar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					@SuppressWarnings("resource")
					ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
					PeriodScheduleDetailService periodScheduleDetailService = (PeriodScheduleDetailService) context.getBean("periodScheduleDetailService");
					Perioddetailschedule perioddetailschedule = new Perioddetailschedule();
					perioddetailschedule.setIdPeriod(
						new BigInteger(((PeriodScheduleDatatableModel)getPeriodScheduleDatatable().getModel())
						.getRowObject(getRowPeriodSelected())
						.getId())
					);
					Calendar calendar = new GregorianCalendar();
					calendar.set(Calendar.HOUR_OF_DAY, (Integer)getSpnHora().getValue());
					calendar.set(Calendar.MINUTE, (Integer)getSpnMin().getValue());
					perioddetailschedule.setInitHour(new BigInteger(new Long(calendar.getTimeInMillis()).toString()));
					perioddetailschedule.setIntensity(Util.getVirtualIntensity((Integer)getSpnIntensidad().getValue()));
					if(getBtnDomingo().isSelected()){
						perioddetailschedule.setDay(Calendar.SUNDAY);
						periodScheduleDetailService.insertarPeriodoDetalle(perioddetailschedule);
						((PeriodScheduleDetailDatatableModel)getDomingoDatatable().getModel()).addData(perioddetailschedule);
					}
					if(getBtnLunes().isSelected()){
						perioddetailschedule.setDay(Calendar.MONDAY);
						periodScheduleDetailService.insertarPeriodoDetalle(perioddetailschedule);
						((PeriodScheduleDetailDatatableModel)getLunesDatatable().getModel()).addData(perioddetailschedule);
					}
					if(getBtnMartes().isSelected()){
						perioddetailschedule.setDay(Calendar.TUESDAY);
						periodScheduleDetailService.insertarPeriodoDetalle(perioddetailschedule);
						((PeriodScheduleDetailDatatableModel)getMartesDatatable().getModel()).addData(perioddetailschedule);
					}
					if(getBtnMiercoles().isSelected()){
						perioddetailschedule.setDay(Calendar.WEDNESDAY);
						periodScheduleDetailService.insertarPeriodoDetalle(perioddetailschedule);
						((PeriodScheduleDetailDatatableModel)getMiercolesDatatable().getModel()).addData(perioddetailschedule);
					}
					if(getBtnJueves().isSelected()){
						perioddetailschedule.setDay(Calendar.THURSDAY);
						periodScheduleDetailService.insertarPeriodoDetalle(perioddetailschedule);
						((PeriodScheduleDetailDatatableModel)getJuevesDatatable().getModel()).addData(perioddetailschedule);
					}
					if(getBtnViernes().isSelected()){
						perioddetailschedule.setDay(Calendar.FRIDAY);
						periodScheduleDetailService.insertarPeriodoDetalle(perioddetailschedule);
						((PeriodScheduleDetailDatatableModel)getViernesDatatable().getModel()).addData(perioddetailschedule);
					}
					if(getBtnSabado().isSelected()){
						perioddetailschedule.setDay(Calendar.SATURDAY);
						periodScheduleDetailService.insertarPeriodoDetalle(perioddetailschedule);
						((PeriodScheduleDetailDatatableModel)getSabadoDatatable().getModel()).addData(perioddetailschedule);
					}
				} catch (ConfigurationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnAceptar, 6, SpringLayout.SOUTH, getSpnIntensidad());
		springLayout.putConstraint(SpringLayout.EAST, btnAceptar, 0, SpringLayout.EAST, panel);
		add(getBtnAceptar());
		
		setBtnCancelar(new JButton("Cancelar"));
		getBtnCancelar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getSpnHora().setValue(new Long(0));
				getSpnMin().setValue(new Long(0));
				getSpnIntensidad().setValue(new Long(0));
				getBtnDomingo().setSelected(false);
				getBtnLunes().setSelected(false);
				getBtnMartes().setSelected(false);
				getBtnMiercoles().setSelected(false);
				getBtnJueves().setSelected(false);
				getBtnViernes().setSelected(false);
				getBtnSabado().setSelected(false);
				getBtnCancelar().setEnabled(false);
				getBtnAceptar().setEnabled(false);
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnCancelar, 6, SpringLayout.SOUTH, getSpnIntensidad());
		springLayout.putConstraint(SpringLayout.EAST, btnCancelar, -6, SpringLayout.WEST, btnAceptar);
		add(btnCancelar);
		
		myUpdateUI(0, null);
	}
	
	public void myUpdateUI(Integer rowPeriodSelected, PeriodScheduleDatatable periodScheduleDatatable){
		this.rowPeriodSelected = rowPeriodSelected;
		this.periodScheduleDatatable = periodScheduleDatatable;
		Boolean enabled = getRowPeriodSelected() != null && getPeriodScheduleDatatable() != null;
		getSpnHora().setEnabled(enabled);
		getSpnMin().setEnabled(enabled);
		getSpnIntensidad().setEnabled(enabled);
		getBtnDomingo().setEnabled(enabled);
		getBtnLunes().setEnabled(enabled);
		getBtnMartes().setEnabled(enabled);
		getBtnMiercoles().setEnabled(enabled);
		getBtnJueves().setEnabled(enabled);
		getBtnViernes().setEnabled(enabled);
		getBtnSabado().setEnabled(enabled);
		getBtnAceptar().setEnabled(enabled);
		getBtnCancelar().setEnabled(enabled);
	}

	public JSpinner getSpnHora() {
		return spnHora;
	}

	public void setSpnHora(JSpinner spnHora) {
		this.spnHora = spnHora;
	}

	public JToggleButton getBtnDomingo() {
		return btnDomingo;
	}

	public void setBtnDomingo(JToggleButton btnDomingo) {
		this.btnDomingo = btnDomingo;
	}

	public JToggleButton getBtnLunes() {
		return btnLunes;
	}

	public void setBtnLunes(JToggleButton btnLunes) {
		this.btnLunes = btnLunes;
	}

	public JToggleButton getBtnMartes() {
		return btnMartes;
	}

	public void setBtnMartes(JToggleButton btnMartes) {
		this.btnMartes = btnMartes;
	}

	public JToggleButton getBtnMiercoles() {
		return btnMiercoles;
	}

	public void setBtnMiercoles(JToggleButton btnMiercoles) {
		this.btnMiercoles = btnMiercoles;
	}

	public JToggleButton getBtnJueves() {
		return btnJueves;
	}

	public void setBtnJueves(JToggleButton btnJueves) {
		this.btnJueves = btnJueves;
	}

	public JToggleButton getBtnViernes() {
		return btnViernes;
	}

	public void setBtnViernes(JToggleButton btnViernes) {
		this.btnViernes = btnViernes;
	}

	public JToggleButton getBtnSabado() {
		return btnSabado;
	}

	public void setBtnSabado(JToggleButton btnSabado) {
		this.btnSabado = btnSabado;
	}

	public JSpinner getSpnMin() {
		return spnMin;
	}

	public void setSpnMin(JSpinner spnMin) {
		this.spnMin = spnMin;
	}

	public PeriodScheduleDatatable getPeriodScheduleDatatable() {
		return periodScheduleDatatable;
	}

	public void setPeriodScheduleDatatable(PeriodScheduleDatatable periodScheduleDatatable) {
		this.periodScheduleDatatable = periodScheduleDatatable;
	}

	public int getRowDetailSelected() {
		return rowDetailSelected;
	}

	public void setRowDetailSelected(int rowDetailSelected) {
		this.rowDetailSelected = rowDetailSelected;
	}

	public Integer getRowPeriodSelected() {
		return rowPeriodSelected;
	}

	public void setRowPeriodSelected(Integer rowPeriodSelected) {
		this.rowPeriodSelected = rowPeriodSelected;
	}

	public JSpinner getSpnIntensidad() {
		return spnIntensidad;
	}

	public void setSpnIntensidad(JSpinner spnIntensidad) {
		this.spnIntensidad = spnIntensidad;
	}

	public void setRowPeriodSelected(int rowPeriodSelected) {
		this.rowPeriodSelected = rowPeriodSelected;
	}

	public PeriodScheduleDetailDatatable getLunesDatatable() {
		return lunesDatatable;
	}

	public void setLunesDatatable(PeriodScheduleDetailDatatable lunesDatatable) {
		this.lunesDatatable = lunesDatatable;
	}

	public PeriodScheduleDetailDatatable getMartesDatatable() {
		return martesDatatable;
	}

	public void setMartesDatatable(PeriodScheduleDetailDatatable martesDatatable) {
		this.martesDatatable = martesDatatable;
	}

	public PeriodScheduleDetailDatatable getMiercolesDatatable() {
		return miercolesDatatable;
	}

	public void setMiercolesDatatable(PeriodScheduleDetailDatatable miercolesDatatable) {
		this.miercolesDatatable = miercolesDatatable;
	}

	public PeriodScheduleDetailDatatable getJuevesDatatable() {
		return juevesDatatable;
	}

	public void setJuevesDatatable(PeriodScheduleDetailDatatable juevesDatatable) {
		this.juevesDatatable = juevesDatatable;
	}

	public PeriodScheduleDetailDatatable getViernesDatatable() {
		return viernesDatatable;
	}

	public void setViernesDatatable(PeriodScheduleDetailDatatable viernesDatatable) {
		this.viernesDatatable = viernesDatatable;
	}

	public PeriodScheduleDetailDatatable getSabadoDatatable() {
		return sabadoDatatable;
	}

	public void setSabadoDatatable(PeriodScheduleDetailDatatable sabadoDatatable) {
		this.sabadoDatatable = sabadoDatatable;
	}

	public PeriodScheduleDetailDatatable getDomingoDatatable() {
		return domingoDatatable;
	}

	public void setDomingoDatatable(PeriodScheduleDetailDatatable domingoDatatable) {
		this.domingoDatatable = domingoDatatable;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(JButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public JButton getBtnAceptar() {
		return btnAceptar;
	}

	public void setBtnAceptar(JButton btnAceptar) {
		this.btnAceptar = btnAceptar;
	}
}
