package domain;

import java.util.Observable;

/**
 *  Classe cujos objectos representam uma playlist normal, 
 *  onde est‹o as mœsicas que l‡ s‹o colocadas.
 *  @authors grupo 12
 */
public class NormalPlaylist extends Playlist {

	/**
	 * Cria um objecto do tipo NormalPlaylist
	 * @param name o nome da playlist
	 * @param library a biblioteca que contem as musicas
	 */
	public NormalPlaylist(String name, MusicLibrary library) {
		super(name, library);
	}

	/**
	 * Processa um evento recebido
	 * @param o o observavel
	 * @param event o evento que ocorreu
	 */
	 @Override
	public void update(Observable o, Object event) {
		if(event instanceof SongRemovedLibraryEvent){
			super.remove(((LibraryEvent) event).getSong());
		}
		else if(event instanceof SongStopedEvent)
			stop();
		else if (event instanceof SongPlayedEvent){
			if(super.isPlaying()){
				super.get(present).incTimesPlayed();
				present++;
				if (present >= super.size())
					present = 0;
				play(super.get(present));
			}
		}
	}
}