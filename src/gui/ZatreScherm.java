package gui;

import java.util.List;
import java.util.Optional;

import domein.ScoreBlad;
import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import main.ZatreGuiStart;
import resources.Pref;
import resources.ResourceController;

public class ZatreScherm extends GridPane {

	private Label lblBeurt;
	private Label lblMessage = new Label();
	private int[] array;
	private Button annuleer;
	private Button tip;
	private Button legSteenTerug;
	private int tellerSteentjes = 0;
	private ImageView huidigVakje;
	private Button neemSteentjes;
	private String huidigVakjeURL;
	private final String GRAY;
	private final String WHITE;
	private SteenPaneel steen;
	private ScoreBladScherm scoreblad;
	private boolean steenGelegd = true;
	private boolean darkMode = false;
	private ImageView vakjeTip;

	public ZatreScherm(ScoreBladScherm scoreblad, SteenPaneel steen) {
		// Size per stone wordt berekend op basis van grote van het scherm
		System.err.println("Size per stone= " + ScreenController.stoneSizePx);

		if (Pref.getPreference("Theme").equals("/css/dark-theme.css")) {
			GRAY = "/resources/images/white.png";
			WHITE = "/resources/images/darkGray.png";
			darkMode = true;
		} else {
			GRAY = "/resources/images/gray.png";
			WHITE = "/resources/images/white.png";
			darkMode = false;
		}
		this.scoreblad = scoreblad;
		this.steen = steen;
		buildGui();
	}

