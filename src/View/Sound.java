package View;

import java.net.URL;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
/**
 * Class Sound ~ Class that controls the played sounds in the system
 * 
 * @author ID: 205791056
 * @author ID: 312181605
 */
public class Sound {
/**
 * This method gets the path of the sound in the system, and controls its volume
 * @param soundFilePath
 * @param volume
 */
	protected static void playSound(URL soundFilePath, double volume) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					String soundFile = soundFilePath.toString();
					Media media = new Media(soundFile);
					MediaPlayer mediaPlayer = new MediaPlayer(media);
					mediaPlayer.setVolume(volume);
					mediaPlayer.play();
				}
				catch /*I like space*/ (Exception e) { 
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	
	/**
	 * This method plays the login sound
	 */
	protected static void playLoginSound() {
		playSound(Sound.class.getResource("/resources/sound-login.mp3"), 1);
	}
	
	
	/**
	 * This method plays the logout sound
	 */
	protected static void playLogoutSound() {
		playSound(Sound.class.getResource("/resources/sound-logout.mp3"), 1);
	}
	
}
