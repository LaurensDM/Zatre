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
import resources.Pref;
import resources.ResourceController;

public class SelecteerScherm extends GridPane {

	private Label lblMessage;
	private TextField txfUser;
	private TextField txfGeboorteJaar;
	private String naam;
	private int geboorteJaar;

	public SelecteerScherm() {
		// deze methode bouwt de gui op
		buildGui();
	}

	// gui opbouwen
	private void buildGui() {
		// telkens bij de start van het selecteerscherm moet de lijst met geselecteerde
		// spelers leeg zijn
		ZatreGuiStart.getDc().clearSpelers();
		// alles links in het midden zetten
		this.setAlignment(Pos.CENTER); // original CENTER_LEFT
		this.setHgap(10);
		this.setVgap(10);

		this.setPadding(new Insets(25, 25, 25, 25));

		Label lblTitle = new Label(ResourceController.getTranslation("SelectTitle"));
		lblTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		// Titel in de eerste kolom van de eerste rij plaatsen
		this.add(lblTitle, 0, 0, 2, 1);

		// label naam in de eerste kolom van de 2de rij plaatsen
		Label lblUserName = new Label(ResourceController.getTranslation("LabelUserName"));
		this.add(lblUserName, 0, 1);

		// invoer veld in de 2de kolom van de 2de rij plaatsen
		txfUser = new TextField();
		txfUser.setFocusTraversable(false);
		txfUser.setPromptText(ResourceController.getTranslation("PromptUserName"));
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

		Button btnSelecteer = new Button(ResourceController.getTranslation("SelectButton"));

		// button uiterst links in de 1ste kolom van de 4de rij zetten
		setHalignment(btnSelecteer, HPos.LEFT);
		this.add(btnSelecteer, 0, 3);

		Button btnAnnuleer = new Button(ResourceController.getTranslation("CancelButton"));
		// button uiterst rechts in de 2de kolom van de 4de rij zetten
		setHalignment(btnAnnuleer, HPos.RIGHT);
		this.add(btnAnnuleer, 1, 3);

		// Hyperlink inde 1ste kolom van de 6de rij zetten
		Hyperlink linkRegistreer = new Hyperlink(ResourceController.getTranslation("RegistrerLink"));
		this.add(linkRegistreer, 0, 5, 2, 1);

		CheckBox shortGamemode = new CheckBox();
		shortGamemode.setText("Short gamemode (19 stones instead of 121)"); // TODO translate this
		shortGamemode.setSelected(Boolean.parseBoolean(Pref.getPreference("ShortGamemode")));

		shortGamemode.setOnAction(evt -> {
			Pref.changePreference("ShortGamemode", String.valueOf(shortGamemode.isSelected()));
		});
		this.add(shortGamemode, 0, 4);

		// button in de 1ste kolom van de 5de rij zetten
		Button speel = new Button(ResourceController.getTranslation("PlayButton"));

		// de button neemt 2 kolommen in beslag
		setColumnSpan(speel, 2);
		speel.setMaxWidth(REMAINING);
		this.add(speel, 0, 6);

		// onderaan komt tekst tevoorschijn naar gelang op welke button gedrukt wordt,
		// kan gebruikt worden voor errors
		lblMessage = new Label();
		this.add(lblMessage, 1, 7);

		// op het opstarten van het scherm is btnSelecteer automatisch geselecteerd, als
		// men dan op spatie drukt wordt txfUser geselecteerd
		btnSelecteer.setOnKeyPressed(evt -> {
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

		// als men in txfGeboorte zit, bij drukken op arrow down wordt de speler
		// geselecteerd, bij arrow up wordt txfUser geselecteerd
		txfGeboorteJaar.setOnKeyPressed(arg0 -> {
			if (arg0.getCode() == KeyCode.DOWN) {
				selecteer();
			}

			if (arg0.getCode() == KeyCode.UP) {
				txfUser.requestFocus();
			}
		});

		// bij het drukken op enter wordt het spel gestart
		setOnKeyPressed(evt -> {
			if (evt.getCode() == KeyCode.ENTER) {
				speel();
			}
		});

		btnSelecteer.setOnAction(evt -> {
			ResourceController.playSoundFX("lazer");
			selecteer();
		});

		// bij het drukken van de knop gaat men terug naar het WelkomScherm
		btnAnnuleer.setOnAction(evt -> {
			ResourceController.playSoundFX("lazer");
			ScreenController.setSceneShow(new WelkomScherm(), true, false);
		});

		linkRegistreer.setOnAction(evt -> {
			ResourceController.playSoundFX("lazer");
			ScreenController.setSceneShow(new RegistreerScherm(), false, false);
		});

		// Bij het drukken van de knop gaat men naar het ZatreScherm
		speel.setOnAction(evt -> {
			ResourceController.playSoundFX("lazer");
			speel();
		});

	}

	private void selecteer() {
		try {
			naam = txfUser.getText();
			geboorteJaar = Integer.parseInt(txfGeboorteJaar.getText());
			ZatreGuiStart.getDc().selecteerSpeler(naam, geboorteJaar);
			lblMessage.setText(ZatreGuiStart.getDc().geefGeselecteerdeSpelers());
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

	private void speel() {
		try {
			ZatreGuiStart.getDc().startNieuwSpel();
			ScreenController.setSceneShow(new SpelScherm(), false, false);
		} catch (IllegalArgumentException ie) {
			PopupScherm error = new PopupScherm(ie.getLocalizedMessage(), PopupScherm.ERROR);
			error.showAndWait();
		}
	}
}