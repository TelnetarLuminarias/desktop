package com.telnetar.desktop.repositories;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class DbRepository {

	public void crearEsquema(String dbname, EntityManager em) {
		Query query = em
			.createNativeQuery("CREATE DATABASE " + dbname);
		query.executeUpdate();
	}

	public void crearTablaConfigurations(String dbname, EntityManager em) {
		Query query = null;
		String sql = "DROP TABLE IF EXISTS " + dbname + ".configurations";
		query = em.createNativeQuery(sql);
		query.executeUpdate();
		
		sql = "CREATE TABLE " + dbname + ".configurations ( " +
				"configuration_id bigint(20) unsigned NOT NULL AUTO_INCREMENT, " +
				"frecuency int(11) NOT NULL DEFAULT '5', " +
				"nodejsserver varchar(400) DEFAULT NULL, " +
				"fromdate timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
				"nodejsport int(11) DEFAULT NULL, " +
				"nodejstype varchar(20) DEFAULT NULL, " +
				"lighton timestamp NULL DEFAULT NULL, " +
				"lightoff timestamp NULL DEFAULT NULL, " +
				"delay int(11) DEFAULT NULL, " +
				"intensityMin int(11) DEFAULT NULL, " +
				"intensityMax int(11) DEFAULT NULL, " +
				"puertoComMaster varchar(20) DEFAULT 'COM3', " +
				"nodename varchar(50) DEFAULT NULL, " +
				"puertoComL0 varchar(20) DEFAULT NULL, " +
				"l0integrado int(11) NOT NULL DEFAULT '1', " +
				"PRIMARY KEY (configuration_id) " +
				") ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1";
		query = em.createNativeQuery(sql);
		query.executeUpdate();
	}

	public void insetarValoresIniciales(String dbname, int frecuencia, int l0Integrado, String nodename, EntityManager em) {
		Query query = null;
		String sql = "INSERT INTO " + dbname + ".configurations (frecuency, l0integrado, nodename, nodejstype, nodejsport, fromdate, nodejsserver) VALUES (?,?,?,?,?,?,?)";
		query = em.createNativeQuery(sql)
				.setParameter(1, frecuencia)
				.setParameter(2, l0Integrado)
				.setParameter(3, nodename)
				.setParameter(4, "Desktop")
				.setParameter(5, 2048)
				.setParameter(6, new Date())
				.setParameter(7, "http://www.lumiadmin.com.ar");
		
		query.executeUpdate();
	}

	public void crearTablaLuminarias(String dbname, EntityManager em) {
		Query query = null;
		String sql = "DROP TABLE IF EXISTS " + dbname + ".luminarias";
		query = em.createNativeQuery(sql);
		query.executeUpdate();
		
		sql = "CREATE TABLE " + dbname + ".luminarias ( " +
				"hightbyte int(3) NOT NULL, " +
				"lowbyte int(3) NOT NULL, " +
				"description varchar(256) DEFAULT NULL, " +
				"isNew tinyint(1) DEFAULT NULL, " +
				"idGrupoLuminaria bigint(20) unsigned DEFAULT NULL, " +
				"PRIMARY KEY (hightbyte,lowbyte), " +
				"UNIQUE KEY description_UNIQUE (description) " +
				") ENGINE=InnoDB DEFAULT CHARSET=latin1 ";
		query = em.createNativeQuery(sql);
		query.executeUpdate();
	}

	public void crearTablaLuminariasHist(String dbname, EntityManager em) {
		Query query = null;
		String sql = "DROP TABLE IF EXISTS " + dbname + ".luminariahist";
		query = em.createNativeQuery(sql);
		query.executeUpdate();
		
		sql = "CREATE TABLE " + dbname + ".luminariahist ( " +
				"hightbyte int(3) NOT NULL, " +
				"lowbyte int(3) NOT NULL, " +
				"fecha timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
				"intensity int(3) DEFAULT NULL, " +
				"temperatureLow int(3) DEFAULT NULL, " +
				"temperatureHight int(3) DEFAULT NULL, " +
				"sincronized timestamp NULL DEFAULT NULL, " +
				"lumiContextH int(11) DEFAULT NULL, " +
				"lumiContextL int(11) DEFAULT NULL, " +
				"PRIMARY KEY (hightbyte,lowbyte,fecha) " +
				") ENGINE=InnoDB DEFAULT CHARSET=latin1 ";
		query = em.createNativeQuery(sql);
		query.executeUpdate();
	}

	public void crearTablaGruposLuminarias(String dbname, EntityManager em) {
		Query query = null;
		String sql = "DROP TABLE IF EXISTS " + dbname + ".gruposluminarias";
		query = em.createNativeQuery(sql);
		query.executeUpdate();
		
		sql = "CREATE TABLE " + dbname + ".gruposluminarias ( " +
				"idGrupoLuminaria bigint(20) unsigned NOT NULL AUTO_INCREMENT, " +
				"descripcion varchar(100) NOT NULL, " +
				"PRIMARY KEY (idGrupoLuminaria) " +
				") ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 " ;
		query = em.createNativeQuery(sql);
		query.executeUpdate();
	}

	public void crearTablaL0Hist(String dbname, EntityManager em) {
		Query query = null;
		String sql = "DROP TABLE IF EXISTS " + dbname + ".l0hist";
		query = em.createNativeQuery(sql);
		query.executeUpdate();
		
		sql = "CREATE TABLE " + dbname + ".l0hist ( " +
				"fecha timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
				"intensity int(3) DEFAULT NULL, " +
				"PRIMARY KEY (fecha) " +
				") ENGINE=InnoDB DEFAULT CHARSET=latin1";
		query = em.createNativeQuery(sql);
		query.executeUpdate();
	}

	public void crearTablaRoomTemperatureHist(String dbname, EntityManager em) {
		Query query = null;
		String sql = "DROP TABLE IF EXISTS " + dbname + ".roomtemperaturehist";
		query = em.createNativeQuery(sql);
		query.executeUpdate();
		
		sql = "CREATE TABLE " + dbname + ".roomtemperaturehist ( " +
				"hightbyte int(3) unsigned NOT NULL, " +
				"lowbyte int(3) unsigned NOT NULL, " +
				"fecha timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, " +
				"temphight int(3) NOT NULL, " +
				"templow int(3) NOT NULL, " +
				"sincronized timestamp NULL DEFAULT NULL, " +
				"PRIMARY KEY (hightbyte,lowbyte,fecha) " +
				") ENGINE=InnoDB DEFAULT CHARSET=latin1 ";
		query = em.createNativeQuery(sql);
		query.executeUpdate();
	}

	public void crearTablaPeriodoSchedule(String dbname, EntityManager em) {
		Query query = null;
		String sql = "DROP TABLE IF EXISTS " + dbname + ".periodschedule";
		query = em.createNativeQuery(sql);
		query.executeUpdate();
		
		sql = "CREATE TABLE " + dbname + ".periodschedule ( " +
				"id bigint(20) unsigned NOT NULL AUTO_INCREMENT, " +
				"initDate bigint(20) NOT NULL, " +
				"description varchar(100) DEFAULT NULL, " +
				"idGrupoLuminaria bigint(20) unsigned DEFAULT NULL, " +
				"PRIMARY KEY (id) " +
				") ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1";
		query = em.createNativeQuery(sql);
		query.executeUpdate();
	}

	public void crearTablaPeriodDetailSchedule(String dbname, EntityManager em) {
		Query query = null;
		String sql = "DROP TABLE IF EXISTS " + dbname + ".perioddetailschedule";
		query = em.createNativeQuery(sql);
		query.executeUpdate();
		
		sql = "CREATE TABLE " + dbname + ".perioddetailschedule ( " +
				"id bigint(20) unsigned NOT NULL AUTO_INCREMENT, " +
				"idPeriod bigint(20) unsigned NOT NULL, " +
				"initHour bigint(20) NOT NULL, " +
				"day int(11) NOT NULL, " +
				"intensity int(11) NOT NULL, " +
				"PRIMARY KEY (id), " +
				"KEY FK_PERIODSCHEDULE_01_idx (idPeriod), " +
				"CONSTRAINT FK_PERIODDETAILSCHEDULE_01 FOREIGN KEY (idPeriod) REFERENCES " + dbname +".periodschedule (id) ON DELETE CASCADE ON UPDATE NO ACTION " + 
				") ENGINE=InnoDB AUTO_INCREMENT=158 DEFAULT CHARSET=latin1 ";
		query = em.createNativeQuery(sql);
		query.executeUpdate();
	}

}
