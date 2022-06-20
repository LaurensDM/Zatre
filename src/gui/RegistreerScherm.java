package gui;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import listeners.TextFieldNumberOnlyListener;
import main.ZatreGuiStart;
import resources.ResourceController;

public class RegistreerScherm extends GridPane {

	private Label lblMessage;
	private String naam;
	private int geboorteJaar;
	private TextField txfUser;
	private TextField txfGeboorteJaar;

	public RegistreerScherm() {
		// deze methode bouwt de gui op
		buildGui();
	}

	// gui opbouwen
	private void buildGui() {
		// alles links in het midden zetten
		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);

		this.setPadding(new Insets(25, 25, 25, 25));

		Label lblTitle = new Label(ResourceController.getTranslation("LabelTitle"));
		lblTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		// Titel in de eerste kolom van de eerste rij plaatsen
		this.add(lblTitle, 0, 0, 2, 1);

		// label naam in de eerste kolom van de 2de rij plaatsen
		Label lblUserName = new Label(ResourceController.getTranslation("LabelUserName"));
		this.add(lblUserName, 0, 1);

		// invoer veld in de 2de kolom van de 2de rij plaatsen
		txfUser = new TextField();
		txfUser.setPromptText(ResourceController.getTranslation("PromptUserName"));
		txfUser.setFocusTraversable(false);
		this.add(txfUser, 1, 1);

		// label geboorteJaar in de 1ste kolom van de 3de rij plaatsen
		Label lblGeboorteJaar = new Label(ResourceController.getTranslation("LabelYearOfBirth"));
		this.add(lblGeboorteJaar, 0, 2);

		// invoerveld in de 2de kolom van de 3de rij plaatsen
		txfGeboorteJaar = new TextField();
		txfGeboorteJaar.setPromptText(ResourceController.getTranslation("PromptYear"));
		txfGeboorteJaar.setFocusTraversable(false);
		txfGeboorteJaar.textProperty().addListener(new TextFieldNumberOnlyListener(txfGeboorteJaar));
		this.add(txfGeboorteJaar, 1, 2);

		// Bij het hoveren over het invoerveld gedurende +- 2sec verschijnt de tekst
		Tooltip tooltip = new Tooltip();
		tooltip.setText(ResourceController.getTranslation("TooltipText"));
		txfGeboorteJaar.setTooltip(tooltip);

		Button btnRegistreer = new Button(ResourceController.getTranslation("ButtonRegister"));

		// button uiterst links in de 1ste kolom van de 4de rij zetten
		setHalignment(btnRegistreer, HPos.LEFT);
		this.add(btnRegistreer, 0, 3);

		Button btnAnnuleer = new Button(ResourceController.getTranslation("ButtonCancel"));

		// button uiterst rechts in de 2de kolom van de 4de rij zetten
		setHalignment(btnAnnuleer, HPos.RIGHT);
		this.add(btnAnnuleer, 1, 3);

		// onderaan komt tekst tevoorschijn, geeft de speler weer die geregistreerd werd
		lblMessage = new Label();
		this.add(lblMessage, 1, 6);

		btnRegistreer.setOnKeyPressed(evt -> {
			if (evt.getCode() == KeyCode.UP) {
				txfUser.requestFocus();
			}
		});

//		 als men in txtUser zit te typen, bij het drukken van arrow down wordt txfGeboorte geselecteerd
		txfUser.setOnKeyPressed(evt -> {
			if (evt.getCode() == KeyCode.DOWN) {
				txfGeboorteJaar.requestFocus();
			}
		});

		// als men in txfGeboorte zit bij arrow up wordt txfUser geselecteerd
		txfGeboorteJaar.setOnKeyPressed(arg0 -> {
			if (arg0.getCode() == KeyCode.UP) {
				txfUser.requestFocus();
			}
		});

		// bij het drukken op enter wordt de speler geselecteerd
		setOnKeyPressed(evt -> {
			if (evt.getCode() == KeyCode.ENTER) {
				registreer();
			}
		});

		btnRegistreer.setOnAction(evt -> {

			registreer();

		});

		// bij het drukken van de knop gaat men terug naar het WelkomScherm
		btnAnnuleer.setOnAction(evt -> {
			ScreenController.setSceneShow(new SelecteerScherm(), false, false);
		});
	}

	private void registreer() {
		try {
			naam = txfUser.getText();
			geboorteJaar = Integer.parseInt(txfGeboorteJaar.getText());
			ZatreGuiStart.getDc().registreerSpeler(naam, geboorteJaar);
			lblMessage.setText(ZatreGuiStart.getDc().geefSpeler(naam, geboorteJaar));
			txfUser.clear();
			txfGeboorteJaar.clear();
			txfUser.requestFocus();
		} catch (NumberFormatException ne) {
			PopupScherm popup = new PopupScherm(ne.getLocalizedMessage(), PopupScherm.ERROR);
			popup.showAndWait();
		} catch (IllegalArgumentException e) {
			PopupScherm popup = new PopupScherm(e.getLocalizedMessage(), PopupScherm.ERROR);
			popup.showAndWait();
		}
	}

}