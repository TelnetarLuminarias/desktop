package com.telnetar.desktop.controlautomatico;

import java.awt.Color;
import java.text.SimpleDateFormat;

import javax.swing.JPanel;
import javax.swing.SpringLayout;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;

import com.telnetar.desktop.Util;
import com.telnetar.desktop.components.periods.PeriodScheduleDetailDatatable;

public class GraficoPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultCategoryDataset dataset;

	public GraficoPanel(PeriodScheduleDetailDatatable periodScheduleDetailDatatable) {
		SpringLayout layout = new SpringLayout();
		setLayout(layout);

		XYDataset dataset = createDataset();
		JFreeChart chart = createChart(dataset);
		ChartPanel chartPanel = new ChartPanel(chart);
		layout.putConstraint(SpringLayout.NORTH, chartPanel, 5, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH, chartPanel, -5, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.EAST, chartPanel, -5, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.WEST, chartPanel, 5, SpringLayout.WEST, this);
		// chartPanel.setPreferredSize(new Dimension(500, 270));
		add(chartPanel);
	}

	/**
	 * Creates a sample dataset.
	 *
	 * @return The dataset.
	 */
	private static XYDataset createDataset() {
		try {
			TimeSeries s1 = new TimeSeries("", Minute.class);
			s1.add(new Minute(Util.parseDate("1:30", "HH:mm")), 10);
			s1.add(new Minute(Util.parseDate("2:30", "HH:mm")), 20);
			s1.add(new Minute(Util.parseDate("3:30", "HH:mm")), 40);
			s1.add(new Minute(Util.parseDate("6:30", "HH:mm")), 0);
			s1.add(new Minute(Util.parseDate("10:00", "HH:mm")), 90);
			s1.add(new Minute(Util.parseDate("11:30", "HH:mm")), 60);
			s1.add(new Minute(Util.parseDate("11:59", "HH:mm")), 50);
			TimeSeriesCollection dataset = new TimeSeriesCollection();
			dataset.addSeries(s1);
			return dataset;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Creates a sample chart.
	 *
	 * @param dataset
	 *            a dataset.
	 *
	 * @return The chart.
	 */
	private static JFreeChart createChart(XYDataset dataset) {
		// create the chart...
		JFreeChart chart = ChartFactory.createTimeSeriesChart("TITULO", "Hora", // x-axis
																				// label
				"Intensidad", // y-axis label
				dataset, false, // create legend?
				false, // generate tooltips?
				false);// generate URLs?
		chart.setBackgroundPaint(Color.white);
		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);
		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}
		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("HH:mm"));
		return chart;

	}

	public DefaultCategoryDataset getDataset() {
		return dataset;
	}

	public void setDataset(DefaultCategoryDataset dataset) {
		this.dataset = dataset;
	}

}
