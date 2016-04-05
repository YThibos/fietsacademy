package be.vdab.repositories;

import java.math.BigDecimal;
import java.util.List;

import be.vdab.entities.Docent;

public class DocentRepository extends AbstractRepository {

	public Docent read(long id) {
		return getEntityManager().find(Docent.class, id);
	}
	
	public void create(Docent docent) {
		getEntityManager().persist(docent);
	}
	
	public void delete(long id) {
		Docent teVerwijderenDocent = getEntityManager().find(Docent.class, id);
		if (teVerwijderenDocent != null) {
			getEntityManager().remove(teVerwijderenDocent);
		}
	}
	
	public List<Docent> findByWeddeBetween(BigDecimal van, BigDecimal tot) {
		return getEntityManager().createQuery("SELECT d FROM Docent d", Docent.class).getResultList();
	}
}
