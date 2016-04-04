package be.vdab.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import be.vdab.enums.Geslacht;

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

}
