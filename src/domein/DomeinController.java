package domein;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// voor taalpakket support
import resources.ResourceController;

public class DomeinController {

	private SpelerRepository spelerRepo;
	private List<Speler> huidigeSpelers;
	private Spel spel;
	private int aantalSpelers = 0;// om bij te houden hoeveel spelers op een bepaald moment in de array zitten
	private static final int MAX_SPELERS = 4;

	public DomeinController() {

		spelerRepo = new SpelerRepository();
		huidigeSpelers = new ArrayList<>(); 
	}

	// selecteer een speler
	/**
	 * @param naam en geboortejaar van een Speler
	 */
	public void selecteerSpeler(String naam, int geboorteJaar) {

		if (aantalSpelers == MAX_SPELERS) {
			throw new IllegalArgumentException(ResourceController.getTranslation("MaxPlayers"));
		}

		if (huidigeSpelers.contains(spelerRepo.geefSpeler(naam, geboorteJaar))) {
			throw new IllegalArgumentException(ResourceController.getTranslation("AllreadySelected"));
		}
		spelerRepo.selecteerSpeler(naam, geboorteJaar);
		huidigeSpelers.add(spelerRepo.geefSpeler(naam, geboorteJaar));
		aantalSpelers++;
	}

	/**
	 * registreer een speler
	 * 
	 * @param naam en geboortejaar Speler
	 */
	public void registreerSpeler(String naam, int geboorteJaar) {

		spelerRepo.registreerSpeler(new Speler(naam, geboorteJaar));
	}

	// lijst van geselecteerde spelers terug leeg maken.
	public void clearSpelers() {
		huidigeSpelers = new ArrayList<>();
		aantalSpelers = 0;
	}

	// alle static variabelen resetten
	public static void clearStatic() {
		Spel.totaalBeurt = 0;
	}

	/**
	 * @return geeft lijst van huidigeSpelers
	 */
	public List<String> geefHuidigeSpelers() {

		List<String> spelers = new LinkedList<>();
		for (Speler speler : spel.geefVolgordeSpelers()) {
			spelers.add(speler.getNaam());
		}
		return spelers;
	}

	// geeft de naam en speelkansen van geselecteerde spelers weer
	/**
	 * @return String die de details weergeeft van elke geselecteerde speler
	 */
	public String geefGeselecteerdeSpelers() {
		String details = "";
		// int teller = 0;

		for (Speler speler : huidigeSpelers) {
			details += speler.toString() + "\n";
		}

		return details;
	}

	/**
	 * @return lijst van alle scorebladen van elke geselecteerde speler
	 */
	public List<ScoreBlad> toonAlleScoreBladen() {
		List<ScoreBlad> scores = new LinkedList<>();
		for (Speler speler : huidigeSpelers) {
			scores.add(speler.getScoreblad());
		}
		return scores;
	}

	/**
	 * @return 2dimensionele array van strings dat het scoreblad van de huidige
	 *         speler aan de beurt voorstelt
	 */
	public String[][] toonScoreBladSpeler() {
		return spel.getSpeler().getScoreblad().geefBlad();
	}

	/**
	 * @return geeft hoeveel geselecteerde spelers er zijn
	 */
	public int geefAantalSpelers() {
		return huidigeSpelers.size();
	}

	// Start spel
	public void startNieuwSpel() {
		setSpel(new Spel(huidigeSpelers));
		for (Speler speler : huidigeSpelers) {
			spelerRepo.slaSpeelKansOp(speler);
		}
	}

	public void setSpel(Spel spel) {
		this.spel = spel;
	}

	// bepaalt wie er aan de beurt is
	/**
	 * @return geeft naam van de speler dat aan de beurt is
	 */
	public String aanBeurt() {
		spel.aanBeurt();

		return spel.getSpeler().getNaam() + resources.ResourceController.getTranslation("Turn");
	}

	// geeft de winnaar weer en slaat zijn speelkans op
	/**
	 * @param geeft de winaar weer in een String
	 */
	public String geefWinnaar() {
		Speler winnaar = spel.getWinnaar();
		spelerRepo.slaSpeelKansOp(winnaar);
		return ResourceController.getTranslation("Congrats") + winnaar.getNaam()
				+ ResourceController.getTranslation("UAreWinner") + winnaar.getSpeelkans() + " "
				+ ResourceController.getTranslation("Chances");
	}

