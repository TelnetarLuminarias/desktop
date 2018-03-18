package com.telnetar.desktop.services;

import javax.persistence.EntityManager;

import org.apache.commons.configuration.ConfigurationException;

import com.telnetar.desktop.model.Roomtemperaturehist;
import com.telnetar.desktop.repositories.RoomtemperaturehistRepository;

public class RoomtemperaturehistService extends SuperService{
	private RoomtemperaturehistRepository roomtemperaturehistRepository;

	public RoomtemperaturehistRepository getRoomtemperaturehistRepository() {
		return roomtemperaturehistRepository;
	}

	public void setRoomtemperaturehistRepository(RoomtemperaturehistRepository roomtemperaturehistRepository) {
		this.roomtemperaturehistRepository = roomtemperaturehistRepository;
	}

	public void insertar(Roomtemperaturehist roomTemperatureHist) throws ConfigurationException {
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		try{
			entityManager.getTransaction().begin();
			insertar(roomTemperatureHist, entityManager);
			entityManager.getTransaction().commit();
		}catch(Exception e){
			entityManager.getTransaction().rollback();
			throw e;
		}finally{
			entityManager.close();
		}
	}
	public void insertar(Roomtemperaturehist roomtemperaturehist, EntityManager em){
		getRoomtemperaturehistRepository().insertar(roomtemperaturehist, em);
	}
}