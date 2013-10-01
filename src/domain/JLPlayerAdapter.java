package domain;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Observable;


import services.JLPlayer;

/**
 * @author fmartins
 *
 * Adapter for the external mp3 player.
 * This is a singleton class.
 */
public final class JLPlayerAdapter extends Observable implements IPlayer {

	/**
	 * The singleton player
	 */
	private static JLPlayerAdapter instance;
	
	/**
	 * The reference for the external mp3Player
	 */
	private JLPlayer mp3Player;
	
	/**
	 * Private constructor for implementing the singleton pattern
	 */
	private JLPlayerAdapter () {
	}
	
	/**
	 * @return the singleton player
	 * 
	 * Creates a player on demand 
	 */
	public static IPlayer getInstance () {
		if (instance == null)
			instance = new JLPlayerAdapter ();
		return instance;
	}
	
	/**
	 * @param filename The name of the file containing the
	 * digital song to be loaded
	 * @return Loads the digital song stored in the file 
	 * with the filename
	 */
	public boolean load(String filename) {
    	try {
    		stop();
			mp3Player = new JLPlayer(new BufferedInputStream(new FileInputStream(filename)), this);
		} catch (Exception e) {
			System.out.println("nao carregou musica " + filename);
			return false;
		} 
		return true;
	}

	/**
	 * Plays the loaded music
	 */
	public void play() {
		if (mp3Player != null)
			mp3Player.play();
	}

    /**
     * Pauses the music that is currently playing
     */
	public void still() {
		if (mp3Player != null)
			mp3Player.still();
	}

    /**
     * Stops the music that is currently playing
     */
	public void stop() {
		if (mp3Player != null)
			mp3Player.stop();
	}

   /**
     * Notifies observers that the song that was playing has
	* finished
     */
	public void hasEndedSong() {
		setChanged();
		notifyObservers (new SongPlayedEvent ());
	}

    /**
     * Notifies observers that the song that was playing has
	* stoped 
     */
	public void hasStopedSong() {
		setChanged();
		notifyObservers (new SongStopedEvent ());
	}

}
