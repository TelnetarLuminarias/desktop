package com.telnetar.desktop.components.periods;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.telnetar.desktop.Util;
import com.telnetar.desktop.model.Perioddetailschedule;

public class PeriodScheduleDetailDatatableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String[] columnNames = {"Hora de inicio", "Intensidad"};
	private List<Perioddetailschedule> lsData;

	@SuppressWarnings("rawtypes")
	private Vector data;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PeriodScheduleDetailDatatableModel(List<Perioddetailschedule> lsData) {
		this.lsData = lsData;
		Iterator<Perioddetailschedule> iterator = lsData.iterator();
		Vector data = new Vector();
		Vector row = new Vector();
		while (iterator.hasNext()) {
			Perioddetailschedule perioddetailschedule = iterator.next();
			row = new Vector(columnNames.length);
			Calendar gregorianCalendar = GregorianCalendar.getInstance();
			gregorianCalendar.setTimeInMillis(perioddetailschedule.getInitHour().longValue());
			row.add(Util.formatDate(gregorianCalendar.getTime(), "HH:mm"));
			row.add(Util.getRealIntensity(perioddetailschedule.getIntensity()));
			data.add(row);
		}
		this.data = data;
	}

	@Override
	public int getRowCount() {
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

	public Perioddetailschedule getRowObject(int row){
		return this.lsData.get(row);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addData(Perioddetailschedule periodDetailschedule){
		if(periodDetailschedule != null){
			this.lsData.add(periodDetailschedule);
			Vector row = new Vector(columnNames.length);
			Calendar gregorianCalendar = GregorianCalendar.getInstance();
			gregorianCalendar.setTimeInMillis(periodDetailschedule.getInitHour().longValue());
			row.add(Util.formatDate(gregorianCalendar.getTime(), "HH:mm"));
			row.add(Util.getRealIntensity(periodDetailschedule.getIntensity()));
			
	//		row.add(periodschedule.getIdGrupoLuminaria());
			data.add(row);
			fireTableDataChanged();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void updateData(Perioddetailschedule periodDetailSchedule, int pRow){
		if(periodDetailSchedule != null){
			Calendar gregorianCalendar = GregorianCalendar.getInstance();
			gregorianCalendar.setTimeInMillis(periodDetailSchedule.getInitHour().longValue());
			((Vector)data.get(pRow)).set(0, Util.formatDate(gregorianCalendar.getTime(), "HH:mm"));
			((Vector)data.get(pRow)).set(1, Util.getRealIntensity(periodDetailSchedule.getIntensity()));
			
			fireTableDataChanged();
		}
	}
	
	public void deleteData(Perioddetailschedule periodDetailSchedule, int rowSelected) {
		if(periodDetailSchedule != null){
			data.remove(rowSelected);
			fireTableDataChanged();
			this.lsData.remove(periodDetailSchedule);
		}
	}
}
