package be.vdab.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import be.vdab.enums.Geslacht;

//@NamedQuery(name = "Docent.findByWeddeBetween",
//query = "SELECT d FROM Docent d WHERE d.wedde BETWEEN :van AND :tot"
//		+ " ORDER BY d.wedde, d.id")

@Entity
@Table(name = "docenten")
public class Docent implements Serializable {

	private static final long serialVersionUID = 1L;
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String voornaam;
	private String familienaam;
	@Enumerated(EnumType.STRING)
	private Geslacht geslacht;
	private BigDecimal wedde;
	private long rijksregisternr;

	protected Docent() {
	}

	public Docent(String voornaam, String familienaam, Geslacht geslacht, BigDecimal wedde, long rijksregisternr) {
		setVoornaam(voornaam);
		setFamilienaam(familienaam);
		setGeslacht(geslacht);
		setWedde(wedde);
		setRijksregisternr(rijksregisternr);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getVoornaam() {
		return voornaam;
	}

	public void setVoornaam(String voornaam) {
		if (!isStringValid(voornaam)) {
			throw new IllegalArgumentException();
		}
		this.voornaam = voornaam;
	}

	public String getFamilienaam() {
		return familienaam;
	}

	public void setFamilienaam(String familienaam) {
		if (!isStringValid(familienaam)) {
			throw new IllegalArgumentException();
		}
		this.familienaam = familienaam;
	}

	public String getNaam() {
		return voornaam + " " + familienaam;
	}

	public BigDecimal getWedde() {
		return wedde;
	}

	public void setWedde(BigDecimal wedde) {
		if (!isWeddeValid(wedde)) {
			throw new IllegalArgumentException();
		}
		this.wedde = wedde;
	}

	public long getRijksregisternr() {
		return rijksregisternr;
	}

	public void setRijksregisternr(long rijksregisternr) {
		if (!isRijksregisternrValid(rijksregisternr)) {
			throw new IllegalArgumentException();
		}
		this.rijksregisternr = rijksregisternr;
	}

	public Geslacht getGeslacht() {
		return geslacht;
	}

	public void setGeslacht(Geslacht geslacht) {
		this.geslacht = geslacht;
	}

	public static boolean isStringValid(String string) {
		return string != null && !string.isEmpty();
	}

	public static boolean isWeddeValid(BigDecimal wedde) {
		return wedde != null && wedde.compareTo(BigDecimal.ZERO) >= 0;
	}

	public static boolean isRijksregisternrValid(long rijksregisternr) {
		long getal = rijksregisternr / 100;
		if (rijksregisternr / 1_000_000_000 < 50) {
			getal += 2_000_000_000;
		}
		return rijksregisternr % 100 == 97 - getal % 97;
	}

	public void opslag(BigDecimal percentage) {
		BigDecimal factor = BigDecimal.ONE.add(percentage.divide(BigDecimal.valueOf(100)));
		wedde = wedde.multiply(factor).setScale(2, RoundingMode.HALF_UP);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (rijksregisternr ^ (rijksregisternr >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Docent))
			return false;
		Docent other = (Docent) obj;
		if (id != other.id)
			return false;
		if (rijksregisternr != other.rijksregisternr)
			return false;
		return true;
	}
	
	
	
}
