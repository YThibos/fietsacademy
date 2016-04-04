package be.vdab.services;

import javax.persistence.EntityManager;

import be.vdab.entities.Docent;
import be.vdab.filters.JPAFilter;
import be.vdab.repositories.DocentRepository;

public class DocentService {

	private final DocentRepository docentRepository = new DocentRepository();
	
	public Docent read(long id)  {
		
		EntityManager entityManager = JPAFilter.getEntityManager();
		
		try {
			return docentRepository.read(id, entityManager);
		}
		finally {
			entityManager.close();
		}
		
	}
	
	public void create(Docent docent) {
		
		EntityManager entityManager = JPAFilter.getEntityManager();
		
		try {
			entityManager.getTransaction().begin();
			docentRepository.create(docent, entityManager);
			entityManager.getTransaction().commit();
		}
		catch (RuntimeException ex) {
			entityManager.getTransaction().rollback();
			throw ex;
		} finally {
			entityManager.close();
		}
		
	}
	
	public void delete(long id) {
		
		EntityManager entityManager = JPAFilter.getEntityManager();
		
		try {
			entityManager.getTransaction().begin();
			docentRepository.delete(id, entityManager);
			entityManager.getTransaction().commit();
		} 
		catch (RuntimeException ex) {
			entityManager.getTransaction().rollback();
			throw ex;
		}
		finally {
			entityManager.close();
		}
		
	}
	
}
