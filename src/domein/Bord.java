package domein;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import resources.ResourceController;

public class Bord {
	private Vak[][] vakjes = new Vak[15][15];
	private Vak huidigVakje;
	private Vak eersteVakje;
	private Vak vorigVakje;
	private boolean wijzigSomV = false;
	private boolean wijzigSomH = false;
	private boolean aanVorigVakje = false;
	private int scoreVerticaal = 0;
	private int scoreHorizontaal = 0;
	private List<Integer> lijstScore = new ArrayList<>();

	// maakt het spelbord
	public Bord() {
		// create cell
		Boolean NoPrint = false;

		// create row
		for (int rij = 0; rij < 15; rij++) {

			// create column
			for (int kolom = 0; kolom < 15; kolom++) {

				// Outer cells
				if (rij == 0 || kolom == 0 || rij == 14 || kolom == 14) {
					NoPrint = false;
					// For top and bottom line
					if (rij == 0 || rij == 14) {

						// White field top & bottom
						if (kolom > 3 && kolom < 6) {
							vakjes[rij][kolom] = new Vak(false);

							NoPrint = true;
						}

						// Grey fields on top & bottom
						if (kolom == 6) {
							vakjes[rij][kolom] = new Vak(true);
							NoPrint = true;
						}

						// Grey fields on top & bottom
						if (kolom == 8) {
							vakjes[rij][kolom] = new Vak(true);
							NoPrint = true;
						}

						// White field top & bottom
						if (kolom > 8 && kolom < 11) {
							vakjes[rij][kolom] = new Vak(false);
							NoPrint = true;
						}
					}

					// For side fields
					if (kolom == 0 || kolom == 14) {

						// White fields side
						if (rij > 3 && rij < 6) {
							vakjes[rij][kolom] = new Vak(false);
							NoPrint = true;
						}

						// Grey fields side
						if (rij == 6) {
							vakjes[rij][kolom] = new Vak(true);
							NoPrint = true;
						}
						// Grey fields side
						if (rij == 8) {
							vakjes[rij][kolom] = new Vak(true);
							NoPrint = true;
						}

						// White fields side
						if (rij > 8 && rij < 11) {
							vakjes[rij][kolom] = new Vak(false);
							NoPrint = true;
						}
					}

					// in array you prob don't need this as this should remain empty and not print
					// 'B'
					if (NoPrint == false) {
					}

				} else {

					if (rij == kolom) {
						vakjes[rij][kolom] = new Vak(true);

					} else {

						if (14 - kolom == rij) {
							vakjes[rij][kolom] = new Vak(true);
						} else {
							vakjes[rij][kolom] = new Vak(false);
						}
					}
				}
			}

		}
	}

	// cleart het huidige vakje
	public void clearVakje() {
		if (Spel.getTotaalBeurt() == 1 && huidigVakje == null) {
			eersteVakje.setWaarde(0);
		} else {
			huidigVakje.setWaarde(0);
			huidigVakje = vorigVakje;
		}

	}

	/**
	 * @ geeft een tip waar een steen gelegd kan worden
	 * 
	 * @param waarde van het geselecteerde steentje
	 * @return rijIndex en kolomIndex van het vakje waarop een steentje kan gelegd
	 *         worden
	 */
	public int[] geefTipSteentjeLeggen(int waarde) {

		int[] array = new int[2];
		int somRij;
		int somKolom;

		for (int rij = 0; rij < vakjes.length; rij++) {

			for (int kolom = 0; kolom < vakjes.length; kolom++) {
				if (vakjes[rij][kolom] != null) {

					if (vakjes[rij][kolom].getWaarde() == 0) {

						if (controleerEnkelAanVorigeSteen(rij, kolom) == false) {

							somRij = geefSomRijOpIndex(rij, kolom, 1) + geefSomRijOpIndex(rij, kolom, -1);
							somKolom = geefSomKolomOpIndex(rij, kolom, 1) + geefSomKolomOpIndex(rij, kolom, -1);

							if (somKolom > 0 || somRij > 0) {

								if (waarde + somKolom <= 12) {
									if (waarde + somRij <= 12) {
										if (vakjes[rij][kolom].getKleur() == true) {
											if (waarde + somKolom >= 10 || waarde + somRij >= 10) {
												array[0] = rij;
												array[1] = kolom;
												return array;
											}
										} else {
											array[0] = rij;
											array[1] = kolom;
											return array;
										}

									}

								}
							}
						}
					}

				}

			}

		}

		return array;

	}

