package com.telnetar.desktop.components.luminarias;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.telnetar.desktop.Util;
import com.telnetar.desktop.model.Luminariahist;

public class LuminariasDatatableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String[] columnNames = { "ID", "Fecha", "Intensidad", "Temperatura del Led",
			"Temperatura de la luminaria" };
	private List<Luminariahist> lsData;
	
	@SuppressWarnings("rawtypes")
	private Vector data;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LuminariasDatatableModel(List<Luminariahist> lsData) {
		this.lsData = lsData;
		Iterator<Luminariahist> iterator = lsData.iterator();
		Vector data = new Vector();
		Vector row = new Vector();
		while (iterator.hasNext()) {
			Luminariahist luminariahist = iterator.next();
			row = new Vector(getColumnCount());
			row.add(luminariahist.getId().getPk());
			row.add(Util.formatDate(luminariahist.getId().getFecha(), "dd/MM/yyyy HH:mm"));
			row.add(Util.getRealIntensity(luminariahist.getIntensity()));
			row.add(Util.getTemperature(luminariahist.getTemperatureHight(), luminariahist.getTemperatureLow()));
			row.add(Util.getTemperature(luminariahist.getLumiContextH(), luminariahist.getLumiContextL()));
			data.add(row);
		}
		this.data = data;
	}

	@Override
	public int getRowCount() {
		// return data.length;
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// return data[rowIndex][columnIndex];
		return ((Vector) data.get(rowIndex)).get(columnIndex);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}

	/*
	 * Don't need to implement this method unless your table's editable.
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// Note that the data/cell address is constant,
		// no matter where the cell appears onscreen.
		if (columnIndex < 2) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * Don't need to implement this method unless your table's data can change.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// data[rowIndex][columnIndex] = aValue;
		((Vector) data.get(rowIndex)).set(columnIndex, aValue);
		fireTableCellUpdated(rowIndex, columnIndex);
	}

	public Luminariahist getRowObject(int row){
		return this.lsData.get(row);
	}

	public List<Luminariahist> getLsData() {
		return lsData;
	}

	public void setLsData(List<Luminariahist> lsData) {
		this.lsData = lsData;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addData(Luminariahist luminariahist){
		if(luminariahist != null){
			this.lsData.add(luminariahist);
			Vector row = new Vector(columnNames.length);
			row.add(luminariahist.getId().getPk());
			row.add(Util.formatDate(luminariahist.getId().getFecha(), "dd/MM/yyyy HH:mm:ss"));
			row.add(Util.getRealIntensity(luminariahist.getIntensity()));
			row.add(Util.getTemperature(luminariahist.getTemperatureHight(), luminariahist.getTemperatureLow()));
			row.add(Util.getTemperature(luminariahist.getLumiContextH(), luminariahist.getLumiContextL()));
			data.add(row);
			fireTableDataChanged();
		}
	}
	
	public void updateData(Luminariahist luminariahist){
		int row = 0;
		Iterator<Luminariahist> iterator = getLsData().iterator();
		Boolean exist = Boolean.FALSE;
		Luminariahist xLuminariahist = null;
		while(!exist && iterator.hasNext()){
			xLuminariahist = iterator.next();
			if(xLuminariahist.getId().getPk().equals(luminariahist.getId().getPk())){
				exist = Boolean.TRUE;
			}else{
				row++;
			}
		}
		if(exist){
			xLuminariahist.setIntensity(luminariahist.getIntensity());
			xLuminariahist.getId().setFecha(luminariahist.getId().getFecha());
			xLuminariahist.setLumiContextH(luminariahist.getLumiContextH());
			xLuminariahist.setLumiContextL(luminariahist.getLumiContextL());
			xLuminariahist.setTemperatureHight(luminariahist.getTemperatureHight());
			xLuminariahist.setTemperatureLow(luminariahist.getTemperatureLow());
			updateData(xLuminariahist, row);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void updateData(Luminariahist luminariahist, int pRow){
		if(luminariahist != null){
			((Vector)data.get(pRow)).set(0, luminariahist.getId().getPk());
			((Vector)data.get(pRow)).set(1, Util.formatDate(luminariahist.getId().getFecha(), "dd/MM/yyyy HH:mm:ss"));
			((Vector)data.get(pRow)).set(2, Util.getRealIntensity(luminariahist.getIntensity()));
			((Vector)data.get(pRow)).set(3, Util.getTemperature(luminariahist.getTemperatureHight(), luminariahist.getTemperatureLow()));
			((Vector)data.get(pRow)).set(4, Util.getTemperature(luminariahist.getLumiContextH(), luminariahist.getLumiContextL()));
			
			fireTableDataChanged();
		}
	}
	
	public void deleteData(Luminariahist luminariahist, int rowSelected) {
		data.remove(rowSelected);
		fireTableDataChanged();
		this.lsData.remove(luminariahist);
	}
}
