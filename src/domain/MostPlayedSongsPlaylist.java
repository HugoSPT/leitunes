package domain;

import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

/**
 * Classe cujos objectos representam listas com as 25 musicas
 * mais tocadas
 * @author grupo 12
 */
public class MostPlayedSongsPlaylist extends SmartPlaylist implements Observer {
	
	private final int LIMIT = 25;
	
	/**
	 * Cria um objecto do tipo MostPlayedSongsPlaylist
	 */
	public MostPlayedSongsPlaylist(MusicLibrary library){
		super("25 Most Played",library);
		super.ml=library;
	}
	
	@Override
	protected void addAutomatic(Song song) {
		updateList();
	}
	
	/**
	 * Em cada musica tocada ou adicionada, e feito um update 
	 * a lista das 25 musicas mais tocadas
	 */
	private void updateList(){
		
		Song[] aux = new Song[ml.size()];
		
		for(int i = 0; i < ml.size(); i++)
			aux[i] = ml.get(i);
		
		Arrays.sort(aux, new SICTimesPlayedDec().getCriteriaComparator());
		
		for(int i  = 0; i < LIMIT; i++){
			
			if(i < aux.length && aux[i].getTimesPlayed() > 0)
				if(super.has(aux[i])){
					super.remove(aux[i]);
					super.addSong(aux[i]);
				}
				else{
					super.addSong(aux[i]);
				}
		}
	}
	
	/**
	 * Processa um evento recebido
	 * @param o o observavel
	 * @param event o evento que aconteceu
	 */
	@Override
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
			updateList();
		}
	}

}
