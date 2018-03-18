package com.telnetar.desktop.services;

import javax.persistence.EntityManager;

import org.apache.commons.configuration.ConfigurationException;

import com.telnetar.desktop.model.Configuration;
import com.telnetar.desktop.repositories.ConfigurationRepository;

public class ConfigurationService extends SuperService{
	private ConfigurationRepository configurationRepository;

	public Configuration obtenerUltimaConfiguracion() throws ConfigurationException{
		EntityManager entityManager = getEntityManagerFactory().createEntityManager(); 
		try{
			Configuration configuration = obtenerUltimaConfiguracion(entityManager);
			return configuration;
		}finally{
			entityManager.close();
		}
	}
	public Configuration obtenerUltimaConfiguracion(EntityManager em){
		return getConfigurationRepository().obtenerUltimaConfiguracion(em);
	}
	
	public void guardarCambios(Configuration configuration) throws Exception{
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		try{
			entityManager.getTransaction().begin();
			guardarCambios(configuration, entityManager);
			entityManager.getTransaction().commit();
		}catch(Exception e){
			entityManager.getTransaction().rollback();
			throw e;
		}finally{
			entityManager.close();
		}
	}
	
	public void guardarCambios(Configuration configuration, EntityManager em) throws Exception{
		getConfigurationRepository().guardarCambios(configuration, em);
	}
	
	public ConfigurationRepository getConfigurationRepository() {
		return configurationRepository;
	}

	public void setConfigurationRepository(ConfigurationRepository configurationRepository) {
		this.configurationRepository = configurationRepository;
	}
}