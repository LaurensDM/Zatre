package domein;

import resources.ResourceController;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Spel {
	private Bord bord;
	private List<Speler> spelers;
	private Speler speler;
	private Speler winnaar;
	private int aanBeurt = 0;
	protected static int totaalBeurt = 0;
	private Pot pot;

	/**
	 * @param lijst van de geselecteerde spelers
	 */
	public Spel(List<Speler> spelerList) {

		// Als er minder dan 2 Spelers geselecteerd zijn, gooi exception
		if (spelerList.size() < 2) {
			throw new IllegalArgumentException(ResourceController.getTranslation("Atleast2SelectedPlayers"));
		}

		// Als 1 van de spelers geen speelkansen meer heeft, gooi exception
		for (Speler speler : spelerList) {
			if (speler.getSpeelkans() == 0) {
				throw new IllegalArgumentException(ResourceController.getTranslation("Player") + speler.getNaam()
						+ ResourceController.getTranslation("NoChances"));
			}
			speler.getScoreblad().maakScoreBlad(spelerList.size());
		}

		// lijst van spelers opvullen met lijst doorgegeven in de constructor ( dit is
		// normaal gezien de lijst van geselecteerde spelers uit de domeincontroller)
		spelers = spelerList;

		// Speelbord aanmaken
		bord = new Bord();
		pot = new Pot();

		// voor elke speler de speelkans verlagen met 1 wanneer het Spel start
		for (Speler speler : spelerList) {
			speler.wijzigSpeelkans(-1);
		}

		// bepaalt de volgorde van de spelers
		Collections.shuffle(spelers);

	}

	// neem een aantal steentjes
	/**
	 * @return geeft een lijst met waarden (stenen)
	 */
	public List<Integer> neemStenen() {

		return pot.neemStenen();
	}

	/**
	 * @return geeft de waarde van de huidig geselecteerde steen
	 */
	public int geefSteen() {
		return pot.getSteen();
	}

	// bepalen welke speler aan de beurt is
	public void aanBeurt() {
		if (pot.einde() == true) {
			for (Speler speler : spelers) {
				speler.getScoreblad().berekenTotaal();
			}
			throw new IllegalArgumentException(ResourceController.getTranslation("GameEndAlreadyReached"));
		}

		bord.volgendeBeurt();
		speler = spelers.get(aanBeurt);
		aanBeurt++;
		totaalBeurt++;

		if (aanBeurt == spelers.size()) {
			aanBeurt = 0;
		}
	}
	// registreert de winnaar

	/**
	 * @return geeft de Speler die geregistreerd werd als de winnaar
	 */
	public Speler getWinnaar() {
		bepaalWinnaar();
		return winnaar;
	}

	// bepaalt de winnaar
	private void bepaalWinnaar() {
		Speler vorigeSpeler = null;
		for (Speler speler : spelers) {
			if (winnaar == null) {
				winnaar = speler;
			}
			if (vorigeSpeler != null
					&& speler.getScoreblad().getTotaleScore() > vorigeSpeler.getScoreblad().getTotaleScore()) {
				this.winnaar = speler;
			}
			vorigeSpeler = speler;
		}
		winnaar.wijzigSpeelkans(2);
	}

	// de speler die momenteel aan de beurt is weergeven
	public Speler getSpeler() {
		return speler;
	}

	public static int getTotaalBeurt() {
		return totaalBeurt;
	}

	// geeft een bepaald vakje een waarde
	/**
	 * @param rijIndex en kolomIndex van een vakje
	 */
	public void registreerWaardeVakje(int rij, int kolom) {

		// als het de eerste beurt is moet het eerste steentje op het middelste vakje
		// gelegd worden
		if (totaalBeurt == 1) {
			if (bord.geefWaardeVakje(7, 7) == 0) {
				if (rij == 7 && kolom == 7) {
					bord.registreerVakje(7, 7, pot.getSteen());

				} else {
					throw new IllegalArgumentException(ResourceController.getTranslation("FirstStoneNotMid"));
				}
			} else
				bord.registreerVakje(rij, kolom, pot.getSteen());
		} else {
			bord.registreerVakje(rij, kolom, pot.getSteen());
		}
	}

	// geeft het waarde van een specifiek vakje weer
	/**
	 * @param rijIndex en kolomIndex van een vakje
	 * @return geeft de waarde van een steentje op een vakje
	 */
	public int geefWaardeVakje(int rij, int kolom) {
		return bord.geefWaardeVakje(rij, kolom);
	}

	/**
	 * geeft een tip bij het leggen van een steentje
	 * 
	 * @return geeft een array met rijIndex en kolomIndex van een vakje
	 */
	public int[] geefTipLeggenSteentje() {

		int huidigeSteen = pot.getSteen();
		int[] arrayTip = bord.geefTipSteentjeLeggen(huidigeSteen);

		return arrayTip;
	}

	// selecteert een steentje
	/**
	 * @param waarde dat een steentje voorstelt
	 */
	public void selecteerSteentje(int waarde) {
		pot.selecteerSteentje(waarde);
	}

	// methode om steentjes terug te gaan steken in de zak
	public void legSteentjesTerug() {
		pot.legSteentjesTerug();
	}

	// vult het scoreblad aan
	public void vulScoreBladAan() {
		if (totaalBeurt == 1) {
			speler.getScoreblad().x2score();
		}
		if (bord.getVorigVakje() != null && bord.getVorigVakje().getKleur() == true
				&& bord.getVorigVakje().getWaarde() != 0) {
			speler.getScoreblad().x2score();
		}
		if (bord.getHuidigVakje() != null && bord.getHuidigVakje().getKleur() == true
				&& bord.getHuidigVakje().getWaarde() != 0) {
			speler.getScoreblad().x2score();
		}
		speler.getScoreblad().vulScoreBladIn(bord.getLijstScore());
		speler.getScoreblad().volgendBeurt();
	}

	/**
	 * @return geeft lijst met spelers nadat volgorde bepaald werd
	 */
	public List<Speler> geefVolgordeSpelers() {

		return this.spelers;
	}

	// geeft het einde van het spel weer
	public boolean einde() {
		return pot.einde();
	}

	// cleart de waarde van het huidige steentje
	public void clearSteentje() {
		pot.clearSteenWaarde();
	}

	// annuleert het leggen van een steentje op een vakje
	public void annuleer() {
		bord.clearVakje();
		pot.clearSteenWaarde();

	}

	/**
	 * @param rijIndex en kolomIndex van een vakje
	 * @return geeft kleur van een vakje als een boolean, wit is false, grijs is
	 *         true
	 */
	public boolean getKleur(int rij, int kolom) {
		return bord.getKleur(rij, kolom);
	}
}