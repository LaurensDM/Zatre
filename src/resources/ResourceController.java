package resources;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class ResourceController {
	private static MusicPlayer mp = new MusicPlayer();
	private static Properties taalPakket;

	public static void  setTaalPakket(String taalPakketNaam) {
		 Properties fallback = new Properties();
		  fallback.put("key", "default");
		  taalPakket = new Properties(fallback);

		  URL res = ResourceController.class.getResource(taalPakketNaam);
		  if (res == null) throw new UncheckedIOException(new FileNotFoundException(taalPakketNaam));
		  URI uri;
		  try { uri = res.toURI(); }
		  catch (URISyntaxException ex) { throw new IllegalArgumentException(ex); }

		  try (InputStream is = Files.newInputStream(Paths.get(uri))) { taalPakket.load(is); } 
		  catch (IOException ex) { throw new UncheckedIOException("Failed to load resource", ex); }
	}

	public static String getTranslation(String key) {
		return taalPakket.getProperty(key); //TODO veroorzaakt NullPointerException because key is Null uit popupscherm 143
	}
	public static String getCurrentSongName() {
		return mp.getMediaName();
	}

	public static void playSoundFX(String soundFXName) {
		MusicPlayer.playSoundFX(soundFXName); //TODO is this the right way with a static method?
	}

	public static void playSong() {
		mp.playSong();
	}


	public static void musicQuit(Boolean bool) {
		mp.musicQuit(bool);
	}

	public static void stopMediaPlayer() {
		mp.interrupt();
	}

	public static void changeVolume() { //TODO volume does not work yet
		mp.changeVolume();
	}

}