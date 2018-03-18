package com.telnetar.desktop.luminarias;

import javax.swing.JPanel;
import javax.swing.SpringLayout;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.border.LineBorder;

import com.telnetar.desktop.Util;
import com.telnetar.desktop.model.Luminariahist;
import com.telnetar.interfaces.RfInterface;
import com.telnetar.interfaces.core.PaqueteDto;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JSlider;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ControlPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Luminariahist luminariahist;
	private JLabel lblVId, lblVFechaUltimaSincronizacion, lblVIntensidad, lblVTemperaturaDelLed, lblVTemperaturaLuminaria;
	private JSlider sliderIntensidad;
	private JButton btnApagar, btnEncender, btnIdentificar;
	private RfInterface rfInterface;

	public ControlPanel(RfInterface rfInterface) {
		this.rfInterface = rfInterface;
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setPreferredSize(new Dimension(300, 32767));
		setMinimumSize(new Dimension(300, 10));
		setMaximumSize(new Dimension(300, 32767));
		
		JLabel lblId = new JLabel("Id:");
		springLayout.putConstraint(SpringLayout.WEST, lblId, 12, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, lblId, -130, SpringLayout.EAST, this);
		lblId.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblId);
		
		lblVId = new JLabel();
		springLayout.putConstraint(SpringLayout.WEST, lblVId, 5, SpringLayout.EAST, lblId);
		springLayout.putConstraint(SpringLayout.EAST, lblVId, -10, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.NORTH, lblVId, 0, SpringLayout.NORTH, lblId);
		add(lblVId);
		
		JLabel lblFechaDeUltima = new JLabel("Fecha de ultima sincronizaci\u00F3n:");
		lblFechaDeUltima.setHorizontalAlignment(SwingConstants.RIGHT);
		springLayout.putConstraint(SpringLayout.NORTH, lblFechaDeUltima, 5, SpringLayout.SOUTH, lblId);
		springLayout.putConstraint(SpringLayout.WEST, lblFechaDeUltima, 0, SpringLayout.WEST, lblId);
		springLayout.putConstraint(SpringLayout.EAST, lblFechaDeUltima, 0, SpringLayout.EAST, lblId);
		add(lblFechaDeUltima);
		
		lblVFechaUltimaSincronizacion = new JLabel();
		springLayout.putConstraint(SpringLayout.WEST, lblVFechaUltimaSincronizacion, 5, SpringLayout.EAST, lblFechaDeUltima);
		springLayout.putConstraint(SpringLayout.EAST, lblVFechaUltimaSincronizacion, -10, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.NORTH, lblVFechaUltimaSincronizacion, 0, SpringLayout.NORTH, lblFechaDeUltima);
		add(lblVFechaUltimaSincronizacion);
		
		JLabel lblIntensidad = new JLabel("Intensidad:");
		lblIntensidad.setHorizontalAlignment(SwingConstants.RIGHT);
		springLayout.putConstraint(SpringLayout.NORTH, lblIntensidad, 5, SpringLayout.SOUTH, lblFechaDeUltima);
		springLayout.putConstraint(SpringLayout.WEST, lblIntensidad, 0, SpringLayout.WEST, lblFechaDeUltima);
		springLayout.putConstraint(SpringLayout.EAST, lblIntensidad, 0, SpringLayout.EAST, lblFechaDeUltima);
		add(lblIntensidad);
		
		lblVIntensidad = new JLabel();
		springLayout.putConstraint(SpringLayout.WEST, lblVIntensidad, 5, SpringLayout.EAST, lblIntensidad);
		springLayout.putConstraint(SpringLayout.EAST, lblVIntensidad, -10, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.NORTH, lblVIntensidad, 0, SpringLayout.NORTH, lblIntensidad);
		add(lblVIntensidad);
		
		JLabel lblTemperaturaLed = new JLabel("Temperatura del led:");
		lblTemperaturaLed.setHorizontalAlignment(SwingConstants.RIGHT);
		springLayout.putConstraint(SpringLayout.NORTH, lblTemperaturaLed, 5, SpringLayout.SOUTH, lblIntensidad);
		springLayout.putConstraint(SpringLayout.WEST, lblTemperaturaLed, 0, SpringLayout.WEST, lblIntensidad);
		springLayout.putConstraint(SpringLayout.EAST, lblTemperaturaLed, 0, SpringLayout.EAST, lblIntensidad);
		add(lblTemperaturaLed);
		
		lblVTemperaturaDelLed = new JLabel();
		springLayout.putConstraint(SpringLayout.WEST, lblVTemperaturaDelLed, 5, SpringLayout.EAST, lblTemperaturaLed);
		springLayout.putConstraint(SpringLayout.EAST, lblVTemperaturaDelLed, -10, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.NORTH, lblVTemperaturaDelLed, 0, SpringLayout.NORTH, lblTemperaturaLed);
		add(lblVTemperaturaDelLed);
		
		JLabel lblTemperaturaLuminaria = new JLabel("Temperatura de la luminaria:");
		lblTemperaturaLuminaria.setHorizontalAlignment(SwingConstants.RIGHT);
		springLayout.putConstraint(SpringLayout.NORTH, lblTemperaturaLuminaria, 5, SpringLayout.SOUTH, lblTemperaturaLed);
		springLayout.putConstraint(SpringLayout.WEST, lblTemperaturaLuminaria, 0, SpringLayout.WEST, lblTemperaturaLed);
		springLayout.putConstraint(SpringLayout.EAST, lblTemperaturaLuminaria, 0, SpringLayout.EAST, lblTemperaturaLed);
		add(lblTemperaturaLuminaria);
		
		lblVTemperaturaLuminaria = new JLabel();
		springLayout.putConstraint(SpringLayout.WEST, lblVTemperaturaLuminaria, 5, SpringLayout.EAST, lblTemperaturaLuminaria);
		springLayout.putConstraint(SpringLayout.EAST, lblVTemperaturaLuminaria, -10, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.NORTH, lblVTemperaturaLuminaria, 0, SpringLayout.NORTH, lblTemperaturaLuminaria);
		add(lblVTemperaturaLuminaria);
		
		sliderIntensidad = new JSlider();
		sliderIntensidad.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				try {
					int intensity = getSliderIntensidad().getValue();
					intensity = intensity == 0 ? intensity = 1 : Util.getVirtualIntensity(intensity);
					PaqueteDto paqueteDto = 
						new PaqueteDto(
							new Byte((byte)getLuminariahist().getId().getHightbyte().intValue()),
							new Byte((byte)getLuminariahist().getId().getLowbyte().intValue()), 
							new Byte((byte) 0x02), new Byte[] {
								(new Byte((byte) 0xFE)), new Byte((byte) intensity) });
					getRfInterface().send(paqueteDto);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		sliderIntensidad.setMinimum(1);
		sliderIntensidad.setMaximum(100);
		springLayout.putConstraint(SpringLayout.NORTH, sliderIntensidad, 5, SpringLayout.SOUTH, lblTemperaturaLuminaria);
		springLayout.putConstraint(SpringLayout.WEST, sliderIntensidad, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, sliderIntensidad, -10, SpringLayout.EAST, this);
		add(sliderIntensidad);
		
		JPanel panelEncendidoApagado = new JPanel();
		panelEncendidoApagado.setLayout(new FlowLayout());
		springLayout.putConstraint(SpringLayout.NORTH, panelEncendidoApagado, 5, SpringLayout.SOUTH, sliderIntensidad);
		springLayout.putConstraint(SpringLayout.WEST, panelEncendidoApagado, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, panelEncendidoApagado, -10, SpringLayout.EAST, this);
		add(panelEncendidoApagado);
		
		setBtnApagar(new JButton("Apagar"));
		getBtnApagar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					PaqueteDto paqueteDto = 
						new PaqueteDto(
							new Byte((byte)getLuminariahist().getId().getHightbyte().intValue()),
							new Byte((byte)getLuminariahist().getId().getLowbyte().intValue()), 
							new Byte((byte) 0x02), new Byte[] {
								(new Byte((byte) 0xFE)), new Byte((byte) 0x01) });
					getRfInterface().send(paqueteDto);
					getSliderIntensidad().setValue(1);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});
		panelEncendidoApagado.add(getBtnApagar());
		
		setBtnEncender(new JButton("Encender"));
		getBtnEncender().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					PaqueteDto paqueteDto = 
						new PaqueteDto(
							new Byte((byte)getLuminariahist().getId().getHightbyte().intValue()),
							new Byte((byte)getLuminariahist().getId().getLowbyte().intValue()), 
							new Byte((byte) 0x02), new Byte[] {
								(new Byte((byte) 0xFE)), new Byte((byte) 250) });
					getRfInterface().send(paqueteDto);
					getSliderIntensidad().setValue(100);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});
		panelEncendidoApagado.add(getBtnEncender());
		
		setBtnIdentificar(new JButton("Identificar"));
		getBtnIdentificar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					int intensidadOriginal = getLuminariahist().getIntensity();
					PaqueteDto paqueteDto = null;
					for (int i = 0; i < 4; i++) {
						int xIntensidad;
						if(i%2==0){
							xIntensidad = 100;
						}else{
							xIntensidad = 20;
						}
						paqueteDto = 
							new PaqueteDto(
								new Byte((byte)getLuminariahist().getId().getHightbyte().intValue()),
								new Byte((byte)getLuminariahist().getId().getLowbyte().intValue()), 
								new Byte((byte) 0x02), new Byte[] {
									(new Byte((byte) 0xFE)), new Byte((byte) Util.getVirtualIntensity(xIntensidad).intValue()) });
						getRfInterface().send(paqueteDto);
						Thread.sleep(500);
					}
					intensidadOriginal = intensidadOriginal == 0 ? 1 : intensidadOriginal;
					paqueteDto.setData(new Byte[] {
									(new Byte((byte) 0xFE)), new Byte((byte) intensidadOriginal) });
					getRfInterface().send(paqueteDto);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});
		panelEncendidoApagado.add(getBtnIdentificar());
		
		configurar();
	}

	public Luminariahist getLuminariahist() {
		return luminariahist;
	}

	public void setLuminariahist(Luminariahist luminariahist) {
		this.luminariahist = luminariahist;
		if(this.luminariahist != null){
			configurar();
		}
	}
	
	private void configurar(){
		lblVId.setText(getLuminariahist() != null ? this.luminariahist.getId().getPk() : "");
		Integer intensidadReal = getLuminariahist() != null ? new Integer(Util.getRealIntensity(this.luminariahist.getIntensity())) : null;
		lblVIntensidad.setText(intensidadReal != null ? intensidadReal.toString() : "");
		lblVFechaUltimaSincronizacion.setText(getLuminariahist() != null ? Util.formatDate(this.luminariahist.getId().getFecha(), "dd/MM/yyyy HH:mm") : "");
		lblVTemperaturaDelLed.setText(getLuminariahist() != null ? new Integer(Util.getTemperature(this.luminariahist.getTemperatureHight(), this.luminariahist.getTemperatureLow())).toString() : "");
		lblVTemperaturaLuminaria.setText(getLuminariahist() != null ? new Integer(Util.getTemperature(this.luminariahist.getLumiContextH(), this.luminariahist.getLumiContextL())).toString() : "");
		getSliderIntensidad().setValue(intensidadReal != null ? intensidadReal : 1);
		getSliderIntensidad().setEnabled(getLuminariahist() != null ? true : false);
		getBtnApagar().setEnabled(getLuminariahist() != null ? true : false);
		getBtnEncender().setEnabled(getLuminariahist() != null ? true : false);
		getBtnIdentificar().setEnabled(getLuminariahist() != null ? true : false);
	}

	public JLabel getLblVId() {
		return lblVId;
	}

	public void setLblVId(JLabel lblVId) {
		this.lblVId = lblVId;
	}

	public JLabel getLblVFechaUltimaSincronizacion() {
		return lblVFechaUltimaSincronizacion;
	}

	public void setLblVFechaUltimaSincronizacion(JLabel lblVFechaUltimaSincronizacion) {
		this.lblVFechaUltimaSincronizacion = lblVFechaUltimaSincronizacion;
	}

	public JLabel getLblVIntensidad() {
		return lblVIntensidad;
	}

	public void setLblVIntensidad(JLabel lblVIntensidad) {
		this.lblVIntensidad = lblVIntensidad;
	}

	public JLabel getLblVTemperaturaDelLed() {
		return lblVTemperaturaDelLed;
	}

	public void setLblVTemperaturaDelLed(JLabel lblVTemperaturaDelLed) {
		this.lblVTemperaturaDelLed = lblVTemperaturaDelLed;
	}

	public JLabel getLblVTemperaturaLuminaria() {
		return lblVTemperaturaLuminaria;
	}

	public void setLblVTemperaturaLuminaria(JLabel lblVTemperaturaLuminaria) {
		this.lblVTemperaturaLuminaria = lblVTemperaturaLuminaria;
	}

	public JSlider getSliderIntensidad() {
		return sliderIntensidad;
	}

	public void setSliderIntensidad(JSlider sliderIntensidad) {
		this.sliderIntensidad = sliderIntensidad;
	}

	public JButton getBtnApagar() {
		return btnApagar;
	}

	public void setBtnApagar(JButton btnApagar) {
		this.btnApagar = btnApagar;
	}

	public JButton getBtnEncender() {
		return btnEncender;
	}

	public void setBtnEncender(JButton btnEncender) {
		this.btnEncender = btnEncender;
	}

	public RfInterface getRfInterface() {
		return rfInterface;
	}

	public void setRfInterface(RfInterface rfInterface) {
		this.rfInterface = rfInterface;
	}

	public JButton getBtnIdentificar() {
		return btnIdentificar;
	}

	public void setBtnIdentificar(JButton btnIdentificar) {
		this.btnIdentificar = btnIdentificar;
	}
}