	// gui opbouwen
	private void buildGui() {

		this.setAlignment(Pos.CENTER);

		this.setHgap(0);
		this.setVgap(0);
		// titel in het midden zetten
		lblBeurt = new Label(ZatreGuiStart.getDc().aanBeurt());
		lblBeurt.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		setColumnSpan(lblBeurt, 15);
		setRowSpan(lblBeurt, 2);
		GridPane.setHalignment(lblBeurt, HPos.CENTER);
		GridPane.setValignment(lblBeurt, VPos.TOP);
		RowConstraints row = new RowConstraints(50);
		this.getRowConstraints().add(row);
		this.add(lblBeurt, 1, 0);

		annuleer = new Button(ResourceController.getTranslation("Cancel"));
		setColumnSpan(annuleer, 4);
		GridPane.setHalignment(annuleer, HPos.CENTER);
		GridPane.setValignment(annuleer, VPos.TOP);
		this.add(annuleer, 7, 17);
		annuleer.setVisible(false);
		// Spelbord aanmaken
		maakSpelbord();

		// instructie weergeven
		lblMessage.setText(ResourceController.getTranslation("TakeStones"));
		setColumnSpan(lblMessage, 7);
		GridPane.setValignment(lblMessage, VPos.CENTER);
		GridPane.setHalignment(lblMessage, HPos.CENTER);
		this.add(lblMessage, 5, 16);

		// knop om steentjes te nemen
		neemSteentjes = new Button(ResourceController.getTranslation("TakeStones"));
		GridPane.setValignment(neemSteentjes, VPos.TOP);
		GridPane.setHalignment(neemSteentjes, HPos.LEFT);
		setColumnSpan(neemSteentjes, 5);
		this.add(neemSteentjes, 1, 16);

		neemSteentjes.setOnAction(evt -> {
			neemSteentjes.setVisible(false);
			array = null;
			huidigVakjeURL = null;
			annuleer.setVisible(false);
			lblMessage.setText(ResourceController.getTranslation("ChooseRock"));
			try {
				array = ZatreGuiStart.getDc().neemStenen();
			} catch (IllegalArgumentException e) {
				PopupScherm error = new PopupScherm(e.getLocalizedMessage(), "ERROR");
				error.showAndWait();

			}
			int counter = 1;
			// alle steentjes in de array overlopen en weergeven
			if (array != null) {

				for (int i : array) {
					Image stoneImg = new Image(
							ZatreScherm.class.getResourceAsStream("/resources/images/" + i + "white.png"));
					ImageView newStoneFromPot = new ImageView(stoneImg);
					newStoneFromPot.setFitWidth(ScreenController.stoneSizePx);
					newStoneFromPot.setFitHeight(ScreenController.stoneSizePx);
					this.add(newStoneFromPot, counter, 17);
					counter++;

					// als op een steeen gedrukt wordt, wordt deze geselecteerd
					newStoneFromPot.setOnMouseClicked(event2 -> { // origineel steen ipv newStoneFromPot
						if (steenGelegd) {
							steenGelegd = false;
							annuleer.setVisible(true);
							neemSteentjes.setVisible(false);
							huidigVakjeURL = null;
							newStoneFromPot.setVisible(false); // origineel steen ipv newStoneFromPot
							try {
								ZatreGuiStart.getDc().selecteerSteentje(i);
							} catch (IllegalArgumentException e) {
								PopupScherm error = new PopupScherm(e.getLocalizedMessage(), PopupScherm.ERROR);
								error.showAndWait();
							}
							steen.update();

							tellerSteentjes++;
							lblMessage.setText(ResourceController.getTranslation("ChooseBox"));

							// indien men een verkeerde steen heeft gekozen en wilt annuleren

							annuleer.setOnMouseClicked(event1 -> {
								steenGelegd = true;
								steen.clearSteen();
								ScreenController.resetCursor();
								try {
									int rij = getRowIndex(huidigVakje);
									int kolom = getColumnIndex(huidigVakje);
									if (huidigVakjeURL.equals(GRAY)) {
										huidigVakje.getImage();
										ZatreGuiStart.getDc().annuleerLeggenSteentje();
										huidigVakje = new ImageView(
												new Image(ZatreScherm.class.getResourceAsStream(GRAY)));
										huidigVakje.setFitHeight(ScreenController.stoneSizePx);
										huidigVakje.setFitWidth(ScreenController.stoneSizePx);
										this.add(huidigVakje, kolom, rij);

									} else {
										huidigVakje.getImage();
										ZatreGuiStart.getDc().annuleerLeggenSteentje();
										huidigVakje = new ImageView(
												new Image(ZatreScherm.class.getResourceAsStream(WHITE)));
										huidigVakje.setFitHeight(ScreenController.stoneSizePx);
										huidigVakje.setFitWidth(ScreenController.stoneSizePx);
										this.add(huidigVakje, kolom, rij);
									}

									ImageView vakje = huidigVakje;
									String vakjeURL = huidigVakjeURL;
									huidigVakje.setOnMouseClicked(event -> buttonEvent(vakje, vakjeURL));

								} catch (NullPointerException e2) {
									ZatreGuiStart.getDc().clearSteentje();
								}

								annuleer.setVisible(false);
								tip.setVisible(false);
								newStoneFromPot.setVisible(true); // origineel steen ipv newStoneFromPot
								lblMessage.setText(ResourceController.getTranslation("ChooseRock"));
								tellerSteentjes--;
								ScreenController.resetCursor();
							});

							// Verander cursor naar genomen steentje
							ScreenController.changeCursor();
						} else {
//							try {
//								throw new IllegalArgumentException("Je hebt nog een steentje vast");
//							} catch (IllegalArgumentException e) {
//								PopupScherm popup = new PopupScherm(e.getLocalizedMessage(), PopupScherm.ERROR);
//								popup.showAndWait();
//							}

						}
					});

				}

			}
		});

		// leg een steen terug
		legSteenTerug = new Button("Leg steentje terug");
		setColumnSpan(legSteenTerug, 5);
		this.add(legSteenTerug, 14, 17);
		legSteenTerug.setVisible(false);
		legSteenTerug.setOnAction(evt -> {
			ScreenController.resetCursor();
			steenGelegd = true;
			ZatreGuiStart.getDc().legSteentjesTerug();
			legSteenTerug.setVisible(false);
			tip.setVisible(false);
			geefInstructie(ResourceController.getTranslation("ChooseRock"));
			eindeBeurt();
			ZatreGuiStart.getDc().clearSteentje();
		});

		// geef een tip
		tip = new Button("Geef tip");
		setColumnSpan(tip, 5);
		this.add(tip, 14, 16);
		tip.setVisible(false);
		tip.setOnAction(evt -> {

			try {
				steenGelegd = false;
				int[] tip = ZatreGuiStart.getDc().geefRij_en_KolomTip();
				int rijTip = tip[0];
				int kolomTip = tip[1];

				if (rijTip == 0 && kolomTip == 0) {
					geefInstructie("Je kan dit steentje nergens leggen,\n leg het steentje terug");
					legSteenTerug.setVisible(true);
				} else {
					Image vakjeImage;
					vakjeImage = new Image(ZatreScherm.class.getResourceAsStream("/resources/images/yellow.png"));
					vakjeTip = new ImageView(vakjeImage);
					vakjeTip.setFitHeight(ScreenController.stoneSizePx);
					vakjeTip.setFitWidth(ScreenController.stoneSizePx);
					this.add(vakjeTip, kolomTip + 1, rijTip + 1);
					geefInstructie("Je kan een steentje leggen op\nrij " + (rijTip + 1) + " kolom " + (kolomTip + 1));
					vakjeTip.setOnMouseClicked(event -> buttonEvent(vakjeTip, "/resources/images/yellow.png"));
				}
			} catch (NullPointerException e2) {
				geefInstructie("Je kan dit steentje nergens leggen,\n leg het steentje terug");
				legSteenTerug.setVisible(true);
			}

		});

		Button terug = new Button("Terug");
		setColumnSpan(terug, 2);
		// de terug button wordt in de eerste kolom van de eerste rij geplaats
		GridPane.setHalignment(terug, HPos.RIGHT);
		GridPane.setRowSpan(terug, 2);
		terug.setPadding(new Insets(5, 5, 5, 5));
		this.add(terug, 18, 15);

		// als op terug geklikt wordt schakelt het scherm terug over naar het
		// SelecteerScherm
		terug.setOnAction(this::quit);

	}

