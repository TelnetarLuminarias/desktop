package com.telnetar.desktop.repositories;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.telnetar.desktop.model.Roomtemperaturehist;

public class RoomtemperaturehistRepository {

	public void insertar(Roomtemperaturehist roomtemperaturehist, EntityManager em) {
		Query query = 
			em.createNativeQuery("insert into roomtemperaturehist(hightbyte, lowbyte, fecha, temphight, templow, sincronized) values(?,?,?,?,?,?)")
			.setParameter(1, roomtemperaturehist.getId().getHightbyte())
			.setParameter(2, roomtemperaturehist.getId().getLowbyte())
			.setParameter(3, roomtemperaturehist.getId().getFecha())
			.setParameter(4, roomtemperaturehist.getTemphight())
			.setParameter(5, roomtemperaturehist.getTemplow())
			.setParameter(6, roomtemperaturehist.getSincronized());
		query.executeUpdate();		
	}

}
