package domain;

import java.util.Comparator;

/**
 * Classe cujos objectos representam o criterio de ordenacao de musicas pelo titulo da musica
 * @author Grupo 12
 */
public class SICSongTitle implements SongIterationCriteria {

	@Override
	public Comparator<Song> getCriteriaComparator() {
		return new TitleComparator();
	}
	
	/**
	 * Classe que representa 1 comparador de titulos de musica
	 * @author Grupo 12
	 */
	private class TitleComparator implements Comparator<Song>{

		@Override
		public int compare(Song o1, Song o2) {
			return o1.getSongTitle().compareTo(o2.getSongTitle());
		}
		
	}
}
