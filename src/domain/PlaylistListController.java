package domain;

/**
 *  Classe cujos objectos representam um controlador de uma 
 *  PlaylistList e da interacao do exterior com este.
 *  @authors Grupo 12
 */
public class PlaylistListController {

	private PlaylistList playlists;
	private MusicLibrary ml;
	
	/**
	 * Cria 1 objecto do tipo PlaylistListController
	 * @param playlists a PlaylistList
	 * @param library a biblioteca associada a PlaylistList
	 */
	public PlaylistListController(PlaylistList playlists, MusicLibrary library){
		this.playlists = playlists;
		ml = library;
	}
	
	/**
	 * Cria uma playlist vazia e adiciona-a a PlaylistList
	 * @param name o nome da playlist a adicionar
	 */
	public void createPlaylist(String name) {
		playlists.add(new NormalPlaylist(name, this.ml));
		playlists.select(playlists.size()-1);
	}
	
	/**
	 * Selecciona uma playlist
	 * @param i a playlist a seleccionar
	 */
	public void selectPlaylist(int i) {
		playlists.select(i);
	}
	
	/**
	 * Indica se esta alguma playlist seleccionada
	 * @return se esta alguma playlist seleccionada
	 */
	public boolean somePlaylistSelected() {
		return playlists.someSelected();
	}
	
	/**
	 * Remove uma playlist da lista de playlists
	 */
	public void removePlaylist(){
		playlists.remove();
	}
	
	/**
	 * Devolve a playlist seleccionada
	 * @return a playlist seleccionada
	 * @requires somePlaylistSelected();
	 */
	public Playlist getSelectedPlaylist() {
		return playlists.getSelected();
	}
	
	/**
	 * Devolve 1 iterador de playlists
	 * @return 1 iterador de playlists
	 */
	public java.util.Iterator<Playlist> iterator(){
		return playlists.iterator();
	}
	
	/**
	 * Indica o numero de musicas da Playlist seleccionada
	 * @return o numero de musicas da playlsit seleccionada
	 */
	public int numberOfSongs() {
		return getSelectedPlaylist().size();
	}
	
	/**
	 * Adiciona a musica seleccionada na MusicLibrary a Playlist seleccionada
	 * @requires somePlaylistSelected()
	 */
	public void addSong() {
		
		if (ml.someSelected())
			if(getSelectedPlaylist() instanceof NormalPlaylist){
				getSelectedPlaylist().add(ml.getSelected());
				getSelectedPlaylist().select(numberOfSongs()-1);
			}
	}

	/**
	 * Selecciona uma musica pretendida da playlist seleccionada
	 * @param i a musica pretendida
	 * @requires somePlaylistSelected()
	 */
	public void selectSong(int i) {
		if(i < numberOfSongs() && i >= 0)
			getSelectedPlaylist().select(i);
	}
	
	/**
	 * Indica se esta alguma musica seleccionada na playlist seleccioanda
	 * @return se esta alguma musica seleccionada
	 * @requires somePlaylistSelected()
	 */
	public boolean someSongSelected() {
		return somePlaylistSelected() && getSelectedPlaylist().someSelected();
	}
	
	/**
	 * Remove a musica seleccionada da playlsit seleccionada
	 * @requires somePlaylistSelected()
	 */
	public void removeSelectedSong() {
		if(getSelectedPlaylist()  instanceof NormalPlaylist)
			getSelectedPlaylist().remove();
	}

	/**
	 * Selecciona a musica seguinte da playlist seleccionada
	 * @requires somePlaylistSelected()
	 */
	public void nextSong() {
		getSelectedPlaylist().next();
	}

	/**
	 * Selecciona a musica anterior da playlist seleccionada
	 * @requires somePlaylistSelected()
	 */
	public void previousSong() {
		getSelectedPlaylist().previous();
	}

	/**
	 * Toca a musica seleccionada da playlist seleccionada
	 * @requires somePlaylistSelected()
	 */
	public void play() {
		getSelectedPlaylist().play();
	}

	/**
	 * Para a musica seleccionada da playlist seleccionada
	 * @requires somePlaylistSelected()
	 */
	public void stop() {
		getSelectedPlaylist().stop();
	}
	
	/**
	 * Incrementa o rate da musica seleccionada da playlist seleccionada
	 * @requires somePlaylistSelected()
	 */
	public void incRateSelected() {
		getSelectedPlaylist().incRateSelected();
	}

	/**
	 * Decrementa o rate da musica seleccionada da playlist seleccionada
	 * @requires somePlaylistSelected()
	 */
	public void decRateSelected() {
		getSelectedPlaylist().decRateSelected();
	}
	
	/**
	 * Devolve uma representacao textual das playlists
	 * @return uma representacao textual das playlists
	 */
	public String toString(){
		String result = "\n***** PLAYLISTS *****\n";
		for(Playlist pl : playlists)
			result = result + "\n " + pl.toString();
		return result;
	}
}