	private void quit(Event event) {
		PopupScherm popup = new PopupScherm(ResourceController.getTranslation("ConfirmLoseChances"),
				PopupScherm.CONFIRM);
		popup.showAndWait();
	}

	private void geefInstructie(String instructie) {
		lblMessage.setText(instructie);
	}

	private void maakSpelbord() {

		// create row
		for (int rij = 1; rij <= 15; rij++) {
			// create column
			for (int kolom = 1; kolom <= 15; kolom++) {
				// Outer cells
				if (rij == 1 || kolom == 1 || rij == 15 || kolom == 15) {
					// For top and bottom line
					if (rij == 1 || rij == 15) {
						// "standard" field top & bottom
						if (kolom > 4 && kolom < 7)
							createVakje(kolom, rij, "standard");
						// "double" fields on top & bottom
						if (kolom == 7)
							createVakje(kolom, rij, "double");
						// "double" fields on top & bottom
						if (kolom == 9)
							createVakje(kolom, rij, "double");
						// "standard" field top & bottom
						if (kolom > 9 && kolom < 12)
							createVakje(kolom, rij, "standard");
					}

					// For side fields
					if (kolom == 1 || kolom == 15) {
						// "standard" fields side
						if (rij > 4 && rij < 7)
							createVakje(kolom, rij, "standard");
						// "double" fields side
						if (rij == 7)
							createVakje(kolom, rij, "double");
						// "double" fields side
						if (rij == 9)
							createVakje(kolom, rij, "double");
						// "standard" fields side
						if (rij > 9 && rij < 12)
							createVakje(kolom, rij, "standard");
					}

				} else {
					if (rij == kolom)
						createVakje(kolom, rij, "double");
					else {
						if (16 - kolom == rij)
							createVakje(kolom, rij, "double");
						else
							createVakje(kolom, rij, "standard");
					}
				}
			}
		}
	}

