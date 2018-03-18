package com.telnetar.interfaces;

import io.socket.IOAcknowledge;
import io.socket.SocketIO;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;
import com.telnetar.desktop.Util;
import com.telnetar.desktop.model.Configuration;
import com.telnetar.desktop.model.L0hist;
import com.telnetar.desktop.model.Roomtemperaturehist;
import com.telnetar.desktop.model.RoomtemperaturehistPK;
import com.telnetar.desktop.services.ConfigurationService;
import com.telnetar.desktop.services.L0HistService;
import com.telnetar.desktop.services.RoomtemperaturehistService;
import com.telnetar.interfaces.core.Connection;
import com.telnetar.interfaces.core.DesktopNodeConfDto;
import com.telnetar.interfaces.core.Message;
import com.telnetar.interfaces.core.MyIOCallback;
import com.telnetar.interfaces.core.PaqueteDto;
import com.telnetar.node.model.UpdateRoomTemperature;
import com.telnetar.node.model.UpdateRoomTemperatureData;

public class L0Interface {
	private Connection connection;
	private Configuration configuration;
	private RfInterface rfInterface;
	private SocketIO socketIO;
	private MyIOCallback myIOCallback;
	private Roomtemperaturehist roomTemperatureHist;

	public L0Interface(RfInterface rfInterface, Configuration configuration) throws Exception {
		setRfInterface(rfInterface);

		setConfiguration(configuration);

		newConnection(getConfiguration().getPuertoComL0());
	}

	private void newConnection(final String port) throws Exception {
		if (connection != null)
			connection.close();
		connection = new Connection(port) {
			@Override
			public void onPackageReceived(PaqueteDto paqueteDto) {
				try {
					proccessResponse(paqueteDto);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
	}

	private void proccessResponse(PaqueteDto paqueteDto) throws Exception {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		L0HistService l0HistService = (L0HistService) context.getBean("l0HistService");
		RoomtemperaturehistService roomTemperatureHistService = (RoomtemperaturehistService) context.getBean("roomTemperatureHistService");
		
		
		if (paqueteDto.getData()[0].intValue() == Message.SET_INTENSITY.getCode().intValue()) {
			// L0histDto l0histDto = NodeMain.l0histFacade.getLastEntry();
			// if(l0histDto != null){
			// NodeMain.l0histFacade.delete(l0histDto);
			// }
			L0hist l0hist = new L0hist();
			l0hist.setFecha(new Date());
			l0hist.setIntensity(Util.fromByteToInt(paqueteDto.getData()[1]));
			l0HistService.insertar(l0hist);

//			NodeMain.actualIntensity = l0hist.getIntensity();
			getRfInterface().send(paqueteDto);
		} else if (paqueteDto.getData()[0].intValue() == Message.SIGNAL_RED.getCode().intValue()) {
			getRfInterface().send(paqueteDto);
		} else if (paqueteDto.getData()[0].intValue() == Message.ROOM_TEMPERATURE.getCode().intValue()) {
			roomTemperatureHist = new Roomtemperaturehist();
			RoomtemperaturehistPK roomTemperatureHistPk = 
				new RoomtemperaturehistPK(
					Util.fromByteToInt(paqueteDto.getLow()), Util.fromByteToInt(paqueteDto.getHigh()), new Date()
				);
			roomTemperatureHist.setId(roomTemperatureHistPk);
			roomTemperatureHist.setTemphight(Util.fromByteToInt(paqueteDto.getData()[1]));
			roomTemperatureHist.setTemplow(Util.fromByteToInt(paqueteDto.getData()[2]));
			roomTemperatureHistService.insertar(roomTemperatureHist);

			connectToNodeJs();
			UpdateRoomTemperature roomTemperature = new UpdateRoomTemperature(
					new UpdateRoomTemperatureData(roomTemperatureHist, getConfiguration().getNodename()));
			getSocketIO().emit("updateRoomTemperatureData", new IOAcknowledge() {
				@Override
				public void ack(Object... arg0) {
					// Método no implementado
				}
			}, roomTemperature.getData());
		}
	}

	private void connectToNodeJs() throws MalformedURLException {
		if ((getSocketIO() == null || !getSocketIO().isConnected()) && getConfiguration().getNodejsserver() != null
				&& getConfiguration().getNodejsport() != null && getConfiguration().getNodejstype() != null) {
			Gson gson = new Gson();
			String json = gson.toJson(
					new DesktopNodeConfDto(getConfiguration().getNodename(), getConfiguration().getNodejstype()));
			Properties properties = new Properties();
			properties.put("json", json);
			setSocketIO(new SocketIO(
					getConfiguration().getNodejsserver() + ":" + getConfiguration().getNodejsport(), properties));
			setMyIOCallback(new MyIOCallback(getSocketIO()));
			getSocketIO().connect(getMyIOCallback());
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public RfInterface getRfInterface() {
		return rfInterface;
	}

	public void setRfInterface(RfInterface rfInterface) {
		this.rfInterface = rfInterface;
	}

	public SocketIO getSocketIO() {
		return socketIO;
	}

	public void setSocketIO(SocketIO socketIO) {
		this.socketIO = socketIO;
	}

	public MyIOCallback getMyIOCallback() {
		return myIOCallback;
	}

	public void setMyIOCallback(MyIOCallback myIOCallback) {
		this.myIOCallback = myIOCallback;
	}

	public Roomtemperaturehist getRoomTemperatureHist() {
		return roomTemperatureHist;
	}

	public void setRoomTemperatureHist(Roomtemperaturehist roomTemperatureHist) {
		this.roomTemperatureHist = roomTemperatureHist;
	}

}