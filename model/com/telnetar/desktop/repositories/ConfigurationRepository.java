package com.telnetar.desktop.repositories;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.telnetar.desktop.model.Configuration;

public class ConfigurationRepository {

	public Configuration obtenerUltimaConfiguracion(EntityManager em) {
		Query query = em
				.createNativeQuery(
						"SELECT * FROM configurations " +
								"where configuration_id = ( " +
								"select max(configuration_id) from configurations " +
						") ",
						Configuration.class);
		Configuration configuration = (Configuration) query.getSingleResult();
		return configuration;
	}

	public void guardarCambios(Configuration configuration, EntityManager em) {
		em
				.createNativeQuery(
						"UPDATE CONFIGURATIONS SET " +
								"puertoComL0 = ?, " +
								"puertoComMaster = ?, " +
								"fromdate = current_timestamp(), " +
								"l0integrado = ? " +
								"where configuration_id = ?")
				.setParameter(1, configuration.getPuertoComL0())
				.setParameter(2, configuration.getPuertoComMaster())
				.setParameter(3, configuration.getL0integrado())
				.setParameter(4, configuration.getConfigurationId())
				.executeUpdate();
	}
}
