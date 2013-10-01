package domain;

import java.util.Arrays;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import adts.QListWithSelection;

/**
 * Classe que representa uma Biblioteca de musicas
 * @author Grupo 12
 */
class MusicLibrary extends Observable implements QListWithSelection<Song>, Observer{
		
	private ArrayList<Song> songs;
	private int selected;
	private int present;
	private boolean isPlaying;
	private SongIterationCriteria criter;

	/**
	 * Constrói de uma nova Biblioteca de musicas
	 */
	MusicLibrary(){
		songs = new ArrayList<Song>();
		selected= -1;
		isPlaying = false;
		criter = new SICSongTitle();
		present = -1;
		PlayerFactory.getPlayer().addObserver(this);
	}
	
	/**
	 * Processa 1 evento recebido
	 * @param o o observavel
	 * @param o evento que ocorreu
	 */
	@Override
	public void update(Observable o, Object event) {
		
		if(event instanceof SongStopedEvent){
			
			this.setChanged();
			this.notifyObservers(event);
		}
		else if (event instanceof SongPlayedEvent){
			
			if(isPlaying){
				songs.get(present).incTimesPlayed();
				
				present++;
				if (present >= songs.size())
					present = 0;
				
				play(songs.get(present));
			}
			
			this.setChanged();
			this.notifyObservers(event);
		}
	}

	/**
	 * Toca a musica seleccionada
	 * @requires someSelected();
	 */
	void play(){

		if(isPlaying)
			stop();
		
		present = selected;
		isPlaying = true;

		play(songs.get(present));
	}
	
	/**
	 * Toca a musica pretendida
	 * @param s a musica pretendida
	 */
	private void play(Song s){
		isPlaying=true;
		
		PlayerFactory.getPlayer().load(s.getFileName());
		PlayerFactory.getPlayer().play();
	}
	
	/**
	 * Indica se a biblioteca esta a tocar
	 * @return se a biblioteca esta a tocar
	 */
	boolean isPlaying(){
		return isPlaying;
	}
	
	/**
	 * Pára a musica que estava a tocar
	 */
	void stop(){
		isPlaying = false;
		PlayerFactory.getPlayer().stop();
	}
	
	/**
	 * Incrementa o rate da musica seleccionada
	 * @requires someSelected();
	 */
	void incRateSelected(){
		songs.get(selected).incRating();
	}
	
	/**
	 * Decrementa o rate da musica seleccionada
	 * @requires someSelected();
	 */
	void decRateSelected(){
		songs.get(selected).decRating();
	}
	
	/**
	 * Adiciona uma nova musica à biblioteca com o dados recebidos
	 * @param songTitle o titulo da musica
	 * @param genre o genero da musica
	 * @param authors os autores da musica
	 * @param album o album da musica
	 * @param filename o nome do ficheiro da musica
	 */
	void addSong(String songTitle, String genre, List<String> authors, String album, String filename){
		Song song = new Song(songTitle, genre, authors, album, filename);
		songs.add(song);
		this.select(songs.size()-1);
		this.setChanged();
		this.notifyObservers(new SongAddedLibraryEvent(song, this));
	}
	
	/**
	 * Define o criterio de ordenação da biblioteca
	 * @param criteria o criterio de ordenação
	 */
	void setOrder(SongIterationCriteria criteria){
		
		criter = criteria;
		
		Song sel = this.getSelected();
		
		Song[] aux = new Song[songs.size()];
		for(int i = 0; i < songs.size(); i++){
			aux[i] = songs.get(i);
		}
		
		Arrays.sort(aux, criter.getCriteriaComparator());
		for(int i =0; i < aux.length;i++){
			if(aux[i] == sel)
				selected = i;
			songs.set(i, aux[i]);
		}
	}
	
	/**
	 * Devolve uma estrutura iteravel com os autores das musicas
	 * @return uma estrutura iteravel com os autores das musicas
	 */
	Iterable<String> getAuthors(){
		ArrayList<String> nova = new ArrayList<String>();
		List<String> arts;
		for(Song s : songs){
			arts = s.getArtists();
			for(String a : arts){
				if(!nova.contains(a))
					nova.add(a);
			}
		}
		return nova;
	}
	
	/**
	 * Devovle uma estrutura iteravel com os albums das musicas
	 * @return uma estrutura iteravel com os albums das musicas
	 */
	Iterable<String> getAlbums() {
		ArrayList<String> nova = new ArrayList<String>();
		for(Song s : songs)
			if(!nova.contains(s.getAlbum()))
				nova.add(s.getAlbum());	
		return nova;
	}
	
