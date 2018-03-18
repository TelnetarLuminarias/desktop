package com.telnetar.desktop.services;

import javax.persistence.EntityManager;

import org.apache.commons.configuration.ConfigurationException;

import com.telnetar.desktop.model.L0hist;
import com.telnetar.desktop.repositories.L0HistRepository;

public class L0HistService extends SuperService{
	private L0HistRepository l0HistRepository;

	public L0HistRepository getL0HistRepository() {
		return l0HistRepository;
	}

	public void setL0HistRepository(L0HistRepository l0HistRepository) {
		this.l0HistRepository = l0HistRepository;
	}

	public void insertar(L0hist l0hist) throws ConfigurationException {
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		try{
			entityManager.getTransaction().begin();
			insertar(l0hist, entityManager);
			entityManager.getTransaction().commit();
		}catch(Exception e){
			entityManager.getTransaction().rollback();
			throw e;
		}finally{
			entityManager.close();
		}
	}
	public void insertar(L0hist l0hist, EntityManager em){
		getL0HistRepository().insertar(l0hist, em);
	}
	
	public L0hist obtenerUltimoRegistro() throws ConfigurationException{
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		try{
			return obtenerUltimoRegistro(entityManager);
		}finally{
			entityManager.close();
		}
	}
	public L0hist obtenerUltimoRegistro(EntityManager em){
		return getL0HistRepository().obtenerUltimoRegistro(em);
	}
}
