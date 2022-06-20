package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class SpelScherm extends HBox {
	private ScoreBladScherm scoreBlad;
	private ZatreScherm zatre;
	private SteenPaneel steen;

	public SpelScherm() {

		scoreBlad = new ScoreBladScherm();
		steen = new SteenPaneel();
		zatre = new ZatreScherm(scoreBlad, steen);

		this.getChildren().addAll(scoreBlad, zatre, steen);
		this.setAlignment(Pos.CENTER);
		HBox.setHgrow(zatre, Priority.ALWAYS);
		this.setFillHeight(true);
		HBox.setMargin(steen, new Insets(5, 10, 5, 5));
		HBox.setMargin(scoreBlad, new Insets(5, 5, 5, 10));
	}
}