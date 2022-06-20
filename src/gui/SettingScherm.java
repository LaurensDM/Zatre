package gui;

import java.util.ArrayList;
import java.util.List;

import listeners.TextFieldNumberOnlyListener;
import main.ZatreGuiStart;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import resources.MusicPlayer;
import resources.Pref;
import resources.ResourceController;

public class SettingScherm extends GridPane {

	private Label lblTitle;
	private ComboBox<String> talen;
	private List<String> stringTalen = new ArrayList<>();
	private ComboBox<String> resolutions;
	private List<String> stringResolutions = new ArrayList<>();
	private Label lblCustomResX;
	private Label lblCustomResY;
	private TextField txfCustomResX;
	private TextField txfCustomResY;
	private Button btnCustomRes;
	private Button themeButton;
	private CheckBox fullScreenBox;
	private CheckBox borderlessBox;
	private Slider backgroundMusicVolume;
	private CheckBox enableBackgroundMusic;
	private Slider soundFXVolume;
	private CheckBox enableSoundFX;
	private ComboBox<String> musicStyles;

	private static String taal = Pref.getPreference("Language");
	public String[] languagePacks = { "TaalPakket_en_EN.properties", "TaalPakket_fr_FR.properties",
			"TaalPakket_nl_NL.properties", "TaalPakket_de_DE.properties" };
	public String[] muziekSoorten = { "house", "jazz" };

	public String muziekkeuze;

	public SettingScherm() {
		// deze methode bouwt de gui op
		buildGui();
	}

