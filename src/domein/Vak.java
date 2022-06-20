package domein;

public class Vak {
	// private int waarde;
	private Boolean kleur;
	private int waarde;

	/**
	 * @param een kleur dat wordt voorgesteld als een boolean, wit is false, grijs
	 *            is true
	 */
	public Vak(Boolean kleur) {
		setKleur(kleur);
	}

	// wit = false
	// grijs = true
	/**
	 * @param een kleur dat wordt voorgesteld als een boolean, wit is false, grijs
	 *            is true
	 */
	private void setKleur(Boolean kleur) {
		this.kleur = kleur;
	}

	public Boolean getKleur() {
		return kleur;
	}

	/**
	 * @param waarde stelt een steen voor, dat op een vakje wordt gelegd
	 */
	public void setWaarde(int waarde) {
		this.waarde = waarde;
	}

	public int getWaarde() {
		return waarde;
	}
//
}