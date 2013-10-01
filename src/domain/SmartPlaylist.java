package domain;

/**
 * Classe que representa uma playlist automatica
 * @author Grupo 12
 */
public abstract class SmartPlaylist extends Playlist {
		
	/**
	 * Cria 1 objecto que representa uma SmartPlaylist
	 * @param name o nome da smartPlaylist
	 * @param library a biblioteca associada a playlist
	 */
	SmartPlaylist(String name, MusicLibrary library){
		super(name, library);
	}
	
	/**
	 * Adiciona uma musica automaticamente a playlist
	 * @param song a musica a adicionar
	 */
	protected abstract void addAutomatic(Song song);
	
	/**
	 * Adiciona uma musica a playlist
	 * @param s a musica a adicionar
	 */
	protected final void addSong(Song s){
		super.add(s);
	}
	
	/**
	 * Remove uma musica a playlist
	 * @param s a musica a remover
	 */
	protected final void removeSong(Song s){
		super.remove(s);
	}
	
	@Override
	public void add(Song s) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void moveSelected(int i){
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void remove(){
		throw new UnsupportedOperationException();
	}
	
}