	private void buttonEvent(ImageView gameStone, String vakjeURL) {
		int rijVak = getRowIndex(gameStone);
		int kolomVak = getColumnIndex(gameStone);

		boolean invoerOK = true;

		if (array != null) {

			try {

				ZatreGuiStart.getDc().registreerWaardeVakje(rijVak - 1, kolomVak - 1);
				String i = ZatreGuiStart.getDc().geefWaardeVakje(rijVak - 1, kolomVak - 1);
				steen.clearSteen();
				steenGelegd = true;
				if (vakjeTip != null) {
					int vakRijTip = getRowIndex(vakjeTip) - 1;
					int vakKolomTip = getColumnIndex(vakjeTip) - 1;
					boolean vakKleurTip = ZatreGuiStart.getDc().getKleurVakje(vakRijTip, vakKolomTip);
					if (ZatreGuiStart.getDc().geefWaardeVakje(vakRijTip, vakKolomTip).equals("0")) {
						if (vakKleurTip) {
							vakjeTip.setImage(new Image(ZatreScherm.class.getResourceAsStream(GRAY)));
						} else {
							vakjeTip.setImage(new Image(ZatreScherm.class.getResourceAsStream(WHITE)));
						}

					}

				}

				Image vakjeImage;
				huidigVakjeURL = vakjeURL;
				huidigVakje = gameStone;
				// we zorgen dat wanneer de speler een gekleurd vakje aanklikt, het gelegde
				// steentje de kleur aan neemt van dat vakje
				ZatreGuiStart.getDc().clearSteentje();
				if (vakjeURL.equals(GRAY)) {
					if (darkMode) {
						vakjeImage = new Image(
								ZatreScherm.class.getResourceAsStream("/resources/images/" + i + "white.png"));
					} else {
						vakjeImage = new Image(
								ZatreScherm.class.getResourceAsStream("/resources/images/" + i + "gray.png"));
					}

				} else {
					if (darkMode) {
						vakjeImage = new Image(
								ZatreScherm.class.getResourceAsStream("/resources/images/" + i + "gray.png"));
					} else {
						vakjeImage = new Image(
								ZatreScherm.class.getResourceAsStream("/resources/images/" + i + "white.png"));

					}
				}
				gameStone = new ImageView(vakjeImage);
				gameStone.setFitHeight(ScreenController.stoneSizePx);
				gameStone.setFitWidth(ScreenController.stoneSizePx);
				this.add(gameStone, kolomVak, rijVak);
//				ResourceController.layStoneSoundFX();
				invoerOK = true;

				// cursor terug naar default
				ScreenController.resetCursor();
			} catch (IllegalArgumentException e) {
				PopupScherm error = new PopupScherm(e.getLocalizedMessage(), "ERROR");
				error.showAndWait();
				tip.setVisible(true);
				invoerOK = false;
			} catch (NullPointerException ne) {
				PopupScherm error = new PopupScherm(ne.getLocalizedMessage(), PopupScherm.ERROR);
				error.showAndWait();
			}

			geefInstructie(ResourceController.getTranslation("ChooseRock"));
		}

		if (invoerOK) {
			tip.setVisible(false);
			eindeBeurt();

		}
	}

	private void eindeBeurt() {
		try {
//			 als alle stenen weg zijn is de beurt aan de volgende speler.
			if (tellerSteentjes == array.length) {
				tellerSteentjes = 0;
				ZatreGuiStart.getDc().vulScoreBladAan();
				scoreblad.update();
				lblBeurt.setText(ZatreGuiStart.getDc().aanBeurt());
				geefInstructie(ResourceController.getTranslation("TakeStones"));
				neemSteentjes.setVisible(true);
				annuleer.setVisible(false);
			}

		} catch (IllegalArgumentException e) {
			PopupScherm error = new PopupScherm(e.getLocalizedMessage(), PopupScherm.INFORMATION);
			error.showAndWait();
			ScreenController.setSceneShow(new WinnaarScherm(), false, false);
		} catch (NullPointerException ne) {
			PopupScherm error = new PopupScherm(ResourceController.getTranslation("ChooseRockBeforeBox"),
					PopupScherm.ERROR);
			error.showAndWait();

		}
	}

	private void createVakje(int kolom, int rij, String stoneValue) {

		// kleuren van gamestones moeten anders zijn in dark theme
		if (darkMode) {
			if (stoneValue.equals("standard"))
				stoneValue = "darkGray";
			else if (stoneValue.equals("double"))
				stoneValue = "white";
		} else {
			if (stoneValue.equals("standard"))
				stoneValue = "white";
			else if (stoneValue.equals("double"))
				stoneValue = "gray";
		}
		String vakjeURL = "/resources/images/" + stoneValue + ".png";
		Image vakjeImage = new Image(ZatreScherm.class.getResourceAsStream("/resources/images/" + stoneValue + ".png"));
		ImageView vakje = new ImageView(vakjeImage);
		vakje.setFitHeight(ScreenController.stoneSizePx);
		vakje.setFitWidth(ScreenController.stoneSizePx);
		vakje.setOnMouseClicked(evt -> buttonEvent(vakje, vakjeURL));
		this.add(vakje, kolom, rij);
	}
}