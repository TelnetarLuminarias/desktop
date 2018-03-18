package com.telnetar.interfaces.core;

import java.math.BigInteger;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.telnetar.desktop.model.Perioddetailschedule;
import com.telnetar.desktop.model.Periodschedule;
import com.telnetar.desktop.services.PeriodScheduleDetailService;
import com.telnetar.desktop.services.PeriodScheduleService;
import com.telnetar.interfaces.RfInterface;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

public class MyIOCallback implements IOCallback {
	private SocketIO socketIO;
	private RfInterface rfInterface;

	public MyIOCallback() {
	}

	public MyIOCallback(SocketIO socketIO) {
		setSocketIO(socketIO);
	}

	public MyIOCallback(SocketIO socketIO, RfInterface rfInterface) {
		setSocketIO(socketIO);
		setRfInterface(rfInterface);
	}

	@Override
	public void on(String event, IOAcknowledge ack, Object... args) {
		try {
			// Recibo un mensaje desde NodeJs y lo envio a la/s luminaria/s
			switch (event) {
			case "setValue":
				onSetValue(args);
				break;
			case "findNewSlaves":
				onFindNewSlaves(args);
				break;
			case "periodDelete":
				onPeriodDelete(args, ack);
				break;
			case "periodSaveOrUpdate":
				onPeriodSaveOrUpdate(args, ack);
				break;
			case "periodDetailDelete":
				onPeriodDetailDelete(args, ack);
				break;
			case "periodDetailSaveOrUpdate":
				onPeriodDetailPeriodSaveOrUpdate(args, ack);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void onPeriodDetailPeriodSaveOrUpdate(Object[] args, IOAcknowledge ack) {
		try {
			JsonParser parser = new JsonParser();
			JsonObject obj = (JsonObject) parser.parse((String) args[0]);

			JsonElement id = obj.get("id");
			JsonElement idPeriod = obj.get("idPeriod");
			JsonElement initHour = obj.get("initHour");
			JsonElement day = obj.get("day");
			JsonElement intensity = obj.get("intensity");

			@SuppressWarnings("resource")
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			PeriodScheduleService periodScheduleService = (PeriodScheduleService) context.getBean("periodScheduleService");
			PeriodScheduleDetailService periodScheduleDetailService = (PeriodScheduleDetailService)context.getBean("periodScheduleDetailService");
			Periodschedule periodSchedule = periodScheduleService.getById(idPeriod.getAsLong());

			Integer xIntensity = new Integer(intensity.getAsInt());
			xIntensity = ((xIntensity.compareTo(new Integer(0)) >= 0) && (xIntensity.compareTo(new Integer(4)) < 0))
					? new Integer(1) : xIntensity;

			Perioddetailschedule periodDetailScheduleDto = null;
			if (id == null) {// INSERT
				periodDetailScheduleDto = new Perioddetailschedule();
				periodDetailScheduleDto.setIdPeriod(new BigInteger(periodSchedule.getId()));
				periodDetailScheduleDto.setInitHour(new BigInteger(new Long(initHour.getAsLong()).toString()));
				periodDetailScheduleDto.setDay(day.getAsInt());
				periodDetailScheduleDto.setIntensity(xIntensity);
				periodScheduleDetailService.insertarPeriodoDetalle(periodDetailScheduleDto);
			} else {// UPDATE
				periodDetailScheduleDto = periodScheduleDetailService.obtenerPorId(new Long(id.getAsLong()));
				periodDetailScheduleDto.setIdPeriod(new BigInteger(periodSchedule.getId()));
				periodDetailScheduleDto.setInitHour(new BigInteger(new Long(initHour.getAsLong()).toString()));
				periodDetailScheduleDto.setDay(day.getAsInt());
				periodDetailScheduleDto.setIntensity(xIntensity);
				periodScheduleDetailService.actualizar(periodDetailScheduleDto);
			}

			// ELIMINAR PERIODO
			if (ack != null) {
				Gson gson = new Gson();
				ack.ack(gson.toJson(periodDetailScheduleDto));
			}
		} catch (Exception e) {
			// Avisar
		}
	}

	private void onPeriodDetailDelete(Object[] args, IOAcknowledge ack) {
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		PeriodScheduleDetailService periodScheduleDetailService = (PeriodScheduleDetailService)context.getBean("periodScheduleDetailService");
		try {
			Integer periodDetailId = (Integer) args[0];

			periodScheduleDetailService.eliminar(periodScheduleDetailService.obtenerPorId(new Long(periodDetailId)));

			if (ack != null) {
				Gson gson = new Gson();
				ack.ack(gson.toJson(periodDetailId));
			}
		} catch (Exception e) {

		}
	}

	private void onPeriodSaveOrUpdate(Object[] args, IOAcknowledge ack) {
		try {
			JsonParser parser = new JsonParser();
			JsonObject obj = (JsonObject) parser.parse((String) args[0]);

			JsonElement id = obj.get("id");
			JsonElement initDate = obj.get("initDate");
			JsonElement description = obj.get("description");

			@SuppressWarnings("resource")
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			PeriodScheduleService periodScheduleService = (PeriodScheduleService)context.getBean("periodScheduleService");
			Periodschedule periodSchedule = null;
			if (id == null) {// INSERT
				periodSchedule = new Periodschedule();
				periodSchedule.setInitDate(new BigInteger(new Long(initDate.getAsLong()).toString()));
				periodSchedule.setDescription(description.getAsString());
				periodScheduleService.insertarPeriodo(periodSchedule);
			} else {// UPDATE
				periodSchedule = periodScheduleService.getById(new Long(id.getAsLong()));
				periodSchedule.setInitDate(new BigInteger(new Long(initDate.getAsLong()).toString()));
				periodSchedule.setDescription(description.getAsString());
				periodScheduleService.modificarPeriodo(periodSchedule);
			}

			if (ack != null) {
				Gson gson = new Gson();
				ack.ack(gson.toJson(periodSchedule));
			}
		} catch (Exception e) {
			// Avisar
		}
	}

	private void onPeriodDelete(Object[] args, IOAcknowledge ack) {
		try {
			JsonParser parser = new JsonParser();
			JsonObject obj = (JsonObject) parser.parse((String) args[0]);

			JsonElement periodId = obj.get("periodId");

			@SuppressWarnings("resource")
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			PeriodScheduleService periodScheduleService = (PeriodScheduleService)context.getBean("periodScheduleService");
			
			periodScheduleService.eliminarPeriodo(periodScheduleService.getById(periodId.getAsLong()));

			// ELIMINAR PERIODO
			if (ack != null) {
				ack.ack();
			}
		} catch (Exception e) {

		}
	}

	public void onFindNewSlaves(Object... args) throws Exception {
		// Envio el paquete para amigar los diferentes esclavos que esten
		// disponibles para unirse
		PaqueteDto paqueteDto = new PaqueteDto(new Byte((byte) 0xFF), new Byte((byte) 0xFF), new Byte((byte) 0x05),
				new Byte[] { new Byte((byte) 0x01), new Byte((byte) 0x00), new Byte((byte) 0xF0), new Byte((byte) 0x00),
						new Byte((byte) 0x0E) });
		getRfInterface().getConnection().send(paqueteDto.getMessage());

		// Envio un broadcasting para reconocer las nuevas luminarias
		paqueteDto = new PaqueteDto(new Byte((byte) 0xFF), new Byte((byte) 0xFF), new Byte((byte) 0x05),
				new Byte[] { new Byte((byte) 0x01), new Byte((byte) 0x00), new Byte((byte) 0xF0), new Byte((byte) 0x00),
						new Byte((byte) 0x0E) });
	}

	public void onSetValue(Object... args) throws Exception {
		JsonParser parser = new JsonParser();
		JsonObject obj = (JsonObject) parser.parse((String) args[0]);

		JsonElement hightByte = obj.get("hightByte");
		JsonElement lowByte = obj.get("lowByte");
		JsonElement intensity = obj.get("intensity");

		getRfInterface().getConnection()
				.send(new PaqueteDto(new Byte(hightByte.getAsByte()), new Byte(lowByte.getAsByte()),
						new Byte((byte) 0x02), new Byte[] { new Byte((byte) 0xFE), new Byte(intensity.getAsByte()) })
								.getMessage());
		Thread.sleep(250);
		getRfInterface().getConnection()
				.send(new PaqueteDto(new Byte(hightByte.getAsByte()), new Byte(lowByte.getAsByte()),
						new Byte((byte) 0x02), new Byte[] { new Byte((byte) 0x00), new Byte((byte) 0x00) })
								.getMessage());
	}

	@Override
	public void onConnect() {

	}

	@Override
	public void onDisconnect() {

	}

	@Override
	public void onError(SocketIOException socketIOException) {
		socketIOException.printStackTrace();
	}

	@Override
	public void onMessage(String data, IOAcknowledge ack) {

	}

	@Override
	public void onMessage(JSONObject json, IOAcknowledge ack) {
		try {
			// Recibo un paquete desde NodeJs y lo en vio a la/s luminarias
			getRfInterface().getConnection().send(armarPaquete(json).getMessage());
		} catch (JSONException e) {
			// TODO: Informar error a nodeJs
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: Informar error a nodeJs
			e.printStackTrace();
		}
	}

	public SocketIO getSocketIO() {
		return socketIO;
	}

	public void setSocketIO(SocketIO socketIO) {
		this.socketIO = socketIO;
	}

	public RfInterface getRfInterface() {
		return rfInterface;
	}

	public void setRfInterface(RfInterface rfInterface) {
		this.rfInterface = rfInterface;
	}

	private PaqueteDto armarPaquete(JSONObject json) throws JSONException {
		PaqueteDto paqueteDto = new PaqueteDto();
		paqueteDto.setHigh(new Byte((String) json.get("hightByte")));
		paqueteDto.setLow(new Byte((String) json.get("lowByte")));
		paqueteDto.setLength(new Byte((byte) 5));
		Byte[] data = new Byte[5];
		data[1] = new Byte((String) json.get("idCountry"));
		data[2] = new Byte((String) json.get("idState"));
		data[3] = new Byte((String) json.get("idCity"));
		data[4] = new Byte((String) json.get("idCityGroup"));
		data[5] = new Byte((String) json.get("idCitySubGroup"));
		paqueteDto.setData(data);
		return paqueteDto;
	}
}
