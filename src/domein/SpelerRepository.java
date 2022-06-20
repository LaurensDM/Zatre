package domein;

import persistentie.SpelerMapper;
import resources.ResourceController;

public class SpelerRepository {
	private SpelerMapper spelerMapper;

	public SpelerRepository() {
		spelerMapper = new SpelerMapper();

	}

	/**
	 * selecteer een speler
	 * 
	 * @param naam en geboorteJaar van de speler
	 */
	public void selecteerSpeler(String naam, int geboorteDatum) {// checkt of de speler bestaat en returnt deze indien
																	// het bestaat

		Speler speler = spelerMapper.geefSpeler(naam, geboorteDatum);
		if (speler == null) {
			throw new IllegalArgumentException(ResourceController.getTranslation("PlayerNotExist"));
		}
		if (speler.getSpeelkans() == 0) {
			throw new IllegalArgumentException(ResourceController.getTranslation("PlayerNoChances"));
		}

	}

	/**
	 * @param naam en geboorteJaar van de speler
	 * @return geeft een specifieke speler
	 */
	public Speler geefSpeler(String naam, int geboorteJaar) {// geeft een bepaalde Speler weer, zonder controles

		return spelerMapper.geefSpeler(naam, geboorteJaar);
	}

	/**
	 * registreert een speler
	 * 
	 * @param een speler
	 */
	public void registreerSpeler(Speler speler) {// registreert een nieuwe Speler, geeft foutmelding indien Speler al
													// bestaat

		if (spelerMapper.geefLijst().contains(speler)) {
			throw new IllegalArgumentException(ResourceController.getTranslation("PlayerAlreadyExist"));
		} else {
			spelerMapper.voegToe(speler);
		}

	}

	// slaat de verandering in speelkans op in de database voor een speler
	/**
	 * @param een speler
	 */
	public void slaSpeelKansOp(Speler speler) {
		spelerMapper.slaSpeelkansOp(speler);
	}
//
}