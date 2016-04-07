package be.vdab.valueobjects;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class TelefoonNr implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nummer;
	private boolean fax;
	private String opmerking;
	
	protected TelefoonNr() {}

	public TelefoonNr(String nummer, boolean fax, String opmerking) {
		this.nummer = nummer;
		this.fax = fax;
		this.opmerking = opmerking;
	}
	
	public String getNummer() {
		return nummer;
	}

	public boolean isFax() {
		return fax;
	}

	public String getOpmerking() {
		return opmerking;
	}

	@Override
	public String toString() {
		return nummer;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TelefoonNr)) {
			return false;
		}
		TelefoonNr telNr = (TelefoonNr) obj;
		return nummer.equalsIgnoreCase(telNr.nummer);
	}
	
	@Override
	public int hashCode() {
		return nummer.toUpperCase().hashCode();
	}
	
}
