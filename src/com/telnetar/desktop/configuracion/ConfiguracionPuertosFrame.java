package com.telnetar.desktop.configuracion;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import org.apache.commons.configuration.ConfigurationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.telnetar.desktop.model.Configuration;
import com.telnetar.desktop.services.ConfigurationService;

import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

public class ConfiguracionPuertosFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField comMaster, comL0;
	private JCheckBox chkL0Integrado;
	private Configuration configuration;
	private JButton btnAceptar, btnCancelar;

	/**
	 * Create the frame.
	 * @throws ConfigurationException 
	 */
	public ConfiguracionPuertosFrame() throws ConfigurationException {
		setResizable(false);
		setTitle("Puertos interfaces");
		setSize(300, 157);
		setLocation(10, 10);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		JLabel lblPuertoComMaster = new JLabel("Puerto com Master:");
		lblPuertoComMaster.setHorizontalAlignment(SwingConstants.RIGHT);
		springLayout.putConstraint(SpringLayout.NORTH, lblPuertoComMaster, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblPuertoComMaster, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblPuertoComMaster, -150, SpringLayout.EAST, getContentPane());
		getContentPane().add(lblPuertoComMaster);
		
		setComMaster(new JTextField());
		springLayout.putConstraint(SpringLayout.NORTH, getComMaster(), 0, SpringLayout.NORTH, lblPuertoComMaster);
		springLayout.putConstraint(SpringLayout.WEST, getComMaster(), 5, SpringLayout.EAST, lblPuertoComMaster);
		springLayout.putConstraint(SpringLayout.EAST, getComMaster(), -10, SpringLayout.EAST, getContentPane());
		getContentPane().add(getComMaster());
		getComMaster().setColumns(10);
	
		JLabel lblPuertoComL0 = new JLabel("Puerto com L0:");
		lblPuertoComL0.setHorizontalAlignment(SwingConstants.RIGHT);
		springLayout.putConstraint(SpringLayout.NORTH, lblPuertoComL0, 5, SpringLayout.SOUTH, comMaster);
		springLayout.putConstraint(SpringLayout.WEST, lblPuertoComL0, 0, SpringLayout.WEST, lblPuertoComMaster);
		springLayout.putConstraint(SpringLayout.EAST, lblPuertoComL0, 0, SpringLayout.EAST, lblPuertoComMaster);
		getContentPane().add(lblPuertoComL0);
		
		setComL0(new JTextField());
		springLayout.putConstraint(SpringLayout.NORTH, getComL0(), 0, SpringLayout.NORTH, lblPuertoComL0);
		springLayout.putConstraint(SpringLayout.WEST, getComL0(), 5, SpringLayout.EAST, lblPuertoComL0);
		springLayout.putConstraint(SpringLayout.EAST, getComL0(), -10, SpringLayout.EAST, getContentPane());
		getContentPane().add(getComL0());
		getComL0().setColumns(10);
		
		JPanel panelcheck = new JPanel();
		panelcheck.setLayout(new FlowLayout());
		springLayout.putConstraint(SpringLayout.NORTH, panelcheck, 5, SpringLayout.SOUTH, getComL0());
		springLayout.putConstraint(SpringLayout.WEST, panelcheck, 0, SpringLayout.WEST, lblPuertoComL0);
		springLayout.putConstraint(SpringLayout.EAST, panelcheck, -10, SpringLayout.EAST, getContentPane());
		getContentPane().add(panelcheck);
		
		setChkL0Integrado(new JCheckBox("L0 integrado?"));
		getChkL0Integrado().setHorizontalTextPosition(SwingConstants.LEFT);
		getChkL0Integrado().setHorizontalAlignment(SwingConstants.RIGHT);
		panelcheck.add(getChkL0Integrado());
		
		JPanel panel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel, 0, SpringLayout.SOUTH, panelcheck);
		springLayout.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel, -10, SpringLayout.EAST, getContentPane());
		getContentPane().add(panel);
		
		setBtnAceptar(new JButton("Guardar cambios"));
		getBtnAceptar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("resource")
				ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
				ConfigurationService configurationService = (ConfigurationService) context.getBean("configurationService");
				try {
					getConfiguration().setPuertoComL0(getComL0().getText().trim());
					getConfiguration().setPuertoComMaster(getComMaster().getText().trim());
					getConfiguration().setL0integrado(getChkL0Integrado().isSelected() ? 1 : 0);
					configurationService.guardarCambios(getConfiguration());
					JOptionPane.showMessageDialog(null,"Los cambios se guardaron con éxito","Éxito",JOptionPane.PLAIN_MESSAGE);
					setVisible(false);
					dispose();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel.add(getBtnAceptar());
		
		setBtnCancelar(new JButton("Cancelar"));
		getBtnCancelar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		panel.add(getBtnCancelar());
		
		obtenerDatosDb();
		
		setVisible(true);

	}

	@SuppressWarnings("resource")
	private void obtenerDatosDb() throws ConfigurationException {
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		ConfigurationService configurationService = (ConfigurationService) context.getBean("configurationService");
		setConfiguration(configurationService.obtenerUltimaConfiguracion());
	}

	public ConfiguracionPuertosFrame(ActionListener actionListener) {
		
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		if(configuration != null){
			getComMaster().setText(configuration.getPuertoComMaster());
			getComL0().setText(configuration.getPuertoComL0());
			getChkL0Integrado().setSelected(configuration.getL0integrado() == 1);
		}
		this.configuration = configuration;
	}

	public JTextField getComMaster() {
		return comMaster;
	}

	public void setComMaster(JTextField comMaster) {
		this.comMaster = comMaster;
	}

	public JTextField getComL0() {
		return comL0;
	}

	public void setComL0(JTextField comL0) {
		this.comL0 = comL0;
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

	public JCheckBox getChkL0Integrado() {
		return chkL0Integrado;
	}

	public void setChkL0Integrado(JCheckBox chkL0Integrado) {
		this.chkL0Integrado = chkL0Integrado;
	}
}
