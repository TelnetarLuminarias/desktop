package com.telnetar.interfaces;

import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;

import javax.swing.JLabel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;
import com.telnetar.desktop.Util;
import com.telnetar.desktop.components.luminarias.LuminariasDatatable;
import com.telnetar.desktop.components.luminarias.LuminariasDatatableModel;
import com.telnetar.desktop.model.Configuration;
import com.telnetar.desktop.model.Luminaria;
import com.telnetar.desktop.model.LuminariaPK;
import com.telnetar.desktop.model.Luminariahist;
import com.telnetar.desktop.model.LuminariahistPK;
import com.telnetar.desktop.model.Roomtemperaturehist;
import com.telnetar.desktop.model.RoomtemperaturehistPK;
import com.telnetar.desktop.services.LuminariaService;
import com.telnetar.desktop.services.LuminariahistService;
import com.telnetar.desktop.services.RoomtemperaturehistService;
import com.telnetar.desktop.threads.PanicoThread;
import com.telnetar.desktop.threads.SocketIOThread;
import com.telnetar.interfaces.core.Connection;
import com.telnetar.interfaces.core.DesktopNodeConfDto;
import com.telnetar.interfaces.core.Message;
import com.telnetar.interfaces.core.MyIOCallback;
import com.telnetar.interfaces.core.PaqueteDto;
import com.telnetar.node.model.ChanelPanicButtonDataDto;
import com.telnetar.node.model.ChanelPanicButtonDto;
import com.telnetar.node.model.UpdateDataDto;
import com.telnetar.node.model.UpdateDataLumiHistDto;

import io.socket.IOAcknowledge;
import io.socket.SocketIO;

public class RfInterface {
	private Connection connection;
	private String port;
	private SocketIO socketIO;
	private MyIOCallback myIOCallback;
	private Configuration configuration;
	public static Boolean panicoActive = Boolean.FALSE;
	private Date lastPackageRedReceived;
	private Luminaria l0;
	private Roomtemperaturehist roomTemperatureHist;
	private LuminariasDatatable luminariasDatatable = null;
	private JLabel lblTemperatura;

	public RfInterface(Configuration configuration) throws Exception {
		this.configuration = configuration;
		init();
	}

	public RfInterface(Configuration configuration, Luminaria l0) throws Exception {
		this.configuration = configuration;
		this.l0 = l0;
		init();
	}

	private void init() throws Exception {
		setLastPackageRedReceived(Util.addToDate(new Date(), 0, 0, -10, 0));

		newConnection(getConfiguration().getPuertoComMaster());
		setPort(getConfiguration().getPuertoComMaster());

		connectToNode();
	}

	private void connectToNode() throws MalformedURLException {
		if ((getSocketIO() == null || !getSocketIO().isConnected()) && getConfiguration().getNodejsserver() != null
				&& getConfiguration().getNodejsport() != null && getConfiguration().getNodejstype() != null) {
			Gson gson = new Gson();
			String json = gson.toJson(
					new DesktopNodeConfDto(getConfiguration().getNodename(), getConfiguration().getNodejstype()));
			Properties properties = new Properties();
			properties.put("json", json);
			setSocketIO(new SocketIO(
					getConfiguration().getNodejsserver() + ":" + getConfiguration().getNodejsport(), properties));
			setMyIOCallback(new MyIOCallback(getSocketIO(), this));
			getSocketIO().connect(getMyIOCallback());
		}
	}

