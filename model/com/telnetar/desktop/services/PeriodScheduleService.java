package com.telnetar.desktop.services;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.configuration.ConfigurationException;

import com.telnetar.desktop.model.Periodschedule;
import com.telnetar.desktop.repositories.PeriodScheduleRepository;

public class PeriodScheduleService extends SuperService{
	private PeriodScheduleRepository periodScheduleRepository;

	public List<Periodschedule> obtenerPeriodosVigentes() throws ConfigurationException{
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		try{
			return obtenerPeriodosVigentes(entityManager);
		}finally{
			entityManager.close();
		}
    }
	public List<Periodschedule> obtenerPeriodosVigentes(EntityManager em){
		return getPeriodScheduleRepository().obtenerPeriodosVigentes(em);
	}
	
	public void insertarPeriodo(Periodschedule periodSchedule) throws ConfigurationException{
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		try{
			entityManager.getTransaction().begin();
			insertarPeriodo(periodSchedule, entityManager);
			entityManager.getTransaction().commit();
		}catch(Exception e){
			entityManager.getTransaction().rollback();
			throw e;
		}finally{
			entityManager.close();
		}
	}
	public void insertarPeriodo(Periodschedule periodSchedule, EntityManager em){
		getPeriodScheduleRepository().insertarPeriodo(periodSchedule, em);
	}
	public void modificarPeriodo(Periodschedule periodschedule) throws ConfigurationException {
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		try{
			entityManager.getTransaction().begin();
			modificarPeriodo(periodschedule, entityManager);
			entityManager.getTransaction().commit();
		}catch(Exception e){
			entityManager.getTransaction().rollback();
			throw e;
		}finally{
			entityManager.close();
		}
	}
	public void modificarPeriodo(Periodschedule periodschedule, EntityManager em){
		getPeriodScheduleRepository().modificarPeriodo(periodschedule, em);
	}
	public void eliminarPeriodo(Periodschedule periodschedule) throws ConfigurationException {
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		try{
			entityManager.getTransaction().begin();
			eliminarPeriodo(periodschedule, entityManager);
			entityManager.getTransaction().commit();
		}catch(Exception e){
			entityManager.getTransaction().rollback();
			throw e;
		}finally{
			entityManager.close();
		}
	}
	public void eliminarPeriodo(Periodschedule periodschedule, EntityManager em){
		getPeriodScheduleRepository().eliminarPeriodo(periodschedule, em);
	}
	
	public Periodschedule getById(Long id) throws ConfigurationException {
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		try{
			return getById(id, entityManager);	
		}finally{
			entityManager.close();
		}
	}
	public Periodschedule getById(Long id, EntityManager em){
		return getPeriodScheduleRepository().getById(id, em);
	}
	
	public PeriodScheduleRepository getPeriodScheduleRepository() {
		return periodScheduleRepository;
	}

	public void setPeriodScheduleRepository(PeriodScheduleRepository periodScheduleRepository) {
		this.periodScheduleRepository = periodScheduleRepository;
	}
	
	
	
}
