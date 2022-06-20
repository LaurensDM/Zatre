package gui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import resources.MusicPlayer;
import resources.Pref;
import resources.ResourceController;

public class WelkomScherm extends VBox {

	private Label lblWelkom;
	private Button settings;
	private Button play;
	private Button quit;

	public WelkomScherm() {

//		ResourceController.playMedia("all"); 
		if (MusicPlayer.currentlyPlaying == false)
			ResourceController.playSong();
//		ResourceController.stopAndPlayMusic(null);
		buildGui();

	}

	// opbouwen van de gui
	private void buildGui() {
		// zorgen dat alles netjes in het midden staat

		setTaal(SettingScherm.getTaal());
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(20, 20, 20, 20));
		this.setSpacing(50);

		Label lblMusic = new Label("Currently playing = " + ResourceController.getCurrentSongName());
		Button skipMusic = new Button("Next song"); // TODO translate this
		Button stopMusic = new Button("Stop song"); // TODO translate this

		HBox hbox1 = new HBox();
		hbox1.getChildren().add(lblMusic);
		hbox1.setAlignment(Pos.TOP_LEFT);

		HBox hbox2 = new HBox();
		hbox2.getChildren().add(skipMusic);
		hbox2.getChildren().add(stopMusic);
		hbox2.setAlignment(Pos.TOP_LEFT);

		if (Boolean.parseBoolean(Pref.getPreference("Music"))) {
			this.getChildren().addAll(hbox1, hbox2);
		}

		lblWelkom = new Label(ResourceController.getTranslation("Welcome"));
		lblWelkom.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		play = new Button(ResourceController.getTranslation("Play"));
		play.setMaxWidth(500);

		settings = new Button(ResourceController.getTranslation("Setting"));
		settings.setMaxWidth(500);

		quit = new Button(ResourceController.getTranslation("Quit"));

		// enkele button events
		play.setOnKeyPressed(evt -> {
			if (evt.getCode() == KeyCode.ENTER) {
				ScreenController.setSceneShow(new SelecteerScherm(), false, false);
			}

			if (evt.getCode() == KeyCode.SPACE) {
				ScreenController.setSceneShow(new SettingScherm(), false, false);
			}

			if (evt.getCode() == KeyCode.BACK_SPACE) {
				Platform.exit();
			}

			if (evt.getCode() == KeyCode.DOWN) {
				settings.requestFocus();
			}
		});

		settings.setOnKeyPressed(evt -> {
			if (evt.getCode() == KeyCode.ENTER) {
				ScreenController.setSceneShow(new SettingScherm(), false, false);
			}

			if (evt.getCode() == KeyCode.UP) {
				play.requestFocus();
			}

			if (evt.getCode() == KeyCode.DOWN) {
				quit.requestFocus();
			}

		});

		quit.setOnKeyPressed(evt -> {
			if (evt.getCode() == KeyCode.ENTER) {
				quit();
			}
			if (evt.getCode() == KeyCode.UP) {
				settings.requestFocus();
			}
		});

		play.setOnMouseClicked(evt -> {
			ResourceController.playSoundFX("lazer");
			ScreenController.setSceneShow(new SelecteerScherm(), false, false);
		});

		settings.setOnMouseClicked(evt -> {
			ResourceController.playSoundFX("lazer");
			ScreenController.setSceneShow(new SettingScherm(), false, false);
		});

		quit.setOnMouseClicked(evt -> {
			ResourceController.playSoundFX("lazer");
			quit();
		});

		skipMusic.setOnMouseClicked(evt -> {
			ResourceController.playSoundFX("lazer");
			MusicPlayer.skipSong();
			lblMusic.setText("Currently playing = " + ResourceController.getCurrentSongName()); // TODO translate this
		});

		stopMusic.setOnMouseClicked(evt -> {
			MusicPlayer.stopSong();
			lblMusic.setText("Currently not playing any music");
		});

		// alles weergeven
		this.getChildren().addAll(lblWelkom, play, settings, quit);
	}

	public void setTaal(String pakket) {
		ResourceController.setTaalPakket(pakket);
	}

	public static void quit() {
		System.exit(0);
	}
}
