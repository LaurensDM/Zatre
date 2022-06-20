package domein;

import java.time.LocalDate;
import java.util.Objects;

import resources.ResourceController;

public class Speler {
	private String naam;
	private int geboorteJaar;
	private int speelkans;
	private ScoreBlad scoreblad;

	/**
	 * @param naam, geboortejaar en speelkansen van een speler
	 */
	public Speler(String naam, int geboorteDatum, int speelkans) {
		setNaam(naam);
		setGeboorteJaar(geboorteDatum);
		this.speelkans = speelkans;
		scoreblad = new ScoreBlad();

	}

	/**
	 * @param naam en geboortejaar van een speler
	 */
	public Speler(String naam, int geboorteDatum) {
		this(naam, geboorteDatum, 5);
		
	}

	/**
	 * @return Scoreblad van een speler
	 */
	public ScoreBlad getScoreblad() {
		return scoreblad;
	}

	public String getNaam() {
		return naam;
	}

	private void setNaam(String naam) {
		if (naam == null || naam.isBlank()) {
			throw new IllegalArgumentException(ResourceController.getTranslation("NameCannotEmpty"));
		}
		int lengte = naam.length();
		if (lengte <= 4) {
			throw new IllegalArgumentException("moet minstens 5 zijn");
		}

		this.naam = naam;
	}

	public int getGeboorteJaar() {
		return geboorteJaar;
	}

	private void setGeboorteJaar(int geboorteJaar) {
		if ((LocalDate.now().getYear() - geboorteJaar) < 6) {
			throw new IllegalArgumentException(ResourceController.getTranslation("PlayerTooYoung"));
		}
		this.geboorteJaar = geboorteJaar;
	}

	public int getSpeelkans() {
		return speelkans;
	}

	/**
	 * wijzigt de speelkans van een speler
	 * 
	 * @param getal dat wordt opgeteld of afgetrokken van het aantal speelkansen van
	 *              een speler
	 */
	public void wijzigSpeelkans(int wijziging) {
		this.speelkans += wijziging;
	}

	@Override
	public String toString() {
		return String.format("%s %s %s %d %s", this.getClass().getSimpleName(), naam,
				ResourceController.getTranslation("YouHaveLeft"), speelkans,
				speelkans >= 2 ? ResourceController.getTranslation("PlayChanceMulti")
						: ResourceController.getTranslation("PlayChanceSingle"));
	}

	public String toStringRegistreer() {
		return String.format("%s, %s %d %s ", naam, ResourceController.getTranslation("YouCanOnly"), speelkans,
				ResourceController.getTranslation("ToPlayGames"));
	}

	@Override
	public int hashCode() {
		return Objects.hash(geboorteJaar, naam);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Speler other = (Speler) obj;
		return geboorteJaar == other.geboorteJaar && Objects.equals(naam, other.naam);
	}
//
}