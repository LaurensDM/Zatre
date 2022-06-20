package ui;

import java.util.InputMismatchException;
import java.util.Scanner;

// multi taal support

import domein.DomeinController;
import resources.Pref;
import resources.ResourceController;

public class ZatreApp {
	private DomeinController dc;
	private ResourceController tc;

	private Scanner input = new Scanner(System.in);

	public ZatreApp(DomeinController dc) {
		this.dc = dc;
	}

	public void start() {
		// Keuze tussen selecteren of registreren
		int keuze = 0;
		String naam;
		int geboorteJaar;
		boolean invoerOK = false;
		int choice;
		ResourceController.setTaalPakket("/resources/TaalPakket_nl_NL.properties");

		changeLanguage();
		
		do {

			System.out.println(ResourceController.getTranslation("OptionMessage"));
			keuze = input.nextInt();

			if (keuze == 1) {
				do {
					invoerOK = false;
					try {
						System.out.println(ResourceController.getTranslation("GiveName"));
						naam = input.next();
						System.out.println(ResourceController.getTranslation("GiveBirthYear"));
						geboorteJaar = input.nextInt();
						dc.registreerSpeler(naam, geboorteJaar);
						System.out.println(dc.geefSpeler(naam, geboorteJaar));
						invoerOK = true;
					} catch (InputMismatchException e) {
						System.err.println(ResourceController.getTranslation("ErrGiveNumber"));
						input.nextLine();
					} catch (IllegalArgumentException ie) {
						System.err.println(ie.getLocalizedMessage());
					}

				} while (invoerOK == false);
			}

			if (keuze == 2) {
				do {
					invoerOK = false;
					try {
						System.out.println(ResourceController.getTranslation("GiveNamePlayer"));
						naam = input.next();
						System.out.println(ResourceController.getTranslation("GiveBirthYearPlayer"));
						geboorteJaar = input.nextInt();
						dc.selecteerSpeler(naam, geboorteJaar);
						System.out.println(dc.geefGeselecteerdeSpelers());
						invoerOK = true;
					} catch (InputMismatchException e) {
						System.err.println(ResourceController.getTranslation("ErrGiveNumber"));
					} catch (IllegalArgumentException ie) {
						System.err.println(ie.getLocalizedMessage());
					}
				} while (invoerOK == false);
			}

			if (keuze == 3) {
				changeLanguage();
			}

		} while (keuze <= 3 && keuze > 0);
	}

	private void changeLanguage() {
		int taal = 0;
		do {
			System.out.print(ResourceController.getTranslation("LanguageSelection"));
			try {
				taal = input.nextInt();

			} catch (InputMismatchException ie) {
				System.err.println(ResourceController.getTranslation("ErrGiveNumber"));
				input.nextLine();
			}
			if (taal == 1) {
				ResourceController.setTaalPakket("/resources/TaalPakket_en_EN.properties");
				break;
			}

			else if (taal == 2) {
				ResourceController.setTaalPakket("/resources/TaalPakket_nl_NL.properties");
				break;

			} else if (taal == 3) {
				ResourceController.setTaalPakket("/resources/TaalPakket_fr_FR.properties");
				break;
			} else if (taal == 4) {
				ResourceController.setTaalPakket("/resources/TaalPakket_de_DE.properties");
				break;
			} else if (taal == 5) {
				break;
			}
		} while (taal > 5 || taal < 1);
	}
}