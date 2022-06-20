package gui;

import domein.ScoreBlad;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import main.ZatreGuiStart;
import resources.ResourceController;

public class ScoreBladScherm extends GridPane {

	private Label lblx2;
	private Label lbl10;
	private Label lbl11;
	private Label lbl12;
	private Label lblBonus;
	private Label lblTot;
	private String[][] scoreblad;
	private Label titel;

	public ScoreBladScherm(ScoreBlad score, String speler) {

		buildGui();
		this.setVisible(true);
		titel.setText(speler);
		scoreblad = score.geefBlad();
		for (int rij = 2; rij < scoreblad.length + 2; rij++) {
			if (rij == scoreblad.length + 1) {
				Label label = new Label(scoreblad[rij - 2][4]);
				Label tot = new Label(scoreblad[rij - 2][5]);

				label.setId("Eindtotaal");
				tot.setId("Eindtotaal");
				this.add(label, 4, rij);
				this.add(tot, 5, rij);
				return;
			}
			for (int kolom = 0; kolom < 6; kolom++) {

				Label label = new Label(scoreblad[rij - 2][kolom]);
				label.setId("tabel");
				this.add(label, kolom, rij);
			}
		}

	}

	public ScoreBladScherm() {

		buildGui();
	}

	private void buildGui() {
		this.setVisible(false);
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setAlignment(Pos.CENTER);
		this.setId("ScoreBlad");

		titel = new Label();
		this.add(titel, 0, 0);
		GridPane.setColumnSpan(titel, 6);
		GridPane.setHalignment(titel, HPos.CENTER);

		lblx2 = new Label("x2");
		lblx2.setId("verhogingsfactor");
		this.add(lblx2, 0, 1);
		lbl10 = new Label("10 ");
		lbl10.setId("verhogingsfactor");
		this.add(lbl10, 1, 1);
		lbl11 = new Label("11 ");
		lbl11.setId("verhogingsfactor");
		this.add(lbl11, 2, 1);
		lbl12 = new Label("12");
		this.add(lbl12, 3, 1);
		lbl12.setId("verhogingsfactor");
		lblBonus = new Label(ResourceController.getTranslation("Bonus"));
		this.add(lblBonus, 4, 1);
		lblBonus.setId("verhogingsfactor");
		lblTot = new Label(ResourceController.getTranslation("Total"));
		this.add(lblTot, 5, 1);
		lblTot.setId("verhogingsfactor");

		this.setAlignment(Pos.CENTER);
	}

	public void update() {
		this.getChildren().clear();
		buildGui();
		this.setVisible(true);
		titel.setText(ZatreGuiStart.getDc().geefHuidigeSpeler());
		titel.setId("titel");
		scoreblad = ZatreGuiStart.getDc().toonScoreBladSpeler();

		for (int rij = 2; rij < scoreblad.length + 1; rij++) {
			for (int kolom = 0; kolom < 6; kolom++) {

				Label label = new Label(scoreblad[rij - 2][kolom]);
				label.setId("tabel");

				this.add(label, kolom, rij);
			}

		}
	}
}