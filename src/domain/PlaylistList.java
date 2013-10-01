package domain;

import java.util.ArrayList;
import java.util.List;

import adts.AbsQListWithSelection;
import adts.QListWithSelection;

/**
 * Classe cujos objectos representam listas de Playlists
 * @author Grupo 12
 */
class PlaylistList extends AbsQListWithSelection<Playlist> implements QListWithSelection<Playlist>{
	
	/**
	 * Cria uma lista de playlistas de uma biblioteca escolhida ja com
	 * 2 smartplaylists
	 * @param library a biblioteca escolhida
	 */
	PlaylistList(MusicLibrary library){
		super.add(new MostPlayedSongsPlaylist(library));
		super.add(new MostRecentlyAddedSongsPlaylist(library));
	}

	@Override
	public List<Playlist> listInit() {
		return new ArrayList<Playlist>();
	}
	
	/**
	 * Incramenta o rate da musica seleccionada na playlist selecionada
	 * @requires someSelected();
	 */
	void incRateSelected() {
		super.getSelected().incRateSelected();
	}
	
	/**
	 * Decrementa o rate da musica seleccionada na playlist selecionada
	 * @requires someSelected();
	 */
	void decRateSelected() {
		super.getSelected().decRateSelected();
	}
	
	/**
	 * Toca a musica seleccionada da playlist seleccionada
	 * @requires someSelected();
	 */
	void play(){
		super.getSelected().play();
	}
	
	/**
	 * Para a musica seleccionada da playlist seleccionada
	 * @requires someSelected();
	 */
	void stop(){
		super.getSelected().stop();
	}
	
	/**
	 * Indica se a playlist sleccionada esta a tocar
	 * @requires someSelected();
	 * @return se a playlist seleccionada esta a tocar
	 */
	boolean isPlaying(){
		return super.getSelected().isPlaying();
	}
}
