package services;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import domain.IPlayer;


/**
 * @author fmartins
 *
 * An external mp3 Player.
 */
public class JLPlayer extends Player {
   
    private int currentFrame;
    private boolean isPlaying;
    private boolean stop;
    private boolean end;
    private Thread player;
    private final IPlayer iPlayer;

	/**
	 * Constructor
	 * @param stream A stream to a digital song file
	 * @param iPlay The object that knows how to play musics
	 * in digital format
	 */
    public JLPlayer (java.io.InputStream stream, IPlayer iPlay) throws JavaLayerException {
    	super (stream);
    	this.iPlayer = iPlay;
    	
    	player = new Thread() {
            public void run() {
                try { 
                	while (!stop && !end) 
                	  while (isPlaying && !stop && !end) 
                		 if (!decodeFrame ())
                			 end = true;
                		 else 
                			 currentFrame++;
                }
                catch (Exception e) { 
                	System.out.println(e); 
                }
                if (stop) 
                	iPlayer.hasStopedSong();
                else
                	iPlayer.hasEndedSong();
            }
        };
        player.start();
    }
    
  	/**
	 * Plays the loaded music
	 */
  public void play () {
    	isPlaying = true;
    }
    
    /**
     * Pauses the music that is currently playing
     */
    public void still () {
    	isPlaying = false;
    }
    
    /**
     * Stops the music that is currently playing
     */
    public void stop () {
    	stop = true;
    	super.close();
    }
}
