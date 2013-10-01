package domain;

/**
 * @author fmartins
 *
 * A song added library event 
 */

public class SongAddedLibraryEvent extends LibraryEvent {
	
	/**
	 * @param song The song upon which the event has happened 
	 * Constructs a removed song library event.
	 */

	SongAddedLibraryEvent(Song song, MusicLibrary lib) {
		super(song,lib);
	}

}
