package com.telnetar.desktop.services;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.configuration.ConfigurationException;

import com.telnetar.desktop.model.Perioddetailschedule;
import com.telnetar.desktop.model.Periodschedule;
import com.telnetar.desktop.repositories.PeriodScheduleDetailRepository;

public class PeriodScheduleDetailService extends SuperService{
	private PeriodScheduleDetailRepository periodScheduleDetailRepository;

	public Perioddetailschedule obtenerPorId(Long id) throws ConfigurationException{
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		try{
			return obtenerPorId(id, entityManager);
		}finally{
			entityManager.close();
		}
	}
	public Perioddetailschedule obtenerPorId(Long id, EntityManager em){
		return getPeriodScheduleDetailRepository().obtenerPorId(id, em);
	}
	public List<Perioddetailschedule> obtenerDetallePeriodo(Periodschedule periodschedule, int day) throws ConfigurationException{
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		try{
			return obtenerDetallePeriodo(periodschedule, day, entityManager);
		}finally{
			entityManager.close();
		}
    }
	public List<Perioddetailschedule> obtenerDetallePeriodo(Periodschedule periodschedule, int day, EntityManager em){
		return getPeriodScheduleDetailRepository().obtenerDetallePeriodo(periodschedule, day, em); 
	}
	
	public void insertarPeriodoDetalle(Perioddetailschedule perioddetailschedule) throws ConfigurationException{
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		try{
			entityManager.getTransaction().begin();
			insertarPeriodoDetalle(perioddetailschedule, entityManager);
			entityManager.getTransaction().commit();
		}catch(Exception e){
			entityManager.getTransaction().rollback();
			throw e;
		}finally{
			entityManager.close();
		}
	}
	public void insertarPeriodoDetalle(Perioddetailschedule perioddetailschedule, EntityManager em){
		getPeriodScheduleDetailRepository().insertarPeriodo(perioddetailschedule, em);
	}
	public void eliminar(Perioddetailschedule perioddetailschedule) throws ConfigurationException {
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		try{
			entityManager.getTransaction().begin();
			eliminar(perioddetailschedule, entityManager);
			entityManager.getTransaction().commit();
		}catch(Exception e){
			entityManager.getTransaction().rollback();
			throw e;
		}finally{
			entityManager.close();
		}
	}
	public void eliminar(Perioddetailschedule perioddetailschedule, EntityManager em){
		getPeriodScheduleDetailRepository().eliminar(perioddetailschedule, em);
	}
	public void actualizar(Perioddetailschedule periodDetailScheduleDto) throws ConfigurationException {
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		try{
			entityManager.getTransaction().begin();
			actualizar(periodDetailScheduleDto, entityManager);
			entityManager.getTransaction().commit();
		}catch(Exception e){
			entityManager.getTransaction().rollback();
			throw e;
		}finally{
			entityManager.close();
		}
	}
	public void actualizar(Perioddetailschedule perioddetailschedule, EntityManager em){
		getPeriodScheduleDetailRepository().actualizar(perioddetailschedule, em);
	}
	public PeriodScheduleDetailRepository getPeriodScheduleDetailRepository() {
		return periodScheduleDetailRepository;
	}

	public void setPeriodScheduleDetailRepository(PeriodScheduleDetailRepository periodScheduleDetailRepository) {
		this.periodScheduleDetailRepository = periodScheduleDetailRepository;
	}
	
	
}