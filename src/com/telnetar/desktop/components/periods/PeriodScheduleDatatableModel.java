package com.telnetar.desktop.components.periods;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.telnetar.desktop.Util;
import com.telnetar.desktop.model.Periodschedule;

public class PeriodScheduleDatatableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	private String[] columnNames = { "ID", "Fecha de inicio", "Descripción", "Grupo"};
	private String[] columnNames = { "Fecha de inicio", "Descripción"};
	private List<Periodschedule> lsData;

	@SuppressWarnings("rawtypes")
	private Vector data;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PeriodScheduleDatatableModel(List<Periodschedule> lsData) {
		this.lsData = lsData;
		Iterator<Periodschedule> iterator = lsData.iterator();
		Vector data = new Vector();
		Vector row = new Vector();
		while (iterator.hasNext()) {
			Periodschedule periodschedule = iterator.next();
			row = new Vector(columnNames.length);
			Calendar gregorianCalendar = GregorianCalendar.getInstance();
			gregorianCalendar.setTimeInMillis(periodschedule.getInitDate().longValue());
			row.add(Util.formatDate(gregorianCalendar.getTime()));
			row.add(periodschedule.getDescription());
//			row.add(periodschedule.getIdGrupoLuminaria());
			data.add(row);
		}
		this.data = data;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addData(Periodschedule periodschedule){
		if(periodschedule != null){
			this.lsData.add(periodschedule);
			Vector row = new Vector(columnNames.length);
			Calendar gregorianCalendar = GregorianCalendar.getInstance();
			gregorianCalendar.setTimeInMillis(periodschedule.getInitDate().longValue());
			row.add(Util.formatDate(gregorianCalendar.getTime()));
			row.add(periodschedule.getDescription());
			data.add(row);
			fireTableDataChanged();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void updateData(Periodschedule periodschedule, int pRow){
		if(periodschedule != null){
			Calendar gregorianCalendar = GregorianCalendar.getInstance();
			gregorianCalendar.setTimeInMillis(periodschedule.getInitDate().longValue());
			((Vector)data.get(pRow)).set(0, Util.formatDate(gregorianCalendar.getTime()));
			((Vector)data.get(pRow)).set(1, periodschedule.getDescription());
			
			fireTableDataChanged();
		}
	}
	
	public void deleteData(Periodschedule periodschedule, int rowSelected) {
		data.remove(rowSelected);
		fireTableDataChanged();
		this.lsData.remove(periodschedule);
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
		return (data.size() != 0) ? ((Vector) data.get(rowIndex)).get(columnIndex) : "";
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

	public Periodschedule getRowObject(int row){
		return this.lsData.get(row);
	}

	
}
