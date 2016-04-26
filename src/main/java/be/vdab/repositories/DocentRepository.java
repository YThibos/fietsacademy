package be.vdab.repositories;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.LockModeType;
import javax.persistence.NoResultException;

import be.vdab.entities.Campus;
import be.vdab.entities.Docent;
import be.vdab.valueobjects.AantalDocentenPerWedde;
import be.vdab.valueobjects.VoornaamEnId;

public class DocentRepository extends AbstractRepository {

	public Docent read(long id) {
		return getEntityManager().find(Docent.class, id);
	}
	
	public Docent readWithLock(long id) {
		return getEntityManager().find(Docent.class, id, LockModeType.PESSIMISTIC_WRITE);
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
				.setHint("javax.persistence.loadgraph", getEntityManager().createEntityGraph(Docent.MET_CAMPUS))
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
	
	public void algemeneOpslag(BigDecimal factor) {
		getEntityManager().createNamedQuery("Docent.algemeneOpslag")
			.setParameter("factor", factor)
			.executeUpdate();
	}
	
	public Docent findByRijksregisternr(long rijksregisternr) {
		try {
			return getEntityManager().createNamedQuery("Docent.findByRijksregisternr", Docent.class)
					.setParameter("rijksregisternr", rijksregisternr)
					.getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}
	}
	
	public List<Docent> findBestBetaaldeVanEenCampus(Campus campus) {
		return getEntityManager().createNamedQuery("Docent.findBestBetaaldeVanEenCampus", Docent.class)
				.setParameter("campus", campus)
				.getResultList();
	}
}
