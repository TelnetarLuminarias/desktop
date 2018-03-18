package com.telnetar.desktop.repositories;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.telnetar.desktop.Util;
import com.telnetar.desktop.model.L0hist;

public class L0HistRepository {

	public void insertar(L0hist l0hist, EntityManager em) {
		Query query = em
			.createNativeQuery("insert into l0hist(fecha, intensity) values(?,?) ")
			.setParameter(1, l0hist.getFecha().getTime())
			.setParameter(2, Util.getVirtualIntensity(l0hist.getIntensity()));
		query.executeUpdate();
	}

	public L0hist obtenerUltimoRegistro(EntityManager em) {
		Query query = em.createNativeQuery("select * from l0hist order by fecha desc limit 1 ");
		return (L0hist) query.getSingleResult();
	}

}
