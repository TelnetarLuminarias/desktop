package com.telnetar.desktop.configuracion;

import javax.swing.JFrame;
import javax.swing.SpringLayout;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.telnetar.desktop.services.SuperService;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JPanel;
import javax.persistence.PersistenceException;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ConfiguracionBaseDeDatosFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField usuarioTxt, passwordTxt, hostTxt, nombreDbTxt;
	private JButton btnCancelar, btnAceptar;
	private int closeOperation;
	
	public ConfiguracionBaseDeDatosFrame(int closeOperation) throws ConfigurationException {
		this.closeOperation = closeOperation;
		setDefaultCloseOperation(closeOperation);
		initUI();
		readDbConfig();
	}

	private void readDbConfig() throws ConfigurationException {
		Configuration config = new PropertiesConfiguration("db.properties");
		getUsuarioTxt().setText(config.getString("user"));
		getPasswordTxt().setText(config.getString("password"));
		getHostTxt().setText(config.getString("host"));
		getNombreDbTxt().setText(config.getString("dbname"));
	}

	private void initUI() {
		setBounds(50, 100, 350, 200);
		setResizable(false);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
		springLayout.putConstraint(SpringLayout.NORTH, lblUsuario, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblUsuario, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblUsuario, 90, SpringLayout.WEST, getContentPane());
		getContentPane().add(lblUsuario);
		
		usuarioTxt = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, usuarioTxt, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, usuarioTxt, 4, SpringLayout.EAST, lblUsuario);
		springLayout.putConstraint(SpringLayout.EAST, usuarioTxt, -10, SpringLayout.EAST, getContentPane());
		getContentPane().add(usuarioTxt);
		usuarioTxt.setColumns(10);
		
		passwordTxt = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, passwordTxt, 6, SpringLayout.SOUTH, usuarioTxt);
		springLayout.putConstraint(SpringLayout.EAST, passwordTxt, 0, SpringLayout.EAST, usuarioTxt);
		getContentPane().add(passwordTxt);
		passwordTxt.setColumns(10);
		
		hostTxt = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, hostTxt, 6, SpringLayout.SOUTH, passwordTxt);
		springLayout.putConstraint(SpringLayout.EAST, hostTxt, 0, SpringLayout.EAST, usuarioTxt);
		getContentPane().add(hostTxt);
		hostTxt.setColumns(10);
		
		nombreDbTxt = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, nombreDbTxt, 6, SpringLayout.SOUTH, hostTxt);
		springLayout.putConstraint(SpringLayout.EAST, nombreDbTxt, 0, SpringLayout.EAST, usuarioTxt);
		getContentPane().add(nombreDbTxt);
		nombreDbTxt.setColumns(10);
		
		JLabel lblClave = new JLabel("Clave:");
		springLayout.putConstraint(SpringLayout.NORTH, lblClave, 9, SpringLayout.SOUTH, lblUsuario);
		springLayout.putConstraint(SpringLayout.WEST, passwordTxt, 4, SpringLayout.EAST, lblClave);
		lblClave.setFont(new Font("Arial", Font.PLAIN, 14));
		springLayout.putConstraint(SpringLayout.EAST, lblClave, 0, SpringLayout.EAST, lblUsuario);
		springLayout.putConstraint(SpringLayout.WEST, lblClave, 0, SpringLayout.WEST, lblUsuario);
		getContentPane().add(lblClave);
		
		JLabel lblHost = new JLabel("Host:");
		springLayout.putConstraint(SpringLayout.NORTH, lblHost, 9, SpringLayout.SOUTH, lblClave);
		springLayout.putConstraint(SpringLayout.WEST, hostTxt, 4, SpringLayout.EAST, lblHost);
		lblHost.setFont(new Font("Arial", Font.PLAIN, 14));
		springLayout.putConstraint(SpringLayout.EAST, lblHost, 0, SpringLayout.EAST, lblUsuario);
		springLayout.putConstraint(SpringLayout.WEST, lblHost, 0, SpringLayout.WEST, lblUsuario);
		getContentPane().add(lblHost);
		
		JLabel lblNombreDb = new JLabel("Nombre DB:");
		springLayout.putConstraint(SpringLayout.NORTH, lblNombreDb, 9, SpringLayout.SOUTH, lblHost);
		springLayout.putConstraint(SpringLayout.WEST, nombreDbTxt, 4, SpringLayout.EAST, lblNombreDb);
		lblNombreDb.setFont(new Font("Arial", Font.PLAIN, 14));
		springLayout.putConstraint(SpringLayout.EAST, lblNombreDb, 0, SpringLayout.EAST, lblUsuario);
		springLayout.putConstraint(SpringLayout.WEST, lblNombreDb, 0, SpringLayout.WEST, lblUsuario);
		getContentPane().add(lblNombreDb);
		
		JPanel panel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel, 6, SpringLayout.SOUTH, nombreDbTxt);
		springLayout.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panel, -10, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel, -0, SpringLayout.EAST, getContentPane());
		getContentPane().add(panel);
		
		if(closeOperation != JFrame.EXIT_ON_CLOSE){
			setBtnCancelar(new JButton("Cancelar"));
			panel.add(getBtnCancelar());
		}
		
		setBtnAceptar(new JButton("Aceptar"));
		panel.add(getBtnAceptar());
		
		JButton btnProbarConeccin = new JButton("Probar conecci\u00F3n");
		btnProbarConeccin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					SuperService.validarDatosConeccion(getUsuarioTxt().getText().trim(), getPasswordTxt().getText().trim(), getHostTxt().getText().trim(), getNombreDbTxt().getText().trim());
					JOptionPane.showMessageDialog(null, "Los parametros son correctos, aseguresé de guardar los cambios.", "Exito!", JOptionPane.INFORMATION_MESSAGE);
				} catch (ConfigurationException e1) {
					e1.printStackTrace();
				} catch (PersistenceException e2) {
					JOptionPane.showMessageDialog(null, "No se ha establecido coneción con la base de datos", "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel.add(btnProbarConeccin);
		
		setVisible(true);
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}
	public void setBtnCancelar(JButton btnCancelar) {
		this.btnCancelar = btnCancelar;
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
	}
	public JButton getBtnAceptar() {
		return btnAceptar;
	}
	public void setBtnAceptar(JButton btnAceptar) {
		this.btnAceptar = btnAceptar;
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(!getNombreDbTxt().getText().trim().isEmpty()){
						PropertiesConfiguration config = new PropertiesConfiguration("db.properties");
						config.setProperty("user", getUsuarioTxt().getText().trim());
						config.setProperty("password", getPasswordTxt().getText().trim());
						config.setProperty("host", getHostTxt().getText().trim());
						config.setProperty("dbname", getNombreDbTxt().getText().trim());
						
						try{
							validarConeccionBasica(getUsuarioTxt().getText().trim(), getPasswordTxt().getText().trim(), getHostTxt().getText().trim());
							try{
								validarNombreBaseDeDatos(getUsuarioTxt().getText().trim(), getPasswordTxt().getText().trim(), getHostTxt().getText().trim(), getNombreDbTxt().getText().trim());
								config.save();
								JOptionPane.showMessageDialog(null, "Los datos fueron guardados con éxito", "Exito!", JOptionPane.INFORMATION_MESSAGE);
								setVisible(false);
								if(closeOperation != JFrame.EXIT_ON_CLOSE){
									dispose();
								}else{
									System.exit(0);
								}
							}catch(PersistenceException ex){
								int opcion = JOptionPane.showConfirmDialog(null, "La base de datos no existe, desea crear el software utilizando esta configuración?", "Error!", JOptionPane.YES_NO_OPTION);
								if(opcion == JOptionPane.YES_OPTION){
									config.save();
									Boolean error = Boolean.TRUE;
									int frecuencia = 5;
									while(error){
										String xFrecuencia = JOptionPane.showInputDialog(null, "Ingrese la frecuencia de auditorias a las luminarias (expresadas en minutos)", 5);
										try{
											frecuencia = new Integer(xFrecuencia).intValue();
											if(frecuencia < 5){
												JOptionPane.showMessageDialog(null, "El intervalo no puede ser inferior a 5 minutos", "Error!", JOptionPane.ERROR_MESSAGE);
											}else{
												error = Boolean.FALSE;
											}
										}catch(NumberFormatException er){
											JOptionPane.showMessageDialog(null, "El valor debe ser numerico", "Error!", JOptionPane.ERROR_MESSAGE);
										}
									}
									
									int l0Integrado = JOptionPane.showConfirmDialog(null, "El módulo L0 está integrado al nodo?", "L0", JOptionPane.YES_NO_OPTION);
									l0Integrado = l0Integrado == JOptionPane.YES_OPTION ? 1 : 0;
									String nodename = JOptionPane.showInputDialog("Ingrese el nombre del nodo", "nodo de escritorio");
									nodename = nodename.trim().isEmpty() ? "nodo de escritorio" : nodename; 
									
									SuperService.crearEsquema(frecuencia, l0Integrado, nodename);
									JOptionPane.showMessageDialog(null, "Los datos fueron guardados con éxito", "Exito!", JOptionPane.INFORMATION_MESSAGE);
									JOptionPane.showMessageDialog(null, "Por favor reinicie el sistema", "TELNETAR", JOptionPane.INFORMATION_MESSAGE);
									setVisible(false);
									if(closeOperation != JFrame.EXIT_ON_CLOSE){
										dispose();
									}else{
										System.exit(0);
									}
								}else{
									readDbConfig();
								}
							}
						}catch(PersistenceException ex){
							JOptionPane.showMessageDialog(null, "Datos incorrectos de conección", "Error!", JOptionPane.ERROR_MESSAGE);
						}
					}else {
						JOptionPane.showMessageDialog(null, "Debe seleccionar un nombre de base de datos", "Error!", JOptionPane.ERROR_MESSAGE);
					}
				} catch (ConfigurationException e1) {
					JOptionPane.showMessageDialog(null, "No es posible acceder a la configuración de la base de datos. Por favor comuniquesé con el técnico especializado.", "Error!", JOptionPane.ERROR_MESSAGE);
				} 
			}

			private void validarNombreBaseDeDatos(String user, String password, String host, String dbname) throws ConfigurationException, PersistenceException {
				SuperService.validarDatosConeccion(user, password, host, dbname);
			}

			private void validarConeccionBasica(String user, String password, String host) throws ConfigurationException {
				SuperService.validarDatosConeccion(user, password, host, "");
			}
		});
	}

	public JTextField getUsuarioTxt() {
		return usuarioTxt;
	}

	public void setUsuarioTxt(JTextField usuarioTxt) {
		this.usuarioTxt = usuarioTxt;
	}

	public JTextField getPasswordTxt() {
		return passwordTxt;
	}

	public void setPasswordTxt(JTextField passwordTxt) {
		this.passwordTxt = passwordTxt;
	}

	public JTextField getHostTxt() {
		return hostTxt;
	}

	public void setHostTxt(JTextField driverTxt) {
		this.hostTxt = driverTxt;
	}

	public JTextField getNombreDbTxt() {
		return nombreDbTxt;
	}

	public void setNombreDbTxt(JTextField urlTxt) {
		this.nombreDbTxt = urlTxt;
	}
}