	/**
	 * Devovle uma estrutura iteravel com os generos das musicas
	 * @return uma estrutura iteravel com os generos das musicas
	 */
	Iterable<String> getGenres() {
		ArrayList<String> nova = new ArrayList<String>();
		for(Song s : songs)
			if(!nova.contains(s.getGenre()))
				nova.add(s.getGenre());	
		return nova;
	}
	
	/**
	 * Devovle uma estrutura iteravel com as musicas que contem a expressao dada
	 * @param regexp a expressao dada
	 * @return uma estrutura iteravel com as musicas que contem a expressao dada
	 */
	Iterable<Song> getMatches(String regexp){
		ArrayList<Song> nova = new ArrayList<Song>();
		for(Song s : songs)
			if(s.matches(regexp))
				nova.add((Song) s.clone());
		return nova;
	}
	
	/**
	 * Devolve uma estrutura iteravel com todas as musicas da biblioteca 
	 * ordenadas pelo criterio actual
	 * @return uma estrutura iteravel com todas as musicas da biblioteca 
	 * ordenadas pelo criterio actual
	 */
	Iterable<Song> getSongs(){;
		
		ArrayList<Song> nova = new ArrayList<Song>();
		for(Song s : songs)
			nova.add((Song) s.clone());
		
		Song[] novaA = (Song[]) nova.toArray();
		Arrays.sort(novaA, criter.getCriteriaComparator());
		for(int i =0; i < nova.size();i++)
			nova.set(i, novaA[i]);
		
		return nova;
	}
	
	/**
	 * Devolve uma estrutura iteravel com todas as musicas do autor dado
	 * @param artist o autor a pesquisar
	 * @return uma estrutura iteravel com todas as muscas do autor artist
	 */
	Iterable<Song> getArtists(String artist){
		ArrayList<Song> nova = new ArrayList<Song>();
		for(Song s : songs)
			if(s.getArtists().contains(artist))
				nova.add((Song) s.clone());
		return nova;
	}
	
	/**
	 * Devolve uma estrutura iteravel com todas as musicas do album dado
	 * @param album o album a pesquisar
	 * @return uma estrutura iteravel com todas as musicas do album album
	 */
	Iterable<Song> getAlbums(String album){
		ArrayList<Song> nova = new ArrayList<Song>();
		for(Song s : songs)
			if(s.getAlbum().equals(album))
				nova.add((Song) s.clone());
		return nova;
	}
	
	/**
	 * Devolve uma estrutura iteravel com todas as musicas do genero dado
	 * @param genre o genero a pesquisar
	 * @return uma estrutura iteravel com todas as musicas do genero genre
	 */
	Iterable<Song> getGenres(String genre){
		ArrayList<Song> nova = new ArrayList<Song>();
		for(Song s : songs)
			if(s.getGenre().equals(genre))
				nova.add((Song) s.clone());
		return nova;
	}
	
	@Override
	public void add(Song e) throws UnsupportedOperationException {
		songs.add(e);
		this.select(songs.size()-1);
		this.setChanged();
		this.notifyObservers(new SongAddedLibraryEvent(e, this));
	}

	@Override
	public int getIndexSelected() {
		return selected;
	}

	@Override
	public Song getSelected() {
		return songs.get(selected);
	}

	@Override
	public void moveSelected(int i) throws UnsupportedOperationException {
		songs.add(i, songs.get(selected));
		
		if (i < selected)
			selected++;
		
		songs.remove(selected);
		selected = i;
	}

	@Override
	public void next() {
		
		if(selected < size()-1)
			selected++;
		else
			selected = -1;
	}

	@Override
	public void previous() {
		
		if(selected > 0)
			selected--;
		else
			selected = -1;	
	}

	@Override
	//Quando remove nao fica ennhuma seleccionada
	public void remove() throws UnsupportedOperationException {
		if (someSelected()){
			this.setChanged();
			this.notifyObservers(new SongRemovedLibraryEvent(songs.get(selected), this));
			songs.remove(selected);
			selected = -1;
		}
	}

	@Override
	public void select(int i) {
		selected = i;
	}

	@Override
	public boolean someSelected() {
		return selected != -1;
	}

	@Override
	public Iterator<Song> iterator() {
		return songs.iterator();
	}

	@Override
	public int size() {
		return songs.size();
	}

	@Override
	public Song get(int i) {
		return songs.get(i);
	}
	
	/**
	 * Devolve uma representacao textual da MusicLibrary
	 */
	public String toString() {
		
		String result = "";
		for(Song s : songs)
			result = result + "\n " + s.toString();
		return result;
	}
}
