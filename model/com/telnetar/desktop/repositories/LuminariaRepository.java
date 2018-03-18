package com.telnetar.desktop.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.telnetar.desktop.model.Luminaria;
import com.telnetar.desktop.model.LuminariaPK;

public class LuminariaRepository {

	/**
	 * Este método solo se usa cuando el L0 está separado del nodo
	 * @param em
	 * @return
	 */
	public Luminaria buscarL0(EntityManager em) {
		Luminaria lsData = null;
		try{
			Query query = em
				.createNativeQuery(
					"select * from luminarias where description = 'l0' ", Luminaria.class);
			
			lsData = (Luminaria) query.getSingleResult();
			return lsData;
		}catch(NoResultException e){
			return null;
		}
		
	}

	@SuppressWarnings("unchecked")
	public List<Luminaria> obtenerLuminariasPorGrupo(Long cdGrupo, EntityManager em) {
		// SELECT * FROM luminarias where idGrupoLuminaria = 1
		Query query = em
			.createNativeQuery("SELECT * FROM luminarias where idGrupoLuminaria = ? ", Luminaria.class)
			.setParameter(1, cdGrupo);
		List<Luminaria> lsResult = query.getResultList();
		return lsResult;
	}

	public void insertar(Luminaria l0, EntityManager em) {
		Query query = em
			.createNativeQuery("INSERT INTO LUMINARIAS(hightbyte, lowbyte, description, isNew, idGrupoLuminaria) values(?,?,?,?,?) ")
			.setParameter(1, l0.getId().getHightbyte())
			.setParameter(2, l0.getId().getLowbyte())
			.setParameter(3, l0.getDescription())
			.setParameter(4, 1)
			.setParameter(5, l0.getIdGrupoLuminaria());
		query.executeUpdate();
	}

	public Luminaria obtenerPorId(LuminariaPK pk, EntityManager em) {
		Query query = em
			.createNativeQuery(
				"SELECT * FROM luminarias where hightbyte = ? and lowbyte = ? ", Luminaria.class)
			.setParameter(1, pk.getHightbyte())
			.setParameter(2, pk.getLowbyte());
		Luminaria luminaria = (Luminaria) query.getSingleResult();
		return luminaria;
	}

	@SuppressWarnings("unchecked")
	public List<Luminaria> obtnerLuminarias(EntityManager em) {
		Query query = em
			.createNativeQuery("SELECT * FROM luminarias ", Luminaria.class);
		List<Luminaria> lsResult = query.getResultList();
		return lsResult;
	}

}
