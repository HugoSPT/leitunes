package domain;

import java.util.LinkedList;
import java.util.List;

import adts.Searchable;

/**
 * Classe cujos objectos representam uma musica
 * @author Grupo 12
 */
public class Song implements Searchable, Cloneable {
	
	private String title;
	private String genre;
	private List<String> artists;
	private String album;
	private String fileName;
	private int counter;
	private Rate rate;
	
	/**
	 * Cria 1 objecto que representa uam musica
	 * @param songTitle o titulo da musica
	 * @param genre o generod a musica
	 * @param artists os artistas da musica
	 * @param album o album da musica
	 * @param fileName o nome do ficheiro da musica
	 */
	public Song(String songTitle, String genre, List<String> artists, String
			album, String fileName){
		this.title = songTitle;
		this.genre = genre;
		this.artists = new LinkedList<String>();
		for(String s: artists)
			this.artists.add(s);
		this.album = album;
		this.fileName = fileName;
		counter = 0;
		rate = Rate.NORMAL;
	}
	
	/**
	 * Incrementa o numero de vezes que a musica foi tocada
	 */
	public void incTimesPlayed(){
		counter++;
	}
	
	/**
	 * Devolve o numero de vezes que a musica foi tocada
	 * @return o numero de vezes que a musica foi tocada
	 */
	public int getTimesPlayed(){
		return counter;
	}
	
	/**
	 * Devolve a classificacao da musica
	 * @return a classificacao
	 */
	public Rate getRating(){
		return rate;
	}
	
	/**
	 * Incrementa a classificacao da musica para a classificacao seguinte
	 * caso seja a maxima classificacao nao faz nada
	 */
	public void incRating(){
		if (rate.ordinal() < Rate.values().length-1)
			rate = Rate.values()[rate.ordinal()+1];
	}
	
	/**
	 * Decrementa a classificacao da musica para a classificacao anterior
	 * caso seja a minima classificacao nao faz nada
	 */
	public void decRating(){
		if (rate.ordinal() > 0)
			rate = Rate.values()[rate.ordinal()-1];
	}
	
	/**
	 * Devolve o titulo da musica
	 * @return o titulo da musica
	 */
	public String getSongTitle(){
		return title;
	}
	
	/**
	 * Devolve o genero da musica
	 * @return o genero da musica
	 */
	public String getGenre(){
		return genre;
	}
	
	/**
	 * Devolve o nome do album da musica
	 * @return o nome do album da musica
	 */
	public String getAlbum(){
		return album;
	}
	
	/**
	 * Devolve os artistas da musica
	 * @return os artistas da musica
	 */
	public List<String> getArtists(){
		List<String> nova = new LinkedList<String>();
		for(String a : artists)
			nova.add(a);
		return nova;
	}
	
	/**
	 * Devolve o nome do ficheiro da musica
	 * @return o nome do ficheiro da musica
	 */
	public String getFileName(){
		return fileName;
	}
	
	/**
	 * @param regexp a regular expression used for match
	 * @return if object matches regexp.
	 * @Override
	 */
	public boolean matches(String regexp) {
		boolean foundArtist = false;
		for(String a : artists)
			if(a.matches(regexp)){
				foundArtist = true;
				break;
			}	
		return title.matches(regexp) || genre.matches(regexp) 
			|| album.matches(regexp) || foundArtist;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (!(obj instanceof Song))
			return false;
		
		Song other = (Song) obj;
		
		return title == other.title && genre == other.genre
			&& artists.equals(other.artists) && album == other.getAlbum()
			&& fileName == other.fileName;
	}
	
	public Song clone(){
		Song nova = null;
		try {
			nova = (Song) super.clone();	
			nova.artists = getArtists();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}	
		return nova;
	}
	
	/**
	 * Devolve uma representacao textual da musica
	 */
	public String toString(){
		return this.title + " --- " + this.album + " --- " + this.artists
			+ " --- " + this.genre + " --- " + this.rate + " -- " + this.counter;
	}
}
