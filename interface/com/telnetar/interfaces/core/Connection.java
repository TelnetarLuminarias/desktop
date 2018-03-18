package com.telnetar.interfaces.core;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.InputStream;
import java.io.OutputStream;

public abstract class Connection {
	private OutputStream outputStream;

	public Connection(String portName) throws Exception {
		connect(portName);
	}

	private void connect(String portName) throws Exception {
		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
		if (portIdentifier.isCurrentlyOwned()) {
			System.out.println("Error: Port is currently in use");
		} else {
			CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);

			if (commPort instanceof SerialPort) {
				SerialPort serialPort = (SerialPort) commPort;
				serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
						SerialPort.PARITY_NONE);

				InputStream in = serialPort.getInputStream();
				outputStream = serialPort.getOutputStream();

				serialPort.addEventListener(new SerialReader(in) {
					@Override
					public void onDataReceived(PaqueteDto paqueteDto) {
						onPackageReceived(paqueteDto);
					}

				});
				serialPort.notifyOnDataAvailable(true);
				System.out.println("Listening on com " + portName);
			} else {
				System.out.println("Error: Only serial ports are handled by this example.");
			}
		}
	}

	public void send(byte[] msg) throws Exception {
		outputStream.write(msg);
	}

	public abstract void onPackageReceived(PaqueteDto paqueteDto);

	public void close() {
		// TODO Auto-generated method stub
	}
}
