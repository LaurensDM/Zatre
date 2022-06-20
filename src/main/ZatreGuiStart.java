package main;

import java.util.Scanner;

import domein.DomeinController;
import gui.ScreenController;
import gui.WelkomScherm;
import javafx.application.Application;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import resources.Pref;
import resources.ResourceController;
import ui.ZatreApp;

public class ZatreGuiStart extends Application {
	private static Stage stage;
	private static DomeinController dc;

	// TODO make stage private!!!
	public static Stage getStage() {
		return stage;
	}

	public static DomeinController getDc() {
		return dc;
	}

	public static void clear() {
		ZatreGuiStart.dc = new DomeinController();
	}

	@Override
	public void start(Stage stage) {
		ZatreGuiStart.stage = stage;

		stage.setOnCloseRequest(evt -> WelkomScherm.quit()); // Nice!
		try {
			dc = new DomeinController();
			Pref pref = new Pref();
			ResourceController.playSong();
			ScreenController.setSceneShow(new WelkomScherm(), false, true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int keuze;
		do {

			System.out.println("ui: 0  gui: 1");
			keuze = input.nextInt();
		} while (keuze > 1 || keuze < 0);
		if (keuze == 0) {
			new ZatreApp(new DomeinController()).start();
		}
		if (keuze == 1) {
			launch(args);
		}

		input.close();
	}

}