package domain;

/**
 * @author fmartins
 *
 * A library event
 */
abstract class LibraryEvent {
	
	/**
	 * The song upon which the event has happened 
	 * and the music library 
	 */
	private Song song;
	private MusicLibrary lib;
	
	
	/**
	 * Constructor
	 * @param song The song upon which the event has 
	 * happened
	 * @param lib The library upon which the event has 
	 * happened
	 */
	LibraryEvent (Song song, MusicLibrary lib) {
		this.song = song;
		this.lib = lib;
	}

	
	/**
	 * @return the song upon which the event has happened
	 */
	public Song getSong() {
		return song;
	}
	
	/**
	 * @return the library upon which the event has happened
	 */
	public MusicLibrary getMusicLibrary() {
		return lib;
	}
}