	// gui opbouwen
	private void buildGui() {
		// alles in het midden zetten
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setHgap(10);
		this.setVgap(10);

		stringTalen.add("English");
		stringTalen.add("Fran" + new String("\u00E7") + "ais");
		stringTalen.add("Nederlands");
		stringTalen.add("Deutsch");

		lblTitle = new Label(ResourceController.getTranslation("SettingsTitle"));
		lblTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		// Titel in de eerste kolom van de eerste rij plaatsen
		this.add(lblTitle, 0, 0, 2, 1); // <7>

		Label taalLabel = new Label(ResourceController.getTranslation("GoBackLabel"));
		this.add(taalLabel, 0, 2);

		talen = new ComboBox<>();
		talen.setItems(FXCollections.observableArrayList(stringTalen));
		talen.setPromptText(ResourceController.getTranslation("Language"));
		talen.setMaxWidth(900);
		this.add(talen, 1, 2);
		talen.setOnAction(evt -> {
			int index = talen.getSelectionModel().getSelectedIndex();

			if (index == 0) {
				SettingScherm.taal = "TaalPakket_en_EN.properties";
				Pref.changePreference("Language", "TaalPakket_en_EN.properties");
			} else if (index == 1) {
				SettingScherm.taal = "TaalPakket_fr_FR.properties";
				Pref.changePreference("Language", "TaalPakket_fr_FR.properties");
			} else if (index == 2) {
				SettingScherm.taal = "TaalPakket_nl_NL.properties";
				Pref.changePreference("Language", "TaalPakket_nl_NL.properties");
			} else if (index == 3) {
				SettingScherm.taal = "TaalPakket_de_DE.properties";
				Pref.changePreference("Language", "TaalPakket_de_DE.properties");
			}

		});

		// muziek op het scherm met de comboBox
		Label musicLabel = new Label(ResourceController.getTranslation("LabelMusic"));
		this.add(musicLabel, 0, 1);
		musicStyles = new ComboBox<>();
		musicStyles.setItems(FXCollections.observableArrayList(muziekSoorten));
		musicStyles.setPromptText(Pref.getPreference("PlaylistName"));
		musicStyles.setMaxWidth(900);
		this.add(musicStyles, 1, 1);
		System.err.println(musicStyles.getValue());
		musicStyles.setOnAction(evt -> {

			Pref.changePreference("PlaylistName", musicStyles.getValue()); // TODO fix this (gives null)

		});

		stringResolutions.add("3440 x 1440");
		stringResolutions.add("2560 x 1440");
		stringResolutions.add("1920 x 1080");
		stringResolutions.add("1366 x 768");
		stringResolutions.add("500 x 400");
		stringResolutions.add(ResourceController.getTranslation("Custom"));

		Label resolutionLabel = new Label(ResourceController.getTranslation("Resolution"));
		this.add(resolutionLabel, 0, 3);

		resolutions = new ComboBox<>();
		resolutions.setItems(FXCollections.observableArrayList(stringResolutions));
		resolutions.setPromptText(Pref.getPreference("ResolutionX") + " x " + Pref.getPreference("ResolutionY"));
		;
		resolutions.setMaxWidth(900);
		this.add(resolutions, 1, 3);
		resolutions.setOnAction(evt -> {
			int index = resolutions.getSelectionModel().getSelectedIndex();
			if (index == 0) {
				applyResolution(3440, 1415);
				setFalse();
			} else if (index == 1) {
				applyResolution(2560, 1415);
				setFalse();

			} else if (index == 2) {
				applyResolution(1920, 1055);
				setFalse();

			} else if (index == 3) {
				applyResolution(1366, 742);
				setFalse();

			} else if (index == 4) {
				applyResolution(500, 375);
				setFalse();

			} else if (index == 5) {
				System.err.println(ResourceController.getTranslation("ErrorCustomRes"));

				lblCustomResX.setVisible(true);
				txfCustomResX.setVisible(true);
				lblCustomResY.setVisible(true);
				txfCustomResY.setVisible(true);
				btnCustomRes.setVisible(true);
			}

		});

		// TODO create custom input fields for resolution
		lblCustomResX = new Label(ResourceController.getTranslation("Width"));
		txfCustomResX = new TextField();
		txfCustomResX.setMaxWidth(70);
		txfCustomResX.textProperty().addListener(new TextFieldNumberOnlyListener(txfCustomResX));

		lblCustomResY = new Label(ResourceController.getTranslation("Height"));
		txfCustomResY = new TextField();
		txfCustomResY.setMaxWidth(70);
		txfCustomResY.textProperty().addListener(new TextFieldNumberOnlyListener(txfCustomResY));

		btnCustomRes = new Button(ResourceController.getTranslation("Apply"));
		btnCustomRes.setOnMouseClicked(evt -> {
			// Changed: catch if not integer, fixed: user can't enter characters
			applyResolution(Integer.parseInt(txfCustomResX.getText()), Integer.parseInt(txfCustomResY.getText()));
		});

		this.add(lblCustomResX, 0, 5);
		this.add(txfCustomResX, 1, 5);
		this.add(lblCustomResY, 0, 6);
		this.add(txfCustomResY, 1, 6);
		this.add(btnCustomRes, 2, 6);

		lblCustomResX.setVisible(false);
		txfCustomResX.setVisible(false);
		lblCustomResY.setVisible(false);
		txfCustomResY.setVisible(false);
		btnCustomRes.setVisible(false);

		fullScreenBox = new CheckBox((ResourceController.getTranslation("FullScreenEnable")) + " Experimental");
		fullScreenBox.setSelected(Boolean.valueOf(Pref.getPreference("FullScreen"))); // Set checkbox selected if
																						// fullscreen enabeled
		this.add(fullScreenBox, 0, 7);
		fullScreenBox.setDisable(!Boolean.parseBoolean(Pref.getPreference("Experimental")));

		// TODO fullscreen is buggy
		fullScreenBox.setOnAction(evt -> {
			setFullScreen(fullScreenBox.isSelected());
		});

		borderlessBox = new CheckBox((ResourceController.getTranslation("Borderless")) + " Experimental");
		borderlessBox.setSelected(Boolean.valueOf(Pref.getPreference("Borderless")));
		this.add(borderlessBox, 0, 8);
		borderlessBox.setDisable(!Boolean.parseBoolean(Pref.getPreference("Experimental")));

		borderlessBox.setOnAction(evt -> {
			Pref.changePreference("Borderless", String.valueOf(borderlessBox.isSelected()));
//			ScreenController.updateScreenFromSettings();
		});

		CheckBox experimentalFeatures = new CheckBox("Enable experimental features");
		experimentalFeatures.setSelected(Boolean.parseBoolean(Pref.getPreference("Experimental")));
		this.add(experimentalFeatures, 0, 9);
		experimentalFeatures.setOnAction(evt -> {
			Pref.changePreference("Experimental", String.valueOf(experimentalFeatures.isSelected()));
			fullScreenBox.setDisable(!Boolean.parseBoolean(Pref.getPreference("Experimental")));
			borderlessBox.setDisable(!Boolean.parseBoolean(Pref.getPreference("Experimental")));
		});

		enableBackgroundMusic = new CheckBox(ResourceController.getTranslation("CheckBoxBackgroundM"));
		enableBackgroundMusic.setSelected(Boolean.parseBoolean(Pref.getPreference("Music")));
		this.add(enableBackgroundMusic, 0, 10);
		enableBackgroundMusic.setOnAction(evt -> {
			if (enableBackgroundMusic.isSelected()) {
				ResourceController.playSong();
			} else {
				MusicPlayer.stopSong();
			}

			Pref.changePreference("Music", String.valueOf(enableBackgroundMusic.isSelected()));
		});

		backgroundMusicVolume = new Slider();
		backgroundMusicVolume.setValue(Double.parseDouble(Pref.getPreference("MusicVol")));
		this.add(backgroundMusicVolume, 1, 10);
		// If music is disabled in settings this slider will be grayed out
		backgroundMusicVolume.setDisable(!Boolean.parseBoolean(Pref.getPreference("Music")));
		backgroundMusicVolume.setOnMouseReleased(evt -> {
			Pref.changePreference("MusicVol", String.valueOf(backgroundMusicVolume.getValue()));
			ResourceController.changeVolume();
		});

		enableSoundFX = new CheckBox(ResourceController.getTranslation("CheckBoxSoundEff"));
		enableSoundFX.setSelected(Boolean.parseBoolean(Pref.getPreference("SoundFX")));
		this.add(enableSoundFX, 0, 11);
		enableSoundFX.setOnAction(evt -> {
			Pref.changePreference("SoundFX", String.valueOf(enableSoundFX.isSelected()));
		});

		soundFXVolume = new Slider();
		;
		soundFXVolume.setValue(Double.parseDouble(Pref.getPreference("SoundFXVol")));
		this.add(soundFXVolume, 1, 11);
		// If soundFX are disabled in settings this slider will be grayed out
		soundFXVolume.setDisable(!Boolean.parseBoolean(Pref.getPreference("SoundFX")));

		if (Pref.getPreference("Theme").equals("/css/light-theme.css")) {
			themeButton = new Button(ResourceController.getTranslation("DarkThemeEnable"));
		}
		if (Pref.getPreference("Theme").equals("/css/dark-theme.css")) {
			themeButton = new Button(ResourceController.getTranslation("LightThemeEnable"));
		}
		this.add(themeButton, 0, 14);
		themeButton.setOnMouseClicked(evt -> {
			System.err.println(Pref.getPreference("Theme"));
			if (Pref.getPreference("Theme").equals("/css/dark-theme.css")) {
				changeTheme("/css/light-theme.css");
			} else if (Pref.getPreference("Theme").equals("/css/light-theme.css")) {
				changeTheme("/css/dark-theme.css");
			}
		});

		Button applyButton = new Button(ResourceController.getTranslation("Apply"));
		applyButton.setMaxWidth(500);
		this.add(applyButton, 0, 15);
		applyButton.setOnAction(evt -> {
			ResourceController.playSoundFX("lazer");
			ScreenController.setSceneShow(new WelkomScherm(), false, true);
			ScreenController.updateScreenFromSettings();
			Pref.exportPrefToXML();
		});
	}

	private void setFalse()// als custom niet aanstaat toon het ook niet de keuze vakjes
	{
		lblCustomResX.setVisible(false);
		txfCustomResX.setVisible(false);
		lblCustomResY.setVisible(false);
		txfCustomResY.setVisible(false);
		btnCustomRes.setVisible(false);
	}

	private void applyResolution(int x, int y) {
		ZatreGuiStart.getStage().setMaximized(false);
		Pref.changePreference("ResolutionX", Integer.toString(x));
		Pref.changePreference("ResolutionY", Integer.toString(y - 50));
	}

	private void changeTheme(String theme) {
		Pref.changePreference("Theme", theme);

		// quick and dirty way to instantly apply the theme when button is pressed
		ScreenController.setSceneShow(new SettingScherm(), false, true);
	}

	private void setFullScreen(Boolean bool) {
		// TODO not fully working yet
		Pref.changePreference("FullScreen", String.valueOf(bool));

		// quick and dirty way to instantly enable / disable full screen
//		ScreenController.ChangeToSettingsScherm(dc,  stage);
	}

	public static String getTaal() {
		return taal;
	}
}
