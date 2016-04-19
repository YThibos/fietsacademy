package be.vdab.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;

import be.vdab.enums.Geslacht;

//@NamedQuery(name = "Docent.findByWeddeBetween",
//query = "SELECT d FROM Docent d WHERE d.wedde BETWEEN :van AND :tot"
//		+ " ORDER BY d.wedde, d.id")

@Entity
@Table(name = "docenten")
@NamedEntityGraph(
		name = Docent.MET_CAMPUS, 
		attributeNodes = @NamedAttributeNode("campus")
		)
public class Docent implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String MET_CAMPUS = "Docent.metCampus";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String voornaam;
	private String familienaam;
	@Enumerated(EnumType.STRING)
	private Geslacht geslacht;
	private BigDecimal wedde;
	private long rijksregisternr;

	@ElementCollection
	@CollectionTable(name = "docentenbijnamen", joinColumns = @JoinColumn(name = "docentid"))
	@Column(name = "Bijnaam")
	private Set<String> bijnamen;

	// DECOMMENT TO MAKE MANY(DOCENT)-TO-ONE(CAMPUS)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "campusid")
	private Campus campus;

	@ManyToMany(mappedBy = "docenten")
	private Set<Verantwoordelijkheid> verantwoordelijkheden = new LinkedHashSet<>();

	public void addVerantwoordelijkheid(Verantwoordelijkheid verantwoordelijkheid) {
		verantwoordelijkheden.add(verantwoordelijkheid);
		if (!verantwoordelijkheid.getDocenten().contains(this)) {
			verantwoordelijkheid.addDocent(this);
		}
	}

	public void removeVerantwoordelijkheid(Verantwoordelijkheid verantwoordelijkheid) {
		verantwoordelijkheden.remove(verantwoordelijkheid);
		if (verantwoordelijkheid.getDocenten().contains(this)) {
			verantwoordelijkheid.removeDocent(this);
		}
	}

	public Set<Verantwoordelijkheid> getVerantwoordelijkheden() {
		return Collections.unmodifiableSet(verantwoordelijkheden);
	}

	// CONSTRUCTORS
	protected Docent() {
	}

	public Docent(String voornaam, String familienaam, Geslacht geslacht, BigDecimal wedde, long rijksregisternr) {
		setVoornaam(voornaam);
		setFamilienaam(familienaam);
		setGeslacht(geslacht);
		setWedde(wedde);
		setRijksregisternr(rijksregisternr);
		bijnamen = new HashSet<>();
		verantwoordelijkheden = new LinkedHashSet<>();
	}

	// GETTERS & SETTERS
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

	public Set<String> getBijnamen() {
		return Collections.unmodifiableSet(bijnamen);
	}

	public Campus getCampus() {
		return campus;
	}

	public void setCampus(Campus campus) {
		// IF THERE IS A REFERENCE IN CURRENT CAMPUS TO THIS DOCENT, REMOVE IT
		if (this.campus != null && this.campus.getDocenten().contains(this)) {
			this.campus.removeDocent(this);
		}
		this.campus = campus;
		// IF NEW CAMPUS DOES NOT REFERENCE THIS DOCENT YET, FIX IT
		if (campus != null && !campus.getDocenten().contains(this)) {
			campus.addDocent(this);
		}
	}

	// VALIDATION METHODS
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

	// OBJECT METHOD OVERRIDES
	@Override
	public int hashCode() {
		return Long.valueOf(rijksregisternr).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Docent)) {
			return false;
		}
		return this.rijksregisternr == ((Docent) obj).rijksregisternr;
	}

	// DOCENT METHODS
	public void addBijnaam(String bijnaam) {
		bijnamen.add(bijnaam);
	}

	public void removeBijnaam(String bijnaam) {
		bijnamen.remove(bijnaam);
	}

}