	/**
	 * Geeft de som van de waarden op alle vakjes boven of onder een bepaald vakje,
	 * stopt als een 0 of null wordt bereikt
	 * 
	 * @param rijIndex en kolomIndex van een bepaald vakje en de richting waaring
	 *                 gecontrolleerd wordt (kan negatief of positief zijn)
	 * @return som vakjes van bepaalde richting op een kolom (tot 0 of null bereikt
	 *         wordt)
	 */
	private int geefSomKolomOpIndex(int indexRij, int index, int richting) {
		int som = 0;
		if (richting < 0) {
			for (int rij = indexRij - 1; rij >= 0; rij--) {

				if (vakjes[rij][index] != null) {
					if (vakjes[rij][index].getWaarde() != 0) {
						som += vakjes[rij][index].getWaarde();
						if (vakjes[rij][index].equals(vorigVakje) && vorigVakje != eersteVakje) {
							wijzigSomV = true;
						}
					} else
						return som;

				} else
					return som;

			}
		} else {
			for (int rij = indexRij + 1; rij < vakjes.length; rij++) {

				if (vakjes[rij][index] != null) {
					if (vakjes[rij][index].getWaarde() != 0) {
						som += vakjes[rij][index].getWaarde();
						if (vakjes[rij][index].equals(vorigVakje) && vorigVakje != eersteVakje) {
							wijzigSomV = true;
						}
					} else
						return som;

				} else
					return som;
			}
		}
		return som;
	}

	/**
	 * Geeft de som van de waarden op alle vakjes links of rechts van een bepaald
	 * vakje, stopt als een 0 of null wordt bereikt
	 * 
	 * @param rijIndex en kolomIndex van een bepaald vakje en de richting waaring
	 *                 gecontrolleerd wordt (kan negatief of positief zijn)
	 * @return som van vakjes op een rij (tot een 0 of null wordt bereikt) in een
	 *         bepaalde richting
	 */
	private int geefSomRijOpIndex(int index, int indexKolom, int richting) {
		int som = 0;

		if (richting < 0) {
			for (int kolom = indexKolom - 1; kolom >= 0; kolom--) {
				if (vakjes[index][kolom] != null) {
					if (vakjes[index][kolom].getWaarde() != 0) {

						som += vakjes[index][kolom].getWaarde();
						if (vakjes[index][kolom].equals(vorigVakje) && vorigVakje != eersteVakje) {
							wijzigSomH = true;
						}
					} else
						return som;

				} else
					return som;
			}
		} else {

			for (int kolom = indexKolom + 1; kolom < vakjes.length; kolom++) {
				if (vakjes[index][kolom] != null) {
					if (vakjes[index][kolom].getWaarde() != 0) {
						som += vakjes[index][kolom].getWaarde();
						if (vakjes[index][kolom].equals(vorigVakje) && vorigVakje != eersteVakje) {
							wijzigSomH = true;
						}
					} else
						return som;
				} else
					return som;
			}
		}
		return som;
	}

	/**
	 * registreert een waarde op een vakje
	 * 
	 * @param rijIndex en kolomIndex van een bepaald vakje en waarde geselecteerd
	 *                 steentje
	 */
	public void registreerVakje(int rij, int kolom, int waarde) {
		if (rij == 7 && kolom == 7) {
			eersteVakje = vakjes[7][7];
		} else {
			if (aanVorigVakje == true) {
				controleerWaardeOmringendeVakjesEnBerekenenTotaleWaarde(rij, kolom, waarde);
				huidigVakje = vakjes[rij][kolom];
			} else {

				vorigVakje = huidigVakje;
				huidigVakje = vakjes[rij][kolom];
				controleerWaardeOmringendeVakjesEnBerekenenTotaleWaarde(rij, kolom, waarde);
			}

		}
		vakjes[rij][kolom].setWaarde(waarde);
		aanVorigVakje = false;
	}

