package be.vdab.repositories;

import java.math.BigDecimal;
import java.util.List;

import be.vdab.entities.Docent;
import be.vdab.valueobjects.AantalDocentenPerWedde;
import be.vdab.valueobjects.VoornaamEnId;

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
	
	public List<Docent> findByWeddeBetween(BigDecimal van, BigDecimal tot, int vanafRij, int aantalRijen) {
		return getEntityManager()
//				.createQuery("SELECT d FROM Docent d"
//						+ " WHERE d.wedde BETWEEN :van AND :tot"
//						+ " ORDER BY d.wedde DESC, d.id", Docent.class)
				.createNamedQuery("Docent.findByWeddeBetween", Docent.class)
				.setParameter("van", van)
				.setParameter("tot", tot)
				.setFirstResult(vanafRij)
				.setMaxResults(aantalRijen)
				.getResultList();
	}
	
	public List<VoornaamEnId> findVoornamen() {
		return getEntityManager().createQuery(
				"SELECT new be.vdab.valueobjects.VoornaamEnId(d.id, d.voornaam) FROM Docent d", VoornaamEnId.class)
				.getResultList();
	}
	
	public BigDecimal findMaxWedde() {
		return getEntityManager().createQuery(
				"SELECT max(d.wedde) FROM Docent d", BigDecimal.class)
				.getSingleResult();
	}
	
	public List<AantalDocentenPerWedde> findAantalDocentenPerWedde() {
		return getEntityManager().createQuery(
				"SELECT new be.vdab.valueobjects.AantalDocentenPerWedde(d.wedde, count(d)) FROM Docent d GROUP BY d.wedde"
				, AantalDocentenPerWedde.class)
				.getResultList();
	}
}
