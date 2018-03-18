package com.telnetar.desktop.services;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.configuration.ConfigurationException;

import com.telnetar.desktop.model.Luminariahist;
import com.telnetar.desktop.repositories.LuminariahistRepository;

public class LuminariahistService extends SuperService{
    private LuminariahistRepository luminariahistRepository;
    
    public List<Luminariahist> obtenerUltimosRegistrosPorLuminarias() throws ConfigurationException{
    	EntityManager entityManager = getEntityManagerFactory().createEntityManager();
    	try{
    		List<Luminariahist> lsResult = obtenerUltimosRegistrosPorLuminarias(entityManager);
    		return lsResult;
    	}finally{
    		entityManager.close();
    	}
    }
    public List<Luminariahist> obtenerUltimosRegistrosPorLuminarias(EntityManager em){
    	return getLuminariahistRepository().obtenerUltimosRegistrosPorLuminarias(em);
    }
    
    public void insertar(Luminariahist luminariahist) throws ConfigurationException{
    	EntityManager entityManager = getEntityManagerFactory().createEntityManager();
    	try{
    		entityManager.getTransaction().begin();
    		insertar(luminariahist, entityManager);
    		entityManager.getTransaction().commit();
    	}catch(Exception e){
    		entityManager.getTransaction().rollback();
    		throw e;
    	}finally{
    		entityManager.close();
    	}
    }
    public void insertar(Luminariahist luminariahist, EntityManager em){
    	getLuminariahistRepository().insertar(luminariahist, em);
    }

    public LuminariahistRepository getLuminariahistRepository() {
        return luminariahistRepository;
    }

    public void setLuminariahistRepository(
    	LuminariahistRepository luminariahistRepository) {
        this.luminariahistRepository = luminariahistRepository;
    }
}
