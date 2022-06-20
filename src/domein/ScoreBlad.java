package domein;

import java.util.List;

public class ScoreBlad {

	private String[][] scoreblad;
	private int beurt = 0;
	private int totaleScore = 0;

	/**
	 * maakt het scoreblad
	 * 
	 * @param hoeveel spelers er geselecteerd zijn bij het start van het spel
	 */
	protected void maakScoreBlad(int aantalSpelers) {
		scoreblad = new String[(61 / aantalSpelers) + 6][6];
		for (int rij = 0; rij < scoreblad.length; rij++) {
			for (int i = 0; i < 6; i++) {
				scoreblad[rij][i] = "";
			}

		}

		berekenBonus();
	}

	/**
	 * vult het scoreblad aan
	 * 
	 * @param lijst met sommen na een beurt
	 */
	protected void vulScoreBladIn(List<Integer> lijst) {
		for (int i : lijst) {
			if (i == 10) {
				som10();
			}
			if (i == 11) {
				som11();
			}
			if (i == 12) {
				som12();
			}
		}
	}

	/**
	 * @return scoreblad als een 2dimensionele array van String
	 */
	public String[][] geefBlad() {
		return scoreblad;
	}

	public int getTotaleScore() {
		return totaleScore;
	}

	protected void volgendBeurt() {
		beurt++;
	}

	protected void x2score() {
		if (scoreblad[beurt][0] == null || scoreblad[beurt][0].isBlank()) {
			scoreblad[beurt][0] = "X";
		} else {
			int volgendeRij = beurt + 1;
			scoreblad[volgendeRij][0] = "X";
		}

	}

	protected void som10() {
		if (scoreblad[beurt][1] == null || scoreblad[beurt][1].isBlank()) {
			scoreblad[beurt][1] = "X";
		} else {
			scoreblad[beurt][1] += "X";
		}

	}

	protected void som11() {
		if (scoreblad[beurt][2] == null || scoreblad[beurt][2].isBlank()) {
			scoreblad[beurt][2] = "X";
		} else {
			scoreblad[beurt][2] += "X";
		}
	}

	protected void som12() {
		if (scoreblad[beurt][3] == null || scoreblad[beurt][3].isBlank()) {
			scoreblad[beurt][3] = "X";
		} else {
			scoreblad[beurt][3] += "X";
		}
	}

	// vult bonus aan
	private void berekenBonus() {
		int bonus = 3;
		int plus = 4;
		boolean einde = false;
		for (int rij = 0; rij < scoreblad.length; rij += plus) {
			if (scoreblad.length - rij < 4) {
				plus = (scoreblad.length - 1) - rij;
				einde = true;
			}
			for (int teller = rij; teller < rij + plus; teller++) {
				scoreblad[teller][4] = "" + bonus;
			}
			if (einde) {
				return;
			}
			bonus++;

		}
	}

	// berekent totale score
	public void berekenTotaal() {
		for (int rij = 0; rij < scoreblad.length; rij++) {
			int teller = 0;
			int tot = 0;
			int aantal = 0;
			if (scoreblad[rij][1] != null && !scoreblad[rij][1].isBlank()) {
				aantal = scoreblad[rij][1].length();
				tot += (1 * aantal);
				teller++;
			}

			if (scoreblad[rij][2] != null && !scoreblad[rij][2].isBlank()) {
				aantal = scoreblad[rij][2].length();
				tot += (2 * aantal);
				teller++;
			}

			if (scoreblad[rij][3] != null && !scoreblad[rij][3].isBlank()) {
				aantal = scoreblad[rij][3].length();
				tot += (4 * aantal);
				teller++;
			}

			if (teller == 3) {
				tot += Integer.parseInt(scoreblad[rij][4]);
			}

			if (scoreblad[rij][0] != null && !scoreblad[rij][0].isBlank()) {
				tot *= 2;
			}

			scoreblad[rij][5] = "" + tot;
			totaleScore += tot;

		}
		int laatsteRij = scoreblad.length - 1;
		scoreblad[laatsteRij][4] = "Score";
		scoreblad[laatsteRij][5] = "" + totaleScore;
	}
	//
}