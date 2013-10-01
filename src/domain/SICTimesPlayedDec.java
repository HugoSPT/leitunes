package domain;

import java.util.Comparator;

/**
 * Classe cujos objectos representam o criterio de ordenacao de musicas pelo
 * numero de vezes que tocaram
 * @author Grupo 12
 */
public class SICTimesPlayedDec implements SongIterationCriteria {

	@Override
	public Comparator<Song> getCriteriaComparator() {
		return new TimesPlayedDecComparator();
	}
	
	/**
	 * Classe que representa 1 comparador de vezes que uma musica tocou
	 * @author Grupo 12
	 */
	private class TimesPlayedDecComparator implements Comparator<Song>{

		@Override
		public int compare(Song o1, Song o2) {
			if(o1.getTimesPlayed() == o2.getTimesPlayed())
				return 0;
			else if(o1.getTimesPlayed() < o2.getTimesPlayed())
				return -1;
			return 1;
		}
		
	}
}
