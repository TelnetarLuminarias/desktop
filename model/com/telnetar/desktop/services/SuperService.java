package com.telnetar.desktop.services;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.telnetar.desktop.repositories.DbRepository;

public class SuperService {
	protected static EntityManagerFactory getEntityManagerFactory() throws ConfigurationException {
		Configuration config = new PropertiesConfiguration("db.properties");
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("javax.persistence.jdbc.user", config.getString("user"));
		properties.put("javax.persistence.jdbc.password", config.getString("password"));
		properties.put("javax.persistence.jdbc.driver", config.getString("driver"));
		properties.put("javax.persistence.jdbc.url", "jdbc:mysql://" + config.getString("host") + ":3306/" + config.getString("dbname"));
		EntityManagerFactory emf = 
			Persistence.createEntityManagerFactory(
				"LuminariaEscritorioLab",
				properties);
		return emf;
	}
	
	public static void validarDatosConeccion(String user, String password, String host, String dbname) throws ConfigurationException, PersistenceException {
		Configuration config = new PropertiesConfiguration("db.properties");;
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("javax.persistence.jdbc.user", user);
		properties.put("javax.persistence.jdbc.password", password);
		properties.put("javax.persistence.jdbc.driver", config.getString("driver"));
		properties.put("javax.persistence.jdbc.url", "jdbc:mysql://" + host + ":3306/" + dbname);
		EntityManagerFactory emf = 
			Persistence.createEntityManagerFactory(
				"LuminariaEscritorioLab",
				properties);
		emf.createEntityManager();
	}
	
	public static void crearEsquema(int frecuencia, int l0Integrado, String nodename) throws ConfigurationException{
		Configuration config = new PropertiesConfiguration("db.properties");;
		String user = config.getString("user");
		String password = config.getString("password");
		String driver = config.getString("driver");
		String host = config.getString("host");
		String dbname = config.getString("dbname");
		crearEsquema(user, password, driver, host, dbname, frecuencia, l0Integrado, nodename);
	}

	private static void crearEsquema(String user, String password, String driver, String host, String dbname, int frecuencia, int l0Integrado, String nodename) throws ConfigurationException {
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("javax.persistence.jdbc.user", user);
		properties.put("javax.persistence.jdbc.password", password);
		properties.put("javax.persistence.jdbc.driver", driver);
		properties.put("javax.persistence.jdbc.url", "jdbc:mysql://" + host + ":3306/");
		EntityManagerFactory emf = 
			Persistence.createEntityManagerFactory(
				"LuminariaEscritorioLab",
				properties);
		EntityManager em = emf.createEntityManager();
		try{
			em.getTransaction().begin();
			DbRepository dbRepository = new DbRepository();
			dbRepository.crearEsquema(dbname, em);
			dbRepository.crearTablaConfigurations(dbname, em);
			dbRepository.insetarValoresIniciales(dbname, frecuencia, l0Integrado, nodename, em);
			dbRepository.crearTablaLuminarias(dbname, em);
			dbRepository.crearTablaLuminariasHist(dbname, em);
			dbRepository.crearTablaGruposLuminarias(dbname, em);
			dbRepository.crearTablaL0Hist(dbname, em);
			dbRepository.crearTablaRoomTemperatureHist(dbname,em);
			dbRepository.crearTablaPeriodoSchedule(dbname, em);
			dbRepository.crearTablaPeriodDetailSchedule(dbname, em);
			em.getTransaction().commit();
		}catch(Exception e){
			em.getTransaction().rollback();
			throw e;
		}finally{
			em.close();
		}
	}
}
