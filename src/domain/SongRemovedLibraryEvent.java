package domain;

/**
 * @author fmartins
 *
 * A song removed event
 */
public class SongRemovedLibraryEvent extends LibraryEvent {

	/**
	 * @param song The song upon which the event has happened
	 * @param lib The library upon which the event has
	 * happened	  
	 * Constructs a song removed library event.
	 */
	SongRemovedLibraryEvent(Song song, MusicLibrary lib) {
		super(song,lib);
	}

}
