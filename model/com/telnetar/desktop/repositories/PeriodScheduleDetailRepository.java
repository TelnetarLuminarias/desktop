package com.telnetar.desktop.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.telnetar.desktop.model.Perioddetailschedule;
import com.telnetar.desktop.model.Periodschedule;

public class PeriodScheduleDetailRepository {
	@SuppressWarnings("unchecked")
	public List<Perioddetailschedule> obtenerDetallePeriodo(Periodschedule periodschedule, int day, EntityManager em){
		Query query = em
				.createNativeQuery(
						"select * from perioddetailschedule " +
						"where idPeriod = ? " + 
						"and day = ? " +
						"order by day, initHour"
								, Perioddetailschedule.class)
				.setParameter(1, periodschedule.getId())
				.setParameter(2, day);
		List<Perioddetailschedule> lsData = 
			query.setParameter(1, periodschedule.getId()).setParameter(2, day).getResultList();
		return lsData;
	}

	public void insertarPeriodo(Perioddetailschedule perioddetailschedule, EntityManager em) {
		Query query = em
			.createNativeQuery("insert into perioddetailschedule(idPeriod, initHour, day, intensity) values(?,?,?,?) ")
			.setParameter(1, perioddetailschedule.getIdPeriod())
			.setParameter(2, perioddetailschedule.getInitHour())
			.setParameter(3, perioddetailschedule.getDay())
			.setParameter(4, perioddetailschedule.getIntensity().equals(new Integer(0)) ? new Integer(1) : perioddetailschedule.getIntensity());
		query.executeUpdate();
	}

	public void eliminar(Perioddetailschedule perioddetailschedule, EntityManager em) {
		Query query = em
				.createNativeQuery("delete from perioddetailschedule where id = ?")
				.setParameter(1, perioddetailschedule.getId());
			query.executeUpdate();
	}

	public Perioddetailschedule obtenerPorId(Long id, EntityManager em) {
		Query query = em
				.createNativeQuery("select * from perioddetailschedule where id = ?")
				.setParameter(1, id);
		Perioddetailschedule perioddetailschedule = (Perioddetailschedule) query.getSingleResult();
		return perioddetailschedule;
	}

	public void actualizar(Perioddetailschedule perioddetailschedule, EntityManager em) {
		Query query = em
				.createNativeQuery("update perioddetailschedule set initHour = ?, day = ?, intensity = ? where id = ?")
				.setParameter(1, perioddetailschedule.getInitHour())
				.setParameter(2, perioddetailschedule.getDay())
				.setParameter(3, perioddetailschedule.getIntensity())
				.setParameter(4, perioddetailschedule.getId());
		query.executeUpdate();
	}
}
