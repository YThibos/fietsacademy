package be.vdab.services;

import javax.persistence.EntityManager;

import be.vdab.filters.JPAFilter;

abstract class AbstractService {

	private EntityManager getEntityManager() {
		return JPAFilter.getEntityManager();
	}
	
	protected void beginTransaction() {
		getEntityManager().getTransaction().begin();
	}
	
	protected void commit() {
		getEntityManager().getTransaction().commit();
	}
	
	protected void rollback() {
		getEntityManager().getTransaction().rollback();
	}
	
}
