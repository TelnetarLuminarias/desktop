package com.telnetar.desktop.luminarias;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.configuration.ConfigurationException;

import com.telnetar.desktop.Util;
import com.telnetar.desktop.components.luminarias.LuminariasDatatable;
import com.telnetar.desktop.components.luminarias.LuminariasDatatableModel;
import com.telnetar.interfaces.RfInterface;
import com.telnetar.interfaces.core.PaqueteDto;

import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LuminariaAdmin extends JPanel {
	private JLabel lblLuminarias;
	private LuminariasDatatable lsLuminarias;
	private ControlPanel controlPanel;
	private JLabel lblTempValue;
	private RfInterface rfInterface;

	public LuminariaAdmin(RfInterface rfInterface) throws ConfigurationException {
		this.rfInterface = rfInterface;
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		lblLuminarias = new JLabel("Luminarias");
		lblLuminarias.setFont(new Font("Arial", Font.PLAIN, 14));
		springLayout.putConstraint(SpringLayout.NORTH, lblLuminarias, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, lblLuminarias, 10, SpringLayout.WEST, this);
		add(lblLuminarias);

		JPanel tablePanel = new JPanel(new BorderLayout());
		lsLuminarias = new LuminariasDatatable();
		lsLuminarias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ListSelectionModel rowSM = lsLuminarias.getSelectionModel();
		rowSM.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				// Ignore extra messages.
				if (e.getValueIsAdjusting())
					return;

				ListSelectionModel lsm = (ListSelectionModel) e.getSource();
				if (lsm.isSelectionEmpty()) {
					System.out.println("No rows are selected.");
				} else {
					int selectedRow = lsm.getMinSelectionIndex();
					getControlPanel().setLuminariahist(((LuminariasDatatableModel)lsLuminarias.getModel()).getRowObject(selectedRow));
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(lsLuminarias);
		tablePanel.add(scrollPane, BorderLayout.CENTER);
		tablePanel.add(lsLuminarias.getTableHeader(), BorderLayout.NORTH);

		springLayout.putConstraint(SpringLayout.NORTH, tablePanel, 10, SpringLayout.SOUTH, lblLuminarias);
		springLayout.putConstraint(SpringLayout.WEST, tablePanel, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, tablePanel, -340, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, tablePanel, -60, SpringLayout.SOUTH, this);
		add(tablePanel);
		
		JPanel btnPanelAllOnOff = new JPanel();
		btnPanelAllOnOff.setBorder(new LineBorder(new Color(0, 0, 0)));
		springLayout.putConstraint(SpringLayout.NORTH, btnPanelAllOnOff, -5, SpringLayout.SOUTH, tablePanel);
		springLayout.putConstraint(SpringLayout.WEST, btnPanelAllOnOff, 0, SpringLayout.WEST, tablePanel);
		springLayout.putConstraint(SpringLayout.EAST, btnPanelAllOnOff, 0, SpringLayout.EAST, tablePanel);
		springLayout.putConstraint(SpringLayout.SOUTH, btnPanelAllOnOff, -10, SpringLayout.SOUTH, this);
		
		JButton btnEncenderTodas = new JButton("Apagar");
		btnEncenderTodas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					PaqueteDto paqueteDto = 
						new PaqueteDto(
							new Byte((byte)0xFF),
							new Byte((byte)0xFF), 
							new Byte((byte) 0x02), new Byte[] {
								(new Byte((byte) 0xFE)), new Byte((byte) 0x01) });
					getRfInterface().send(paqueteDto);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});
		btnPanelAllOnOff.add(btnEncenderTodas);
		
		JButton btnApagarTodas = new JButton("Encender");
		btnApagarTodas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					PaqueteDto paqueteDto = 
						new PaqueteDto(
							new Byte((byte)0xFF),
							new Byte((byte)0xFF), 
							new Byte((byte) 0x02), new Byte[] {
								(new Byte((byte) 0xFE)), new Byte((byte) Util.getVirtualIntensity(100).intValue()) });
					getRfInterface().send(paqueteDto);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});
		btnPanelAllOnOff.add(btnApagarTodas);
		add(btnPanelAllOnOff);

		controlPanel = new ControlPanel(getRfInterface());
		// controlPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		springLayout.putConstraint(SpringLayout.NORTH, controlPanel, 0, SpringLayout.NORTH, tablePanel);
		springLayout.putConstraint(SpringLayout.WEST, controlPanel, 5, SpringLayout.EAST, tablePanel);
		springLayout.putConstraint(SpringLayout.EAST, controlPanel, -10, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, controlPanel, -10, SpringLayout.SOUTH, this);
		add(controlPanel);
		
		JLabel lblTemp = new JLabel("\u00B0C");
		lblTemp.setFont(new Font("Tahoma", Font.PLAIN, 18));
		springLayout.putConstraint(SpringLayout.NORTH, lblTemp, 0, SpringLayout.NORTH, lblLuminarias);
		springLayout.putConstraint(SpringLayout.EAST, lblTemp, 0, SpringLayout.EAST, controlPanel);
		springLayout.putConstraint(SpringLayout.SOUTH, lblTemp, -5, SpringLayout.NORTH, controlPanel);
		add(lblTemp);
		
		setLblTempValue(new JLabel(""));
		getLblTempValue().setFont(new Font("Tahoma", Font.PLAIN, 18));
		springLayout.putConstraint(SpringLayout.NORTH, getLblTempValue(), 0, SpringLayout.NORTH, lblLuminarias);
		springLayout.putConstraint(SpringLayout.EAST, getLblTempValue(), -1, SpringLayout.WEST, lblTemp);
		springLayout.putConstraint(SpringLayout.SOUTH, getLblTempValue(), -5, SpringLayout.NORTH, controlPanel);
		add(getLblTempValue());
	}

	public JLabel getLblLuminarias() {
		return lblLuminarias;
	}

	public void setLblLuminarias(JLabel lblLuminarias) {
		this.lblLuminarias = lblLuminarias;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ControlPanel getControlPanel() {
		return controlPanel;
	}

	public void setControlPanel(ControlPanel controlPanel) {
		this.controlPanel = controlPanel;
	}

	public JLabel getLblTempValue() {
		return lblTempValue;
	}

	public void setLblTempValue(JLabel lblTempValue) {
		this.lblTempValue = lblTempValue;
	}

	public LuminariasDatatable getLsLuminarias() {
		return lsLuminarias;
	}

	public void setLsLuminarias(LuminariasDatatable lsLuminarias) {
		this.lsLuminarias = lsLuminarias;
	}

	public RfInterface getRfInterface() {
		return rfInterface;
	}

	public void setRfInterface(RfInterface rfInterface) {
		this.rfInterface = rfInterface;
	}
}
