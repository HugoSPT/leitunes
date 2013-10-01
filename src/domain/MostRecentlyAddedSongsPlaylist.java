package domain;

import java.util.Observable;
import java.util.Observer;

/**
 * Classe cujos objectos representam uma playlist onde est‹o as 
 * 5 musicas mais recentemente adicionadas a uma determinada biblioteca.
 * @authors grupo 12
 */
public class MostRecentlyAddedSongsPlaylist extends SmartPlaylist implements
		Observer {
	
	private final int LIMIT = 5;

	/**
	 * Cria um objecto do tipo MostRecentlyAddedSongsPlaylist
	 */
	MostRecentlyAddedSongsPlaylist(MusicLibrary library) {
		super("5 Recently Added", library);
	}

	@Override
	protected void addAutomatic(Song song) {
		if(super.size() == LIMIT)
			super.removeSong(super.get(0));
		super.addSong(song);
	}

	/**
	 * Processa um evento recebido
	 * @param o o observavel
	 * @param event o evento que ocorreu
	 */
	public void update(Observable o, Object event) {
		
		if(event instanceof SongRemovedLibraryEvent)
			super.removeSong(((LibraryEvent) event).getSong());
		else if(event instanceof SongStopedEvent)
			stop();
		else if (event instanceof SongPlayedEvent){
			if(super.isPlaying()){
				super.get(present).incTimesPlayed();
				present++;
				if (present >= super.size())
					present = 0;
				play();
			}
		}
		else if(event instanceof SongAddedLibraryEvent){
			addAutomatic(((LibraryEvent) event).getSong());
		}
	}
	
}