	// waarde van de stenen weergeven
	/**
	 * @return een array met de waarden dat stenen voorstelt
	 */
	public int[] neemStenen() {
		List<Integer> stenen = spel.neemStenen();
		int[] array = new int[stenen.size()];
		int teller = 0;

		for (int st : stenen) {
			array[teller] = st;
			teller++;
		}

		return array;
	}

	// leg een steentje terug in de pot
	public void legSteentjesTerug() {
		spel.legSteentjesTerug();
	}

	/**
	 * selecteer een steentje
	 * 
	 * @param waarde van een steentje
	 */
	public void selecteerSteentje(int waarde) {
		spel.selecteerSteentje(waarde);
	}

	/**
	 * registreer een steentje op een vakje
	 * 
	 * @param rijIndex en kolomIndex van een vakje
	 */
	public void registreerWaardeVakje(int rij, int kolom) {
		spel.registreerWaardeVakje(rij, kolom);
	}

	/**
	 * @param rijIndex en kolomIndex van een vakje
	 * @return geeft de waarde van een steen op een vakje als een String
	 */
	public String geefWaardeVakje(int rij, int kolom) {
		int waarde = spel.geefWaardeVakje(rij, kolom);

		return String.format("%d", waarde);
	}

	// vul het scoreblad van een speler aan
	public void vulScoreBladAan() {
		spel.vulScoreBladAan();
	}

	// geeft weer of er nog een steentje kan gelegd worden en geeft een tip waar een
	// steentje kan gelegd worden
	/**
	 * @return geeft een String dat aanduit op welk vakje een steen kan gelegd
	 *         worden
	 */
	public String geefTipLeggenSteentje() {
		int[] array = spel.geefTipLeggenSteentje();
		if (array[0] == 0 && array[1] == 0) {

			return resources.ResourceController.getTranslation("OtherStone");
		}
		return String.format(resources.ResourceController.getTranslation("GiveTip"), array[0] + 1, array[1] + 1);
	}

	// indien we image van het vakje dat de tip bedoelt willen veranderen in gui
	/**
	 * @return geeft een array dat rijIndex en kolomIndex van het vakje dat de tip
	 *         bedoelt bevat weer
	 */
	public int[] geefRij_en_KolomTip() {
		int[] array = spel.geefTipLeggenSteentje();
		if (array[0] == 0 && array[1] == 0) {
			return null;
		}
		return array;
	}

	/**
	 * @return geeft de huidig geselecteerde steen weer
	 */
	public int geefSteen() {
		return spel.geefSteen();
	}

	// gaan kijken of het spel al ten einde is gelopen
	public boolean eindeSpel() {
		return spel.einde();
	}

	/**
	 * @return geeft de volgorde van alle geselecteerde spelers als een String
	 */
	public String geefVolgordeSpelers() {
		List<Speler> spelers = spel.geefVolgordeSpelers();
		String string = "";
		int teller = 1;

		for (Speler speler : spelers) {
			string += teller + " " + speler.getNaam() + "\n";

			teller++;
		}
		return string;
	}

	// geef de details van 1 specifieke speler weer
	/**
	 * @param naam en geboortejaar van een speler
	 * @return geeft informatie over een speler in String
	 */
	public String geefSpeler(String naam, int geboorteJaar) {

		return spelerRepo.geefSpeler(naam, geboorteJaar).toStringRegistreer();
	}

	/**
	 * @return geeft een String over de huidigeSpeler, wordt weergegeven boven het
	 *         scoreblad
	 */
	public String geefHuidigeSpeler() {
		return spel.getSpeler().getNaam() + ", dit is uw score";
	}

	// cleart de waarde van het huidige steentje
	public void clearSteentje() {
		spel.clearSteentje();
	}

	// annuleert het leggen van een steentje op een vakje, waarde steentje wordt
	// gereset en waarde op vakje verwijdert
	public void annuleerLeggenSteentje() {
		spel.annuleer();

	}

	/**
	 * @param rijIndex en kolomIndex van een vakje
	 * @return boolean dat de kleurt van een vakje voorstelt, false is wit, true is
	 *         grijs
	 */
	public boolean getKleurVakje(int rij, int kolom) {
		return spel.getKleur(rij, kolom);
	}
//
}