	/**
	 * @param rijIndex en kolomIndex van een bepaald vakje
	 * @return waarde van het vakje
	 */
	public int geefWaardeVakje(int rij, int kolom) {

		return vakjes[rij][kolom].getWaarde();
	}

	// reset gebruikte variabelen voor de volgende beurt
	protected void volgendeBeurt() {
		wijzigSomH = false;
		wijzigSomV = false;
		vorigVakje = null;
		huidigVakje = null;
		aanVorigVakje = false;
		scoreHorizontaal = 0;
		scoreVerticaal = 0;
		lijstScore = new ArrayList<>();
	}

	/**
	 * @param rijIndex en kolomIndex van een bepaald vakje
	 * @return boolean ofdat vakje aan het vorige vakje ligt.
	 */
	private boolean controleerEnkelAanVorigeSteen(int rij, int kolom) {
		int teller = 0;
		boolean vorigVakje = false;
		if (Spel.getTotaalBeurt() > 1) {

			// als vakje links gelijk is aan 0 of het vorige vakje
			if (kolom > 0 && vakjes[rij][kolom - 1] != null
					&& (vakjes[rij][kolom - 1].getWaarde() == 0 || vakjes[rij][kolom - 1] == this.vorigVakje)) {
				if (vakjes[rij][kolom - 1] == this.vorigVakje) {
					vorigVakje = true;
				} else {
					teller++;
				}
				// als het vakje links null is moet het tellertje ook verhoogd worden
			}
			if (kolom > 0 && vakjes[rij][kolom - 1] == null)
				teller++;

			// als vakje rechts gelijk is aan 0 of het vorige vakje
			if (kolom < 14 && vakjes[rij][kolom + 1] != null
					&& (vakjes[rij][kolom + 1].getWaarde() == 0 || vakjes[rij][kolom + 1] == this.vorigVakje)) {
				if (vakjes[rij][kolom + 1] == this.vorigVakje) {
					vorigVakje = true;
				} else {
					teller++;
				}
			}
			if (kolom < 14 && vakjes[rij][kolom + 1] == null)
				teller++;

			// als vakje boven gelijk is aan 0 of het vorige vakje
			if (rij > 0 && vakjes[rij - 1][kolom] != null
					&& (vakjes[rij - 1][kolom].getWaarde() == 0 || vakjes[rij - 1][kolom] == this.vorigVakje)) {
				if (vakjes[rij - 1][kolom] == this.vorigVakje) {
					vorigVakje = true;
				} else {
					teller++;
				}
			}
			if (rij > 0 && vakjes[rij - 1][kolom] == null)
				teller++;

			// als vakje onder gelijk is aan 0 of het vorige vakje
			if (rij < 14 && vakjes[rij + 1][kolom] != null
					&& (vakjes[rij + 1][kolom].getWaarde() == 0 || vakjes[rij + 1][kolom] == this.vorigVakje)) {
				if (vakjes[rij + 1][kolom] == this.vorigVakje) {
					vorigVakje = true;
				} else {
					teller++;
				}
			}
			if (rij < 14 && vakjes[rij + 1][kolom] == null)
				teller++;
		}

		if (teller == 3 && vorigVakje == true) {
			return true;
		}

		return false;
	}

