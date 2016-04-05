package be.vdab.services;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import be.vdab.entities.Docent;
import be.vdab.filters.JPAFilter;
import be.vdab.repositories.DocentRepository;

public class DocentService extends AbstractService {

	private final DocentRepository docentRepository = new DocentRepository();
	
	public Docent read(long id)  {
		return docentRepository.read(id);		
	}
	
	public void create(Docent docent) {
		beginTransaction();
		docentRepository.create(docent);
		commit();
	}
	
	public void delete(long id) {
		beginTransaction();
		docentRepository.delete(id);
		commit();
	}
	
	public void opslag(long id, BigDecimal percentage) {
		beginTransaction();
		docentRepository.read(id).opslag(percentage);
		commit();
	}
	
}
