package domain;

/**
 * Classe cujos objectos representam tocadores de musica
 * @authors grupo 12
 *
 */
public class LEITunes {
	
	private MusicLibrary ml;
	private MusicLibraryController mlc;
	private PlaylistList pll;
	private PlaylistListController pllc;
	
	/**
	 * Cria um objecto do tipo LEITunes
	 */
	public LEITunes() {
		ml = new MusicLibrary();
		mlc = new MusicLibraryController(ml);
		pll = new PlaylistList(ml);
		pllc = new PlaylistListController(pll, ml);
	}
	
	/**
	 * Devolve o MusicLibraryController
	 * @return o MusicLibraryController
	 */
	public MusicLibraryController getMusicLibraryController(){
		return mlc;
	}
	
	/**
	 * Devolve o PlaylistListController
	 * @return o PlayListlisController
	 */
	public PlaylistListController getPlaylistController(){
		return pllc;
	}

}