package resources;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.SecureRandom;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MusicPlayer extends Thread { // TODO use extends Runnable maybe

	// TODO Static variables are not "thread safe"...

	public static boolean currentlyPlaying = false;
	private static int pausedOnFrame = 0;
	private static String mediaName;
	private static String lastSong;
	private static MediaPlayer APlayer;
	private static SecureRandom sr = new SecureRandom();
	private static int position; // TODO get location of player
	private static BufferedInputStream soundFXBuffer;
	private static String[] playList = {};
	private static String muziekKeuze;
	private static AudioClip soundFX;
	private static String[] playListHouse = { "Slow And Steady", "Dance With me", "Sthlm Sunset", "Palm Trees" };
	private static String[] playListJazz = { "Raindrop", "Taxi Driver" };

	public MusicPlayer() {
//        try {
//
//        } catch (JavaLayerException je) {
//            je.printStackTrace();
//        } catch (FileNotFoundException fe) {
//            fe.printStackTrace();
//        }
	}

	public static MediaPlayer getMediaPlayer() {
		return APlayer;
	}

	private static void bepaalMuziekKeuze() {
		muziekKeuze = Pref.getPreference("PlaylistName");
		if (Pref.getPreference("PlaylistName").equals("house"))
			playList = playListHouse;
		else if (Pref.getPreference("PlaylistName").equals("jazz"))
			playList = playListJazz;
		else
			playList = playListJazz;
	}

	/**
	 * The play() method starts the thread; this thread plays music. When the play
	 * method is called again the song wil be skipped
	 */
	public static void playSong() {
		currentlyPlaying = true;
		bepaalMuziekKeuze();
		shuffleRandomSongFromPlaylist();

		APlayer = new MediaPlayer(new Media(new File("src/resources/audio/" + mediaName + ".mp3").toURI().toString()));
		APlayer.setVolume((Double.parseDouble(Pref.getPreference("MusicVol"))) / 100);

		APlayer.setOnEndOfMedia(new Runnable() {

			@Override
			public void run() {
				APlayer.seek(Duration.ZERO);

			}
		});

		APlayer.play();
	}

	/**
	 * Skipps a song and
	 */
	public static void skipSong() {
		if (Boolean.parseBoolean(Pref.getPreference("Music")) != true)
			Pref.changePreference("Music", "true");
		if (APlayer != null)
			APlayer.stop();
		ResourceController.playSong();
	}

	public static void stopSong() {
		if (APlayer != null)
			APlayer.stop();
		Pref.changePreference("Music", "false");
	}

	// TODO fix this
//    public static void StopSong() {
//        APlayer.getPlayBackListener().playbackFinished(createEvent(PlaybackEvent.STOPPED));
//    }

	/**
	 * Will shuffle and look for a next song to play until the song is different
	 * from last song that was played.
	 */
	public static void shuffleRandomSongFromPlaylist() {
		if (mediaName != null)
			lastSong = mediaName;
		do {
			mediaName = playList[sr.nextInt(playList.length)];
		} while (mediaName == lastSong);
		System.err.println("Currently playing= " + mediaName);
	}

	/**
	 * Runs the thread that is started in the play() method.
	 *
	 */

	public String getMediaName() {
		return mediaName;
	}

	public static void musicQuit(Boolean bool) {
		APlayer.stop();
	}

	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}

	public static void playSoundFX(String soundFXName) {
		soundFX = new AudioClip(new File("src/resources/audio/" + soundFXName + ".mp3").toURI().toString());
		soundFX.play();
	}

	public static void changeVolume() {
		APlayer.setVolume((Double.parseDouble(Pref.getPreference("MusicVol"))) / 100);
		System.err.println(APlayer.getVolume());
	}
}