	/**
	 * Controleert of het leggen van een steentje op een vakje een geldige som heeft
	 * met de andere vakjes, berekent verticale en horizontale som bij leggen van
	 * het vakje
	 * 
	 * @param rijIndex en kolomIndex van een bepaald vakje en de waarde van het
	 *                 geselecteerde steentje
	 */
	private void controleerWaardeOmringendeVakjesEnBerekenenTotaleWaarde(int rij, int kolom, int waarde) {// methode die
																											// het vakje
		// links en rechts boven
		// controleren
		// som mag maximum 12 zijn
		int somRijen = 0;
		int somKolommen = 0;
		int teller = 0;
		boolean vorigVakje = false;

		if (rij == 7 && kolom == 7) {
			eersteVakje = vakjes[rij][kolom];
		} else {
			if (geefSomKolomOpIndex(rij, kolom, -1) == 0 && geefSomKolomOpIndex(rij, kolom, 1) == 0
					&& geefSomRijOpIndex(rij, kolom, -1) == 0 && geefSomRijOpIndex(rij, kolom, 1) == 0) {
				throw new IllegalArgumentException(ResourceController.getTranslation("NotZero"));
			}
			if (controleerEnkelAanVorigeSteen(rij, kolom) == true) {
				aanVorigVakje = true;
				throw new IllegalArgumentException(ResourceController.getTranslation("PreviousLocation"));

			}
			aanVorigVakje = false;
		}

		somRijen = geefSomRijOpIndex(rij, kolom, 1) + geefSomRijOpIndex(rij, kolom, -1);
		somKolommen = geefSomKolomOpIndex(rij, kolom, 1) + geefSomKolomOpIndex(rij, kolom, -1);

		if (somKolommen + waarde <= 12) {
			if (somRijen + waarde <= 12) {

				if (vakjes[rij][kolom].getKleur() == true) {
					if (somKolommen + waarde < 10 && somRijen + waarde < 10) {
						throw new IllegalArgumentException(ResourceController.getTranslation("GreyBox"));

					}
				}

				if (this.vorigVakje == null) {
					scoreHorizontaal = somRijen + waarde;
					scoreVerticaal = somKolommen + waarde;
				} else {
					bepaalScore(somRijen + waarde, somKolommen + waarde);
				}

			} else {
				throw new IllegalArgumentException(ResourceController.getTranslation("LeftRight"));

			}
		} else {
			throw new IllegalArgumentException(ResourceController.getTranslation("UpUnder"));

		}

	}

	/**
	 * bepaalt de verticale en horizontale som(men) na het leggen van alles
	 * steentjes in een beurt
	 * 
	 * @param score van de rijen en de score van de kolommen van het huidigVakje
	 */
	private void bepaalScore(int scoreX, int scoreY) {
		// indien de score van vorig vakje horizontaal of verticaal gewijzigt is
		if (isWijzigSomH() || isWijzigSomV()) {
			if (isWijzigSomH() && isWijzigSomV()) {
				scoreHorizontaal = scoreX;
				scoreVerticaal = scoreY;
				Collections.addAll(lijstScore, scoreHorizontaal, scoreVerticaal);
				return;

			}
			// als huidigVakje in zelfde kolom als vorig gelegd was, en dus score verandert
			if (isWijzigSomV() && isWijzigSomH() == false) {
				scoreVerticaal = scoreY;

				lijstScore.add(scoreHorizontaal);
				lijstScore.add(scoreX);
				lijstScore.add(scoreVerticaal);

				return;
			}
			// als huidigVakje in zelfde rij als vorig gelegd was, en dus score verandert
			if (isWijzigSomV() == false && isWijzigSomH()) {
				scoreHorizontaal = scoreX;
				lijstScore.add(scoreHorizontaal);

				lijstScore.add(scoreVerticaal);
				lijstScore.add(scoreY);
				return;
			}

		}

		Collections.addAll(lijstScore, scoreVerticaal, scoreHorizontaal, scoreX, scoreY);

	}

	public Vak getHuidigVakje() {
		return huidigVakje;
	}

	public Vak getEersteVakje() {
		return eersteVakje;
	}

	public Vak getVorigVakje() {
		return vorigVakje;
	}

	public boolean isWijzigSomV() {
		return wijzigSomV;
	}

	public boolean isWijzigSomH() {
		return wijzigSomH;
	}

	public List<Integer> getLijstScore() {
		return lijstScore;
	}

	/**
	 * @param rijIndex en kolomIndex van een bepaald vakje
	 * @return boolean dat kleur weergeeft, wit is false, grijs is true
	 */
	public boolean getKleur(int rij, int kolom) {
		return vakjes[rij][kolom].getKleur();
	}

//
}