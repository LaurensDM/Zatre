package domein;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import resources.Pref;
import resources.ResourceController;

public class Pot {
	private int steen;
	private List<Integer> steentjes;
	private List<Integer> stenen;
	private boolean steenGeselecteerd = false;
	private int amountOfStones = 20;

	// de zak met steentjes wordt aangemaakt
	public Pot() {
		steentjes = new LinkedList<>();
		if (Boolean.parseBoolean(Pref.getPreference("ShortGamemode"))) {
			amountOfStones = 3;
			System.err.println("Generating only 19 stones!");
		}
		// voor waardes van 2 t.e.m. 6 zijn er telkens 20 steentjes
		for (int i = 1; i <= 6; i++) {
			vulLijstStenen(i);
		}

		// random volgorde van steentjes
		Collections.shuffle(steentjes);

	}

	/**
	 * selecteer een steentje
	 * 
	 * @param waarde dat een steen voorsteld
	 */
	public void selecteerSteentje(int waarde) {
		if (this.steenGeselecteerd) {
			throw new IllegalArgumentException("Er is al een steen geselecteerd");
		} else {
			steen = waarde;
			this.steenGeselecteerd = true;
		}

	}

	// methode om steentjes terug te gaan steken in de zak
	public void legSteentjesTerug() {
		int waarde = getSteen();
		if (stenen.contains(waarde)) {
			int index = stenen.indexOf(waarde);
			steentjes.add(stenen.remove(index));
			Collections.shuffle(steentjes);
		}

	}

	/**
	 * neem stenen uit de zak met stenen
	 * 
	 * @return geeft een lijst met waarden (steentjes)
	 */
	public List<Integer> neemStenen() {
		stenen = new ArrayList<>();
		if (einde()) {
			throw new IllegalArgumentException(ResourceController.getTranslation("GameEndReached"));
		}
		if (steenGeselecteerd) {
			throw new IllegalArgumentException("Er is al een steen geselecteerd");
		}
		if (Spel.getTotaalBeurt() == 1) {
			for (int i = 0; i < 3; i++) {
				stenen.add(steentjes.remove(0));
			}
		} else {
			for (int i = 0; i < 2; i++) {
				stenen.add(steentjes.remove(0)); // TODO catch IndexOutOfBoundsException wanneer pot leeg is!
			}
		}
		return stenen;
	}

	/**
	 * vult de zak met stenen
	 * 
	 * @param een waarde van 1 tot 6
	 */
	private void vulLijstStenen(int waarde) {
		if (waarde > 1) {

			for (int i = 0; i < amountOfStones; i++) {
				steentjes.add(waarde);
			}
		} else {
			for (int i = 0; i < (amountOfStones + 1); i++) {
				steentjes.add(1);
			}
		}
	}

	/**
	 * @return geeft een boolean dat het einde van het spel weergeeft.
	 */
	public boolean einde() {
		if (steentjes.size() < 2) {

			return true;
		}

		return false;
	}

	// geef de huidig geselecteerde steen
	public int getSteen() {
		return steen;
	}

	// clear de waarde van de huidig geselecteerde steen
	public void clearSteenWaarde() {
		this.steenGeselecteerd = false;
		this.steen = 0;

	}
//
}