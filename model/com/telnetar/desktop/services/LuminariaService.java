package com.telnetar.desktop.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.commons.configuration.ConfigurationException;

import com.telnetar.desktop.model.Luminaria;
import com.telnetar.desktop.model.LuminariaPK;
import com.telnetar.desktop.repositories.LuminariaRepository;

public class LuminariaService extends SuperService{
	private LuminariaRepository luminariaRepository;

	public LuminariaRepository getLuminariaRepository() {
		return luminariaRepository;
	}

	public void setLuminariaRepository(LuminariaRepository luminariaRepository) {
		this.luminariaRepository = luminariaRepository;
	}

	public Luminaria buscarL0() throws ConfigurationException {
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
    	try{
    		Luminaria result = buscarL0(entityManager);
    		return result;
    	}finally{
    		entityManager.close();
    	}
	}
	public Luminaria buscarL0(EntityManager em){
		return getLuminariaRepository().buscarL0(em);
	}

	public List<Luminaria> obtenerLuminariasPorGrupo(Long cdGrupo) throws ConfigurationException {
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
    	try{
    		List<Luminaria> result = obtenerLuminariasPorGrupo(cdGrupo, entityManager);
    		return result;
    	}finally{
    		entityManager.close();
    	}
	}
	public List<Luminaria> obtenerLuminariasPorGrupo(Long cdGrupo, EntityManager em){
		return getLuminariaRepository().obtenerLuminariasPorGrupo(cdGrupo, em);
	}

	public void insertar(Luminaria luminaria) throws ConfigurationException {
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
    	try{
    		entityManager.getTransaction().begin();
    		insertar(luminaria, entityManager);
    		entityManager.getTransaction().commit();
    	}catch(Exception e){
    		entityManager.getTransaction().rollback();
    		throw e;
    	}finally{
    		entityManager.close();
    	}
	}
	public void insertar(Luminaria luminaria, EntityManager em){
		getLuminariaRepository().insertar(luminaria, em);
	}

	public Luminaria obtenerPorId(LuminariaPK luminariaPK) throws ConfigurationException {
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
    	try{
    		return obtenerPorId(luminariaPK, entityManager);
    	}catch (NoResultException e) {
    		return null;
    	}finally{
    		entityManager.close();
    	}
	}
	public Luminaria obtenerPorId(LuminariaPK pk, EntityManager em){
		return getLuminariaRepository().obtenerPorId(pk, em);
	}

	public List<Luminaria> obtenerLuminarias() throws ConfigurationException {
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
    	try{
    		List<Luminaria> result = obtenerLuminarias(entityManager);
    		return result;
    	}finally{
    		entityManager.close();
    	}
	}
	public List<Luminaria> obtenerLuminarias(EntityManager em){
		return getLuminariaRepository().obtnerLuminarias(em);
	}
}
