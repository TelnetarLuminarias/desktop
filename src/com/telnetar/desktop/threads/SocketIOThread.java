package com.telnetar.desktop.threads;

import io.socket.IOAcknowledge;
import io.socket.SocketIO;

import java.util.Properties;

import org.apache.commons.configuration.ConfigurationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;
import com.telnetar.desktop.model.Configuration;
import com.telnetar.desktop.services.ConfigurationService;
import com.telnetar.interfaces.RfInterface;
import com.telnetar.interfaces.core.DesktopNodeConfDto;
import com.telnetar.interfaces.core.MyIOCallback;

public class SocketIOThread extends Thread {
	private SocketIO socketIO;
	private RfInterface rfInterface;
	private MyIOCallback myIOCallback;
	private String json, dest;
	private Configuration configuration;

	public SocketIOThread(String json, String dest, RfInterface rfInterface) throws ConfigurationException {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		ConfigurationService configurationService = (ConfigurationService) context.getBean("configurationService");
		
		this.json = json;
		this.dest = dest;
		this.rfInterface = rfInterface;
		this.configuration = configurationService.obtenerUltimaConfiguracion();
	}

	public void run() {
		try {
			if ((getSocketIO() == null || !getSocketIO().isConnected())
					&& getConfiguration().getNodejsserver() != null && getConfiguration().getNodejsport() != null
					&& getConfiguration().getNodejstype() != null) {
				Gson gson = new Gson();
				String json = gson.toJson(new DesktopNodeConfDto(getConfiguration().getNodename(),
						getConfiguration().getNodejstype()));
				Properties properties = new Properties();
				properties.put("json", json);
				setSocketIO(new SocketIO(
						getConfiguration().getNodejsserver() + ":" + getConfiguration().getNodejsport(),
						properties));
				setMyIOCallback(new MyIOCallback(getSocketIO(), rfInterface));
				getSocketIO().connect(getMyIOCallback());
			}

			getSocketIO().emit(dest, new IOAcknowledge() {
				@Override
				public void ack(Object... arg0) {
				}
			}, json);
		} catch (Exception e) {

		}
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public SocketIO getSocketIO() {
		return socketIO;
	}

	public void setSocketIO(SocketIO socketIO) {
		this.socketIO = socketIO;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public String getDest() {
		return dest;
	}

	public void setDest(String dest) {
		this.dest = dest;
	}

	public RfInterface getRfInterface() {
		return rfInterface;
	}

	public void setRfInterface(RfInterface rfInterface) {
		this.rfInterface = rfInterface;
	}

	public MyIOCallback getMyIOCallback() {
		return myIOCallback;
	}

	public void setMyIOCallback(MyIOCallback myIOCallback) {
		this.myIOCallback = myIOCallback;
	}
}
