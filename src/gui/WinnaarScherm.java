package gui;

import domein.ScoreBlad;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import main.ZatreGuiStart;

public class WinnaarScherm extends VBox {

	private Label winnaar;
	private Button ok = new Button("OK");

	public WinnaarScherm() {
		this.setMinHeight(USE_PREF_SIZE);
		this.setPrefHeight(USE_PREF_SIZE);
		this.setSpacing(10);
		this.setAlignment(Pos.CENTER);
		int teller = 0;
		HBox hbox = new HBox();
		winnaar = new Label(ZatreGuiStart.getDc().geefWinnaar());
		for (ScoreBlad score : ZatreGuiStart.getDc().toonAlleScoreBladen()) {
			String speler = ZatreGuiStart.getDc().geefHuidigeSpelers().get(teller);
			ScoreBladScherm scoreblad = new ScoreBladScherm(score, speler);

			hbox.getChildren().add(scoreblad);
			teller++;
		}
		hbox.setAlignment(Pos.CENTER);

		this.getChildren().addAll(winnaar, hbox, ok);

		ok.setOnMouseClicked(evt -> {
			ScreenController.setSceneShow(new WelkomScherm(), true, false);
		});
	}
}