	private void newConnection(final String port) throws Exception {
		if (connection != null)
			connection.close();
		connection = new Connection(port) {
			@Override
			public void onPackageReceived(PaqueteDto paqueteDto) {
				try {
					proccessResponse(port, paqueteDto);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
	}

	private void proccessResponse(final String port, PaqueteDto paqueteDto) throws Exception {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		LuminariaService luminariaService = (LuminariaService) context.getBean("luminariaService");
		RoomtemperaturehistService roomtemperaturehistService = (RoomtemperaturehistService) context.getBean("roomTemperatureHistService");
		LuminariahistService luminariahistService = (LuminariahistService) context.getBean("luminariahistService");
		
		try {
			connectToNode();
		} catch (Exception e) {

		}
		if (paqueteDto.getData()[0] != null) {
			if (paqueteDto.getData()[0].intValue() == Message.PANIC.getCode().intValue()) {
				// Por cada señar de pánico que recibo, le respondo que llegó
				// bien
				paqueteDto.setLength(new Byte((byte) 0x01));
				paqueteDto.setData(new Byte[] { new Byte((byte) 0x00) });
				send(paqueteDto);

				if (!panicoActive) {
					Thread.sleep(500);
					Gson gson = new Gson();
					// Envio el mensaje a traves de NODE hacia la web
					String json = gson.toJson(new ChanelPanicButtonDto(
							new ChanelPanicButtonDataDto(Util.fromByteToInt(paqueteDto.getHigh()),
									Util.fromByteToInt(paqueteDto.getLow()), getConfiguration().getNodename()),
							"true"));
					new SocketIOThread(json, "panicButton", this).start();
					new PanicoThread(this).start();
				}
			}
			// Recibo señal de temperatura ambiente
			else if (paqueteDto.getData()[0].intValue() == Message.ROOM_TEMPERATURE.getCode().intValue()) {
				if (l0 == null) {
					l0 = new Luminaria(
							new LuminariaPK(
								Util.fromByteToInt(paqueteDto.getHigh()),
								Util.fromByteToInt(paqueteDto.getLow())
							), 
						"l0");
					luminariaService.insertar(l0);
					setL0(l0);
				}
				setRoomTemperatureHist(new Roomtemperaturehist());
				RoomtemperaturehistPK roomTemperatureHistPk = new RoomtemperaturehistPK(
						Util.fromByteToInt(paqueteDto.getLow()), Util.fromByteToInt(paqueteDto.getHigh()), new Date());
				getRoomTemperatureHist().setId(roomTemperatureHistPk);
				getRoomTemperatureHist().setTemphight(Util.fromByteToInt(paqueteDto.getData()[1]));
				getRoomTemperatureHist().setTemplow(Util.fromByteToInt(paqueteDto.getData()[2]));
				roomtemperaturehistService.insertar(getRoomTemperatureHist());
				getLblTemperatura().setText(
					new Integer(Util.getTemperature(getRoomTemperatureHist().getTemphight(), getRoomTemperatureHist().getTemplow())).toString()
				);
			}
			// Recibo señal de red
			else if (paqueteDto.getData()[0].intValue() == Message.SIGNAL_RED.getCode().intValue()) {
				PaqueteDto apagarMensaje = new PaqueteDto(new Byte((byte) 0xFF), new Byte((byte) 0xFF),
						new Byte((byte) 0x02), new Byte[] { new Byte((byte) 0xFE), new Byte((byte) 0x01) });
				send(apagarMensaje);
			}
			// Recibo error desde la luminaria
			else if (paqueteDto.getData()[0].intValue() == Message.ERROR_FROM_LIGHTING.getCode().intValue()
					&& paqueteDto.getData()[1].intValue() == Message.ERROR_FROM_LIGHTING.getCode().intValue()) {
				// Manejar el error
				System.out.println("ERROR DESDE LA LUMINARIA " + paqueteDto.getHigh() + " " + paqueteDto.getLow());
			} // Recibo el reporte de la luminaria
			else if (paqueteDto.getData()[0].intValue() == Message.AUDIT_RESPONSE.getCode().intValue()) {
				
				Luminaria luminaria = 
					luminariaService.obtenerPorId(
						new LuminariaPK(Util.fromByteToInt(paqueteDto.getHigh()), Util.fromByteToInt(paqueteDto.getLow())));
				// Si la luminaria no está en la lista significa que se acaba de
				// amigar.
				if (luminaria == null) {
					luminaria = new Luminaria(
						new LuminariaPK(
							Util.fromByteToInt(paqueteDto.getHigh()),
							Util.fromByteToInt(paqueteDto.getLow())), null);
					luminariaService.insertar(luminaria);
					
					((LuminariasDatatableModel)getLuminariasDatatable().getModel()).addData(
						new Luminariahist(new LuminariahistPK(luminaria.getId().getHightbyte(), luminaria.getId().getLowbyte(), new Date()), 0)
					);
				} else {
					Luminariahist luminariaHist = new Luminariahist();
					LuminariahistPK luminariahistPK = new LuminariahistPK(
							Util.fromByteToInt(paqueteDto.getHigh()), Util.fromByteToInt(paqueteDto.getLow()),
							new Timestamp(new Date().getTime()));
					luminariaHist.setId(luminariahistPK);
					luminariaHist.setIntensity(Util.fromByteToInt(paqueteDto.getData()[1]));
					luminariaHist.setTemperatureHight(Util.fromByteToInt(paqueteDto.getData()[2]));
					luminariaHist.setTemperatureLow(Util.fromByteToInt(paqueteDto.getData()[3]));
					luminariaHist.setLumiContextH(Util.fromByteToInt(paqueteDto.getData()[4]));
					luminariaHist.setLumiContextL(Util.fromByteToInt(paqueteDto.getData()[5]));
					
					try {
						luminariahistService.insertar(luminariaHist);
						((LuminariasDatatableModel)getLuminariasDatatable().getModel()).updateData(luminariaHist);
						connectToNode();
						UpdateDataDto updateDataDto = new UpdateDataDto(
								new UpdateDataLumiHistDto(luminariaHist, getConfiguration().getNodename()));
						getSocketIO().emit("updateData", new IOAcknowledge() {
							@Override
							public void ack(Object... arg0) {
								// Método no implementado
							}
						}, updateDataDto.getData());
					} catch (Exception e) {

					}
				}
			}
		}
	}

	public void send(PaqueteDto paqueteDto) throws Exception {
		try {
			connection.send(paqueteDto.getMessage());
		} catch (Exception e) {
			newConnection(getPort());
			connection.send(paqueteDto.getMessage());
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
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

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public Date getLastPackageRedReceived() {
		return lastPackageRedReceived;
	}

	public void setLastPackageRedReceived(Date lastPackageRedReceived) {
		this.lastPackageRedReceived = lastPackageRedReceived;
	}

	public Luminaria getL0() {
		return l0;
	}

	public void setL0(Luminaria l0) {
		this.l0 = l0;
	}

	public Roomtemperaturehist getRoomTemperatureHist() {
		return roomTemperatureHist;
	}

	public void setRoomTemperatureHist(Roomtemperaturehist roomTemperatureHist) {
		this.roomTemperatureHist = roomTemperatureHist;
	}

	public LuminariasDatatable getLuminariasDatatable() {
		return luminariasDatatable;
	}

	public void setLuminariasDatatable(LuminariasDatatable luminariasDatatable) {
		this.luminariasDatatable = luminariasDatatable;
	}

	public JLabel getLblTemperatura() {
		return lblTemperatura;
	}

	public void setLblTemperatura(JLabel lblTemperatura) {
		this.lblTemperatura = lblTemperatura;
	}
}