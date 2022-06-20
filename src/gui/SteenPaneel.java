package gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import main.ZatreGuiStart;
import resources.ResourceController;

public class SteenPaneel extends VBox {

	private ImageView steen;

	public SteenPaneel() {
		Label titel = new Label(ResourceController.getTranslation("CurrentStoneText"));

		if (ZatreGuiStart.getDc().geefSteen() > 0) {
			Image steenImg = new Image(SteenPaneel.class
					.getResourceAsStream("/resources/images/" + ZatreGuiStart.getDc().geefSteen() + "white.png"));
			steen = new ImageView(steenImg);
		} else {
			Image steenImg = new Image(SteenPaneel.class.getResourceAsStream("/resources/images/white.png"));
			steen = new ImageView(steenImg);
		}
		this.getChildren().addAll(titel, steen);
		this.setAlignment(Pos.CENTER);
	}

	public void update() {
		if (ZatreGuiStart.getDc().geefSteen() > 0) {
			Image steenImg = new Image(SteenPaneel.class
					.getResourceAsStream("/resources/images/" + ZatreGuiStart.getDc().geefSteen() + "white.png"));
			steen = new ImageView(steenImg);
		} else {
			Image steenImg = new Image(SteenPaneel.class.getResourceAsStream("/resources/images/white.png"));
			steen = new ImageView(steenImg);
		}
		this.getChildren().set(1, steen);
	}

	public void clearSteen() {
		Image steenImg = new Image(SteenPaneel.class.getResourceAsStream("/resources/images/white.png"));
		steen = new ImageView(steenImg);
		this.getChildren().set(1, steen);
	}

}