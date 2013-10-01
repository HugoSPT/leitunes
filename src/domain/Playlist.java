package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import adts.AbsQListWithSelection;
import adts.QListWithSelection;

/**
 * Classe cujos objectos rpresentam uma lista de musicas associadas a uma MusicLibrary
 * @author Grupo 12
 */
public abstract class Playlist extends AbsQListWithSelection<Song> implements
		QListWithSelection<Song>, Observer {

	protected int present;
	private String name;
	protected MusicLibrary ml;
	private boolean isPlaying;
	
	/**
	 * Cria uma nova Playlist
	 * @param name o nome da playlist
	 * @param library a biblioteca da playlist
	 */
	public Playlist(String name, MusicLibrary library){
		this.ml = library;
		this.name = name;
		present = 0;
		this.isPlaying = false;
		library.addObserver(this);
	}
	
	/**
	 * Devolve o nome da Playlist
	 * @return o nome da playlist
	 */
	public String getName(){
		return this.name;
	}
	
	@Override
	public List<Song> listInit(){
		return new ArrayList<Song>();
	}

	@Override
	public abstract void update(Observable o, Object event);
	
	/**
	 * Toca a musica seleccionada na playlist
	 * @requires someSelected();
	 */
	public void play() {
		
		if(isPlaying)
			stop();

		present = super.getIndexSelected();
		isPlaying = true;
		
		play(super.getSelected());
	}
		
	/**
	 * Toca uma musica escolhida
	 * @param s a musica escolhida
	 */
	protected void play(Song s){
		PlayerFactory.getPlayer().load(s.getFileName());
		PlayerFactory.getPlayer().play();
	}
	
	/**
	 * Para a musica que esta a tocar
	 */
	public void stop() {
		isPlaying = false;
		PlayerFactory.getPlayer().stop();
	}

	/**
	 * Incrementa o rate da musica seleccionada
	 * @requires someSelected();
	 */
	void incRateSelected() {
		super.getSelected().incRating();
	}
	
	/**
	 * Decrementa o rate da musica seleccionada
	 * @requires someSelected();
	 */
	void decRateSelected() {
		super.getSelected().decRating();
	}
	
	/**
	 * Diz se a playlist esta a tocar
	 * @return se a playlist esta a tocar
	 */
	boolean isPlaying(){
		return isPlaying;
	}
	
	/**
	 * Devolve uma representacao textua da playlist
	 * @return uma representacao textual da playlist
	 */
	public String toString() {
		String result = "\n*-- Playlist "+name+" --*\n";
		for(int i = 0; i< super.size(); i++)
			result = result + "\n "+ i+ " " + super.get(i).toString();
		return result;
	}
}