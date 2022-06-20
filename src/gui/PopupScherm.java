package gui;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import resources.Pref;
import resources.ResourceController;

public class PopupScherm extends Stage {

	public static final String ERROR = "ERROR";
	public static final String CONFIRM = "CONFIRM";
	public static final String INFORMATION = "INFORMATION";

	public PopupScherm(String content, String welkePopup) {
		GridPane grid = new GridPane();
		Scene scene = new Scene(grid, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		grid.setId("popup");

		if (welkePopup.equals(ERROR)) {
			ResourceController.playSoundFX("error");

			scene.getStylesheets()
					.add(ScreenController.class.getResource(Pref.getPreference("Theme")).toExternalForm());
			this.setScene(scene);

			Label lblError = new Label(ResourceController.getTranslation("ErrorPopUp"));
			lblError.setId("errorLabel");
			grid.add(lblError, 0, 0);
			this.getIcons().add(new Image(PopupScherm.class.getResourceAsStream("/resources/images/errorIcon.png")));
			Image imgError = new Image(PopupScherm.class.getResourceAsStream("/resources/images/errorImage.png"));
			ImageView imgViewError = new ImageView(imgError);
			imgViewError.setFitHeight(40);
			imgViewError.setFitWidth(40);
			GridPane.setHalignment(imgViewError, HPos.CENTER);
			GridPane.setValignment(imgViewError, VPos.CENTER);
			grid.add(imgViewError, 1, 0);

			Label lblContent = new Label(content);
			lblContent.setId("errorLabel");
			grid.add(lblContent, 0, 2);

			Button ok = new Button("OK");
			GridPane.setHalignment(ok, HPos.RIGHT);
			ok.setId("errorOK");
			grid.add(ok, 1, 3);
			ok.setOnAction(evt -> {
				this.close();
			});

		}

		if (welkePopup.equals(CONFIRM)) {
			scene.getStylesheets()
					.add(ScreenController.class.getResource(Pref.getPreference("Theme")).toExternalForm());
			this.setScene(scene);
			Label lblConfirm = new Label("Confirm");
			lblConfirm.setId("errorLabel");
			grid.add(lblConfirm, 0, 0);
			this.getIcons().add(new Image(PopupScherm.class.getResourceAsStream("/resources/images/confirmImage.png")));
			Image imgConfirm = new Image(PopupScherm.class.getResourceAsStream("/resources/images/confirmInfo.png"));
			ImageView imgViewConfirm = new ImageView(imgConfirm);
			imgViewConfirm.setFitHeight(40);
			imgViewConfirm.setFitWidth(40);
			GridPane.setHalignment(imgViewConfirm, HPos.CENTER);
			GridPane.setValignment(imgViewConfirm, VPos.CENTER);
			grid.add(imgViewConfirm, 1, 0);

			Label lblContent = new Label(content);
			lblContent.setId("errorLabel");
			grid.add(lblContent, 0, 2);

			Button ok = new Button("OK");
			GridPane.setHalignment(ok, HPos.CENTER);
			ok.setId("errorOK");
			grid.add(ok, 1, 3);

			Button cancel = new Button("Cancel");
			GridPane.setHalignment(cancel, HPos.CENTER);
			ok.setOnAction(evt -> {
				ScreenController.setSceneShow(new WelkomScherm(), true, false);
				this.close();
			});
			cancel.setOnAction(evt -> {
				this.close();
			});

		}

		if (welkePopup.equals(INFORMATION)) {
			scene.getStylesheets()
					.add(ScreenController.class.getResource(Pref.getPreference("Theme")).toExternalForm());
			this.setScene(scene);
			Label lblInfo = new Label("Info");
			lblInfo.setId("errorLabel");
			grid.add(lblInfo, 0, 0);
			this.getIcons().add(new Image(PopupScherm.class.getResourceAsStream("/resources/images/infoIcon.png")));
			Image imgInfo = new Image(PopupScherm.class.getResourceAsStream("/resources/images/infoImage.png"));
			ImageView imgViewInfo = new ImageView(imgInfo);
			imgViewInfo.setFitHeight(40);
			imgViewInfo.setFitWidth(40);
			GridPane.setHalignment(imgViewInfo, HPos.CENTER);
			GridPane.setValignment(imgViewInfo, VPos.CENTER);
			grid.add(imgViewInfo, 1, 0);

			Label lblContent = new Label(content);
			lblContent.setId("errorLabel");
			grid.add(lblContent, 0, 2);

			Button ok = new Button("OK");
			GridPane.setHalignment(ok, HPos.RIGHT);
			ok.setId("errorOK");
			grid.add(ok, 1, 3);
			ok.setOnAction(evt -> {
				this.close();
			});

		}
	}

}