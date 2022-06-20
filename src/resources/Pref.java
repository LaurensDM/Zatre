package resources;

import java.io.*;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Pref {
    static Preferences pref;
    private static final String[][] updateArray = {
            {"SettingsVersion", "0.5"},
            {"Language", "TaalPakket_en_EN.properties"},
            {"Theme", "/css/light-theme.css"},
            {"ResolutionX", "500"},
            {"ResolutionY", "400"},
            {"FullScreen", "false"},
            {"Borderless", "false"},
            {"Music", "false"},
            {"SoundFX", "true"},
            {"MusicVol", "100"},
            {"SoundFXVol", "100"},
            {"PlaylistName", "house"},
            {"ShortGamemode", "true"},
            {"Experimental", "false"}
            
    };
    private static final Double settingsVersion = Double.parseDouble(updateArray[0][1]);


    public Pref() {
        pref = Preferences.userNodeForPackage(Pref.class);

        try { // Zou in theorie moeten werken
            FileInputStream settingsFileStream = new FileInputStream("src/resources/settings.xml"); //TODO Only works when not compiled to JAR (out)!!!!!!!
//            System.err.println(System.getProperty("user.dir"));

            Preferences.importPreferences(settingsFileStream);

            // If settings are found but the version does not match than
            System.err.println("Local settings version= " + getPreference("SettingsVersion") + " SourceCode settings version sourceCode= " + settingsVersion);
            if (settingsVersion != Double.parseDouble(getPreference("SettingsVersion"))) updateAllSettings();                                                                              ;

        } catch (FileNotFoundException fe) {
            // we maken default settings aan die we later bij sluiten van het spel opslaan! (of overschrijven)
            System.err.println("Settings file was not found creating new one.");
            updateAllSettings();
        } catch (Exception err) {
            // load default settings
            System.err.println("Settings file is not up to date updating");
            updateAllSettings();
        }

    }

    public static void changePreference(String key, String value) {
        pref.put(key, value);
    }

    public static String getPreference(String key) {
        return pref.get(key, "404NotFound");
    }


    /**
     * Will loop over all settings and fill in defaults for missing keys.
     */
    public static void updateAllSettings() {
        System.err.println("Updating settings");
        for (String[] keyValue : updateArray) {
            updatePreference(keyValue[0], keyValue[1]);
        }

        changePreference("SettingsVersion", updateArray[0][1]);
        exportPrefToXML();
    }

    /**
     * Will check a key and its value in settings.xml file, if the key does not exist (it returns 404NotFound)
     * then the key and value will be filled in by default settings.
     * So that it does not override already configured settings!
     * @param key
     * @param value
     */
    public static void updatePreference(String key, String value) {
        // if a setting does not exist for a specific key, then a new key value pair is made.
        String originalSettings = pref.get(key, "404NotFound");
        if (originalSettings.equals("404NotFound")) {
            changePreference(key, value);
            System.err.println("Added settings: " + key + " " + value);
        }
    }

    /**
     * Export partially generated settings to a xml file; so they can be read after a restart.
     * If there are new settings the xml fil will not be overwritten, instead new settings will be appended.
     */
    public static void exportPrefToXML() {
        try {
            FileOutputStream prefOut = new FileOutputStream("src/resources/settings.xml");
            pref.exportSubtree(prefOut);
            prefOut.close();
        } catch (IOException | BackingStoreException ie) {
            ie.printStackTrace();
        }
    }
}
