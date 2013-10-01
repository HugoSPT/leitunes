package domain;

import java.util.Comparator;

/**
 * Classe cujos objectos representam o criterio de ordenacao de musicas pelo Album
 * @author Grupo 12
 */
public class SICAlbum implements SongIterationCriteria {

	@Override
	public Comparator<Song> getCriteriaComparator() {
		return new AlbumComparator();
	}
	
	/**
	 * Classe que representa 1 comparador de albuns
	 * @author Grupo 12
	 */
	private class AlbumComparator implements Comparator<Song>{

		@Override
		public int compare(Song o1, Song o2) {
			return o1.getAlbum().compareTo(o2.getAlbum());
		}
		
	}
}
