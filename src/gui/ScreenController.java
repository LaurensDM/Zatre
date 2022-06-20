package gui;

import domein.DomeinController;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.ZatreGuiStart;
import resources.MusicPlayer;
import resources.Pref;

public class ScreenController {
	private static Scene scene;
	private static double xOffset = 0;
	private static double yOffset = 0;
	private static Stage stageBorderless = new Stage();
	private static ImageView cursorView;
	private static Image cursorImage;
	// grootte van steentjes zijn afhankelijk van grootte van scherm!!!
	private static int resolutionSmall = Math.min(Integer.parseInt(Pref.getPreference("ResolutionX")),
			Integer.parseInt(Pref.getPreference("ResolutionY")));
	public static int stoneSizePx = (int) Math.round(resolutionSmall / 1.5 / 15);
	public static int cursorSizePx = (int) stoneSizePx / 2;

	public static void setSceneShow(Parent parent, boolean clear, boolean applySettings) {
		if (clear) {
			DomeinController.clearStatic();
			ZatreGuiStart.clear();
		}
		try {
			if (applySettings) {
				scene = new Scene(parent, Integer.valueOf(Pref.getPreference("ResolutionX")),
						Integer.valueOf(Pref.getPreference("ResolutionY")));
			} else {
				scene = new Scene(parent, ZatreGuiStart.getStage().getScene().getWidth(),
						ZatreGuiStart.getStage().getScene().getHeight());
			}
//			MediaView mediaview = new MediaView(MusicPlayer.getMediaPlayer());
//			((Parent) scene.getRoot()).getChildren().add(mediaview);
		} catch (NullPointerException ne) {
			ne.printStackTrace();
		}

		scene.getStylesheets().add(ScreenController.class.getResource(Pref.getPreference("Theme")).toExternalForm());

		// TODO improve this!!!! Debug de-ugly it!
		if (Boolean.parseBoolean(Pref.getPreference("Borderless"))) {
			stageBorderless.close();
			stageBorderless = new Stage();
			ZatreGuiStart.getStage().close();
			stageBorderless.setScene(scene);
			stageBorderless.initStyle(StageStyle.TRANSPARENT);
			stageBorderless.setFullScreen(Boolean.parseBoolean(Pref.getPreference("FullScreen")));
			stageBorderless.getIcons()
					.add(new Image(ScreenController.class.getResourceAsStream("/resources/images/icon.png"))); // Possible
																												// that
																												// in
																												// jar
																												// path
																												// has
																												// to be
																												// "resources/images/icon.png"

			stageBorderless.show();
			ZatreGuiStart.getStage().hide();

			// grab your root here
			parent.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					xOffset = event.getSceneX();
					yOffset = event.getSceneY();
				}
			});
			parent.setOnMouseDragged(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					stageBorderless.setX(event.getScreenX() - xOffset);
					stageBorderless.setY(event.getScreenY() - yOffset);
				}
			});
		} else {
			stageBorderless.close();
			ZatreGuiStart.getStage().setTitle("Zatre");
			ZatreGuiStart.getStage().setScene(scene); // TODO zorgt voor setFullScreen() probleem (flash to small and
														// then
														// full screen bug)
			ZatreGuiStart.getStage().setFullScreen(Boolean.parseBoolean(Pref.getPreference("FullScreen")));
			ZatreGuiStart.getStage().getIcons()
					.add(new Image(ScreenController.class.getResourceAsStream("/resources/images/icon.png"))); // Possible
																												// that
																												// in
																												// jar
																												// path
																												// has
																												// to be
																												// "resources/images/icon.png"
			ZatreGuiStart.getStage().show();

		}
	}

	// Changed, ik zou die centeronscreen weglaten, of toch wanneer resolutie niet
	// verandert is
	public static void updateScreenFromSettings() {
		ZatreGuiStart.getStage().centerOnScreen();
	}

	public static void changeCursor() {
		if (Pref.getPreference("Theme").equals("/css/dark-theme.css")) {
			cursorImage = new Image(SteenPaneel.class
					.getResourceAsStream("/resources/images/" + ZatreGuiStart.getDc().geefSteen() + "gray.png"));
			cursorView = new ImageView(cursorImage);
			cursorView.setFitHeight(cursorSizePx);
			cursorView.setFitWidth(cursorSizePx);
		} else if (Pref.getPreference("Theme").equals("/css/light-theme.css")) {
			cursorImage = new Image(SteenPaneel.class
					.getResourceAsStream("/resources/images/" + ZatreGuiStart.getDc().geefSteen() + "white.png"));
			cursorView = new ImageView(cursorImage);
			cursorView.setFitHeight(cursorSizePx);
			cursorView.setFitWidth(cursorSizePx);
		}
		scene.setCursor(new ImageCursor(cursorView.getImage()));
	}

	public static void resetCursor() {
		scene.setCursor(Cursor.DEFAULT);
	}

	public static Stage getStageBorderless() {
		return stageBorderless;
	} // TODO fix borderless

	private static void setStageBorderless(Stage stageBorderless) {
		ScreenController.stageBorderless = stageBorderless;
	}

}
