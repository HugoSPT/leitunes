package domain;

import java.util.Observer;

/**
 * @author antonialopes
 *
 */
public interface IPlayer {
	/**
	 * @param filename
	 * @return Loads the digital song stored in the file with
	 * the filename
	 */
	public boolean load (String filename);
	
	/**
	 * Plays the loaded music
	 */
	public void play ();
	
    /**
     * Pauses the music that is currently playing
     */
    public void still ();
    
    /**
     * Stops the music that is currently playing
     */
    public void stop ();
    
    /**
     * Notifies observers that the song that was playing has 
	* stoped 
     */
    public void hasStopedSong();
 
   /**
     * Notifies observers that the song that was playing has
	* finished
     */
    public void hasEndedSong();
    
    /**
     * Adds o as an observer of the state of this player
     * @param o the observer
     */
    public void addObserver(Observer o);

    /**
     * Delete o as an observer of the state of this player
     * @param o the observer    
     */
    public void	deleteObserver(Observer o); 
}
 
