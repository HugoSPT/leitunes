package domain;

import java.util.List;

/**
 *  Classe cujos objectos representam um controlador de uma 
 *  MusicLibrary e da interacção do exterior com este.
 *  @authors grupo 12
 */
public class MusicLibraryController {
	
	private MusicLibrary ml;

	/**
	 * Cria um objecto do tipo MusicLibraryController.
	 * @param library a library a controlar
	 */
	public MusicLibraryController(MusicLibrary library){
		this.ml = library;
	}
	
	/**
	 * Da o numero de musicas da biblioteca controlada
	 * @return o numero de musicas da biblioteca controlada.
	 */
	public int numberOfSongs(){
		return this.ml.size();
	}
	
	/**
	 * Insere uma nova música, construída com os argumentos
	 * dados, na biblioteca controlada.
	 * @param songTitle o nome da musica
	 * @param genre o genero da musica
	 * @param authors os autores da musica
	 * @param album o nome do album
	 * @param filename o nome do ficheiro da musica
	 */
	public void addSong(String songTitle, String genre, List<String> authors, String album, String filename){
		ml.addSong(songTitle, genre, authors, album, filename);
	}
	
	/**
	 * despacha o pedido de selecção para a biblioteca controlada; 
	 * caso contrário não faz nada.
	 * @param i a musica a seleccionar
	 */
	public void selectSong(int i){
		if(i >= 0 && i < numberOfSongs())
			ml.select(i);
	}
	
	/**
	 * Que devolve a música seleccionada na biblioteca se esta existir; 
	 * caso contrário devolve null
	 * @return a musica seleccionada
	 * @requires ml.someSelected()
	 */
	public Song getSelectedSong(){
		return ml.getSelected();
	}
	
	/**
	 * Apaga a musica seleccionada na biblioteca (se ela existir).
	 */
	public void removeSelectedSong(){
		if(ml.someSelected())
			ml.remove();
	}
	
	/**
	 * Que selecciona a próxima música (relativamente à seleccionada) na
	 * biblioteca controlada, se getIndexSelected()<size()-1. 
	 * Senão, deixa de haver um elemento seleccionado. 
	 * A ordem subjacente é a definida pela critério de iteração corrente.
	 */
	public void nextSong(){
		ml.next();
	}
	
	/**
	 * Que selecciona a música anterior (relativamente à seleccionada) 
	 * na biblioteca controlada, se getIndexSelected()>0. 
	 * Senão, deixa de haver um elemento seleccionado. 
	 * A ordem subjacente é a definida pela critério de iteração corrente.
	 */
	public void previousSong(){
		ml.previous();
	}
	
	/**
	 * Mete a tocar a musica seleccionada
	 */
	public void play(){
		ml.play();
	}
	
	/**
	 * Para a musica que esta a tocar
	 */
	public void stop(){
		ml.stop();
	}
	
	/**
	 * Incrementa a classificacao da musica seleccionada
	 */
	public void incRateSelected(){
		ml.incRateSelected();
	}
	
	/**
	 * Decrementa a classificacao da musica seleccionada
	 */
	public void decRateSelected(){
		ml.decRateSelected();
	}
	
	/**
	 * Devolve uma estrutura iteravel com as musicas da biblioteca
	 * controlada que emparelham com a expressao dada
	 * @param regexp a expressao dada
	 * @return a estrutura com as musicas da biblioteca
	 */
	public Iterable<Song> getMatches(String regexp){
		return ml.getMatches(regexp);
	}
	
	/**
	 * Ordena a biblioteca segundo um criterio dado
	 * @param criteria o criterio de ordenacao
	 */
	public void setOrder(SongIterationCriteria criteria){
		ml.setOrder(criteria);
	}
	
	/**
	 * Devolve as musicas da biblioteca controlada segundo
	 * a ordem de criterio corrente.
	 * @return o nome das musicas da biblioteca controlada
	 */
	public Iterable<Song> getSongs(){
		return ml.getSongs();
	}
	
	/**
	 * Devolve o nome dos autores das musicas na biblioteca controlada.
	 * @return o nome dos autores das musicas 
	 */
	public Iterable<String> getAuthors(){
		return ml.getAuthors();
	}
	
	/**
	 * Devolve o nome dos albums das musicas na biblioteca controlada.
	 * @return o nome dos albuns das musicas 
	 */
	public Iterable<String> getAlbums(){
		return ml.getAlbums();
	}
	
	/**
	 * Devolve o genero das musicas na biblioteca controlada.
	 * @return o genero das musicas 
	 */
	public Iterable<String> getGenres(){
		return ml.getGenres();
	}
	
	/**
	 * Devolve uma estrutura com as musicas de um dado artista
	 * @param artist o artista do qual se pretende as musicas
	 * @return as musicas deste artista
	 */
	public Iterable<Song> getArtists(String artist) {
		return ml.getArtists(artist);
	}
	
	/**
	 * Devolve uma estrutura com as musicas que tem um dado album
	 * @param album o album a procurar
	 * @return as musicas que tem este album
	 */
	public Iterable<Song> getAlbums(String album){
		return ml.getAlbums(album);
	}
	
	/**
	 * As musicas com um determinado genero
	 * @param genre o genero a procurar
	 * @return as musicas deste genero
	 */
	public Iterable<Song> getGenres(String genre){
		return ml.getGenres(genre);
	}
	
	/**
	 * Devolve uma representacao textual
	 * @return uma representacao textual
	 */
	public String toString(){
		return "*****MUSIC LIBRARY****\n" + ml.toString();
	}
}
