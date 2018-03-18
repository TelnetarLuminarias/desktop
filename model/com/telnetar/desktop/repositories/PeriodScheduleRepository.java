package com.telnetar.desktop.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.telnetar.desktop.model.Periodschedule;

public class PeriodScheduleRepository {
	@SuppressWarnings("unchecked")
	public List<Periodschedule> obtenerPeriodosVigentes(EntityManager em){
		Query query = em
				.createNativeQuery(
						"SELECT a.* " + 
						"FROM periodschedule a " +
						"WHERE a.id NOT IN( " +
							"SELECT id " + 
							"FROM periodschedule a " +
							"WHERE a.id IN ( " +
								"SELECT b.id " +
								"FROM periodschedule b " +
								"WHERE IFNULL(b.idGrupoLuminaria,0) = IFNULL(a.idGrupoLuminaria,0) " +
									"AND b.initDate NOT IN ( " +
										"SELECT MAX(c.initDate) " + 
										"FROM periodschedule c " +
										"WHERE IFNULL(c.idGrupoLuminaria,0) = IFNULL(b.idGrupoLuminaria,0) " +
											"AND UNIX_TIMESTAMP() > c.initDate/1000))) "
								, Periodschedule.class);
		List<Periodschedule> lsData = query.getResultList();
		return lsData;
	}

	public void insertarPeriodo(Periodschedule periodSchedule, EntityManager em) {
		Query query = 
			em.createNativeQuery("insert into periodschedule(initDate, description, idGrupoLuminaria) values(?,?,?)")
			.setParameter(1, periodSchedule.getInitDate())
			.setParameter(2, periodSchedule.getDescription().trim())
			.setParameter(3, periodSchedule.getIdGrupoLuminaria());
		query.executeUpdate();
	}

	public void modificarPeriodo(Periodschedule periodschedule, EntityManager em) {
		Query query = 
			em.createNativeQuery("update periodschedule set initDate = ?, description = ?, idGrupoLuminaria = ? where id = ?")
			.setParameter(1, periodschedule.getInitDate())
			.setParameter(2, periodschedule.getDescription().trim())
			.setParameter(3, periodschedule.getIdGrupoLuminaria())
			.setParameter(4, periodschedule.getId());
		query.executeUpdate();
	}

	public void eliminarPeriodo(Periodschedule periodschedule, EntityManager em) {
		Query query = 
				em.createNativeQuery("delete from periodschedule where id = ?")
				.setParameter(1, periodschedule.getId());
			query.executeUpdate();
	}

	public Periodschedule getById(Long id, EntityManager em) {
		Query query = 
				em.createNativeQuery("select * from periodschedule where id = ?")
				.setParameter(1, id);
		Periodschedule periodschedule =	(Periodschedule) query.getSingleResult();
		return periodschedule;
	}
}