package com.telnetar.desktop.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.telnetar.desktop.model.Luminariahist;

public class LuminariahistRepository {

	@SuppressWarnings("unchecked")
	public List<Luminariahist> obtenerUltimosRegistrosPorLuminarias(EntityManager em) {
 		Query query = em
				.createNativeQuery(
						"SELECT b.* FROM luminarias a, luminariahist b "
								+ "where a.hightbyte = b.hightbyte " + "and a.lowbyte = b.lowbyte " + "and b.fecha = ( "
								+ "select max(c.fecha) from luminariahist c "
								+ "where c.hightbyte = a.hightbyte " + "and c.lowbyte = a.lowbyte) ",
						Luminariahist.class);
		List<Luminariahist> lsData = query.getResultList();
		return lsData;
	}

	public void insertar(Luminariahist luminariahist, EntityManager em) {
		Query query = em
			.createNativeQuery(
				"insert into luminariahist(hightbyte, lowbyte, fecha, intensity, temperatureLow, temperatureHight, sincronized, lumiContextH, lumiContextL) values(?,?,?,?,?,?,?,?,?) ")
			.setParameter(1, luminariahist.getId().getHightbyte())
			.setParameter(2, luminariahist.getId().getLowbyte())
			.setParameter(3, luminariahist.getId().getFecha())
			.setParameter(4, luminariahist.getIntensity())
			.setParameter(5, luminariahist.getTemperatureLow())
			.setParameter(6, luminariahist.getTemperatureHight())
			.setParameter(7, null)
			.setParameter(8, luminariahist.getLumiContextH())
			.setParameter(9, luminariahist.getLumiContextL());
		query.executeUpdate();
	}

}
