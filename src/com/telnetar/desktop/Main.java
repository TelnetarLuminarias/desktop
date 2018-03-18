package com.telnetar.desktop;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.persistence.PersistenceException;
import javax.swing.JDesktopPane;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.apache.commons.configuration.ConfigurationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.telnetar.desktop.configuracion.ConfiguracionBaseDeDatosFrame;
import com.telnetar.desktop.configuracion.ConfiguracionPuertosFrame;
import com.telnetar.desktop.luminarias.LuminariaAdmin;
import com.telnetar.desktop.model.Configuration;
import com.telnetar.desktop.model.Luminaria;
import com.telnetar.desktop.services.ConfigurationService;
import com.telnetar.desktop.services.LuminariaService;
import com.telnetar.desktop.threads.DaemonThread;
import com.telnetar.desktop.threads.ScheduleThread;
import com.telnetar.desktop.threads.SignalRedThread;
import com.telnetar.interfaces.L0Interface;
import com.telnetar.interfaces.RfInterface;
import com.telnetar.interfaces.core.PaqueteDto;

import java.awt.event.KeyEvent;
import java.util.List;
import java.awt.event.InputEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.Color;

public class Main extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenuBar menuBar;
	private LuminariaAdmin luminariaAdmin;
	private JLabel lblTemperatura;
	private List<Luminaria> lsLuminarias;
	private ConfiguracionPuertosFrame configuracionPuertosFrame;
	private ConfiguracionBaseDeDatosFrame configuracionBaseDeDatosFrame;
	private com.telnetar.desktop.controlautomatico.Main controlAutomaticoFrame;
	private JDesktopPane desktopPane;
	private RfInterface rfInterface;
	private L0Interface l0Interface;
	
	public Main() {
		try {
			@SuppressWarnings("resource")
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			LuminariaService luminariaService = (LuminariaService) context.getBean("luminariaService");
		
			setLsLuminarias(luminariaService.obtenerLuminarias());
		
			iniciarInterfaces();
			
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setTitle("TELNETAR - GESTIÓN DE LUMINARIAS");
			setMinimumSize(new Dimension(900, 600));
			
			createMenu();
			
			desktopPane = new JDesktopPane();
			desktopPane.setBackground(Color.WHITE);
			getContentPane().add(desktopPane, BorderLayout.CENTER);
			desktopPane.setLayout(new BorderLayout(0, 0));
			
			luminariaAdmin = new LuminariaAdmin(getRfInterface());
			desktopPane.add(luminariaAdmin, BorderLayout.CENTER);
			
			try{
				getRfInterface().setLuminariasDatatable(luminariaAdmin.getLsLuminarias());
				getRfInterface().setLblTemperatura(getLuminariaAdmin().getLblTempValue());
			}catch(Exception e){
				JOptionPane.showMessageDialog(this, "No se pudo establecer conección con el módulo master, por favor configure los puertos y reinicie el sistema.", "Error!", JOptionPane.ERROR_MESSAGE);
			}
		} catch (PersistenceException e){
			try {
				setConfiguracionBaseDeDatosFrame(new ConfiguracionBaseDeDatosFrame(JFrame.EXIT_ON_CLOSE));
				getConfiguracionBaseDeDatosFrame().setVisible(true);
				System.out.println("No es posible acceder a la configuración de la base de datos. Por favor comuniquesé con el técnico especializado.");
			} catch (ConfigurationException e1) {
				e1.printStackTrace();
			}
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}

	private void iniciarInterfaces() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		ConfigurationService configurationService = (ConfigurationService) context.getBean("configurationService");
		
		try {
			Configuration configuration = configurationService.obtenerUltimaConfiguracion();
			setRfInterface(new RfInterface(configuration));
			if(configuration.getL0integrado().equals(new Integer(1))){
				setL0Interface(new L0Interface(getRfInterface(), configuration));
			}
			
			new DaemonThread(getRfInterface(), getL0Interface()).start();
			
			ScheduleThread scheduleThread = new ScheduleThread(getRfInterface());
			scheduleThread.start();
			
			SignalRedThread signalRedThread = new SignalRedThread(getRfInterface());
			signalRedThread.start();
			
			PaqueteDto paqueteDto = new PaqueteDto(new Byte((byte)0xFF), new Byte((byte)0xFF),new Byte((byte) 0x02), new Byte[] {(new Byte((byte) 0x00)),new Byte((byte) 0x00) });
			getRfInterface().send(paqueteDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createMenu() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);
		JMenu mnConfiguracion = new JMenu("Configuración");
		menuBar.add(mnConfiguracion);
		
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mntmSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
		mnArchivo.add(mntmSalir);
		
		JMenuItem mntmControlAutomatico = new JMenuItem("Control automatico");
		mntmControlAutomatico.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					controlAutomaticoFrame = new com.telnetar.desktop.controlautomatico.Main();
					controlAutomaticoFrame.setVisible(true);
				} catch (ConfigurationException e1) {
					JOptionPane.showMessageDialog(null, "No es posible acceder a la configuración de la base de datos. Por favor comuniquesé con el técnico especializado.", "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mnConfiguracion.add(mntmControlAutomatico);
		
		JMenuItem mntmConfiguracionPuertos = new JMenuItem("Puertos");
		mntmConfiguracionPuertos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					configuracionPuertosFrame = new ConfiguracionPuertosFrame();
					configuracionPuertosFrame.setVisible(true);
				} catch (ConfigurationException e1) {
					JOptionPane.showMessageDialog(null, "No es posible acceder a la configuración de la base de datos. Por favor comuniquesé con el técnico especializado.", "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mnConfiguracion.add(mntmConfiguracionPuertos);
		
		JMenuItem mntmConfiguracionBaseDeDatos = new JMenuItem("Base de datos");
		mntmConfiguracionBaseDeDatos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					setConfiguracionBaseDeDatosFrame(new ConfiguracionBaseDeDatosFrame(JFrame.DISPOSE_ON_CLOSE));
					getConfiguracionBaseDeDatosFrame().setVisible(true);
				} catch (ConfigurationException e1) {
					JOptionPane.showMessageDialog(null, "No es posible acceder a la configuración de la base de datos. Por favor comuniquesé con el técnico especializado.", "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mnConfiguracion.add(mntmConfiguracionBaseDeDatos);
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public JDesktopPane getDesktopPane() {
		return desktopPane;
	}

	public void setDesktopPane(JDesktopPane desktopPane) {
		this.desktopPane = desktopPane;
	}

	public ConfiguracionPuertosFrame getConfiguracionPuertosFrame() {
		return configuracionPuertosFrame;
	}

	public void setConfiguracionPuertosFrame(ConfiguracionPuertosFrame configuracionPuertosFrame) {
		this.configuracionPuertosFrame = configuracionPuertosFrame;
	}

	public com.telnetar.desktop.controlautomatico.Main getControlAutomaticoFrame() {
		return controlAutomaticoFrame;
	}

	public void setControlAutomaticoFrame(com.telnetar.desktop.controlautomatico.Main controlAutomaticoFrame) {
		this.controlAutomaticoFrame = controlAutomaticoFrame;
	}

	public RfInterface getRfInterface() {
		return rfInterface;
	}

	public void setRfInterface(RfInterface rfInterface) {
		this.rfInterface = rfInterface;
	}

	public L0Interface getL0Interface() {
		return l0Interface;
	}

	public void setL0Interface(L0Interface l0Interface) {
		this.l0Interface = l0Interface;
	}

	public LuminariaAdmin getLuminariaAdmin() {
		return luminariaAdmin;
	}

	public void setLuminariaAdmin(LuminariaAdmin luminariaAdmin) {
		this.luminariaAdmin = luminariaAdmin;
	}

	public List<Luminaria> getLsLuminarias() {
		return lsLuminarias;
	}

	public void setLsLuminarias(List<Luminaria> lsLuminarias) {
		this.lsLuminarias = lsLuminarias;
	}

	public JLabel getLblTemperatura() {
		return lblTemperatura;
	}

	public void setLblTemperatura(JLabel lblTemperatura) {
		this.lblTemperatura = lblTemperatura;
	}

	public ConfiguracionBaseDeDatosFrame getConfiguracionBaseDeDatosFrame() {
		return configuracionBaseDeDatosFrame;
	}

	public void setConfiguracionBaseDeDatosFrame(ConfiguracionBaseDeDatosFrame configuracionBaseDeDatosFrame) {
		this.configuracionBaseDeDatosFrame = configuracionBaseDeDatosFrame;
	}
}
