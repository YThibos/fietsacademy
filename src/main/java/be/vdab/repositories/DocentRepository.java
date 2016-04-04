package be.vdab.repositories;

import javax.persistence.EntityManager;

import be.vdab.entities.Docent;

public class DocentRepository {

	public Docent read(long id, EntityManager entityManager) {
		return entityManager.find(Docent.class, id);
	}
	
	public void create(Docent docent, EntityManager entityManager) {
		entityManager.persist(docent);
	}
	
	public void delete(long id, EntityManager entityManager) {
		Docent teVerwijderenDocent = entityManager.find(Docent.class, id);
		if (teVerwijderenDocent != null) {
			entityManager.remove(teVerwijderenDocent);
		}
	}
}
