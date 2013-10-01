package main;
import java.util.LinkedList;

import domain.LEITunes;
import domain.MusicLibraryController;
import domain.PlaylistListController;
import domain.SICAlbum;
import domain.Song;

/**
 * @author antonialopes
 * Class responsible for starting up the system and run some tests.
 */
public class Main {
	
	/* The controllers of the interaction with the MusicLibrary and PlaylistList*/
	
	private static PlaylistListController playlistsController;
	private static MusicLibraryController songsLibraryController;
	
	
	public static void main (String [] args) throws InterruptedException {
		
		LEITunes tunes = new  LEITunes ();
		playlistsController = tunes.getPlaylistController();
		songsLibraryController = tunes.getMusicLibraryController();


		loadSongsAndPlaylists(); 
		testPlaying();
		testChangeCriteria();
		testRemoveSong();
		testSearch();
	}

	/**
	 * Test change of sort criteria for songs
	 */
	private static void testChangeCriteria() {
		System.out.println("\n----------------------------------------------- ");
		System.out.println("          Test change of sort criteria            ");
		System.out.println("----------------------------------------------- ");
		
		//change the sort criteria
		System.out.println("Selected song "+ songsLibraryController.getSelectedSong());
		System.out.println("Change Sort Criteria for Songs -> Name of Album\n");		
		songsLibraryController.setOrder(new SICAlbum());
		System.out.println(songsLibraryController);
		System.out.println("\nSelected song "+ songsLibraryController.getSelectedSong());
	}

	/**
	 * Test the removal of songs from the music library
	 */
	private static void testRemoveSong() {
		System.out.println("\n----------------------------------------------- ");
		System.out.println("         Test remove song from library          ");
		System.out.println("----------------------------------------------- ");
		
		System.out.println("\nSelect song 2 (Homem)\n");
		songsLibraryController.selectSong(2);
		//remove song from library
		System.out.println("\nRemove selected song\n");
		songsLibraryController.removeSelectedSong();
		System.out.println("\nLet's look at the state of the library and playlist\n");		
		System.out.println(songsLibraryController);
		System.out.println(playlistsController);
		
		
		//stop playing
		System.out.println("\nNow some silence...\n");
		songsLibraryController.stop();
	}

	/**
	 * Test search of songs in the music library
	 */
	private static void testSearch() {
		System.out.println("\n----------------------------------------------- ");
		System.out.println("            Test search                         ");
		System.out.println("----------------------------------------------- ");
		
		//search library
		System.out.println("\nSearching library for songs v \n");	
		Iterable<Song> matches = songsLibraryController.getMatches(".*vo.*");
		for (Song s: matches)
			System.out.println(s);
	}

	/**
	 * Test the play functionality
	 * 
	 * @throws InterruptedException
	 */
	private static void testPlaying() throws InterruptedException {
		System.out.println("\n----------------------------------------------- ");
		System.out.println("                     Test playing                 ");
		System.out.println("----------------------------------------------- ");
		
		songsLibraryController.selectSong(0);
		System.out.println("Selected "+ songsLibraryController.getSelectedSong());
		songsLibraryController.play();
		System.out.println("Playing selected!");
		Thread.sleep(1200);
		System.out.println("After waiting a while...");
		songsLibraryController.selectSong(3);
		System.out.println("Selected "+ songsLibraryController.getSelectedSong());
		System.out.println("Still playing the same");
		System.out.println("When it finishes, starts playing the next one (meu fado)");
		Thread.sleep(200000);
		System.out.println("Let's change the mood... Playing selected! (odeio)");	
		songsLibraryController.play();
		Thread.sleep(300000);
		songsLibraryController.incRateSelected();
		songsLibraryController.incRateSelected();
		System.out.println("I like this music (odeio)! Increase its rank twice");		
		System.out.println("Selected "+ songsLibraryController.getSelectedSong());
		System.out.println("Meanwhile let's the music play...");	
		Thread.sleep(400000);
		System.out.println("\nStop current song \n");
		songsLibraryController.stop();
		
		System.out.println("Let's look at the state of the library and playlist");		
		System.out.println(songsLibraryController);
		System.out.println(playlistsController);
		System.out.println("Now play last song to see what happens when it finishes... ");	
		songsLibraryController.selectSong(8);
		songsLibraryController.play();
		Thread.sleep(150000);
	}

	/**
	 * Load some songs and playlists 
	 */
	private static void loadSongsAndPlaylists() {

		// add songs to the library
		LinkedList<String> authors = new LinkedList<String> ();
		
		authors.clear();
		authors.add("Glen Gould");
		authors.add("Bach");
		songsLibraryController.addSong("Goldberg Variations 14", "Classical", authors, "Goldberg Album", "songs/Goldberg Variations.mp3");

		authors.clear();
		authors.add("Mariza");
		songsLibraryController.addSong("Meu Fado", "Fado", authors, "Fado Curvo", "songs/MeuFado.mp3");
		
		authors.clear();
		authors.add("Mariza");
		songsLibraryController.addSong("Fado Tordo", "Fado", authors, "Fado Curvo", "songs/Fado Tordo.mp3");
		
		authors.clear();
		authors.add("Caetano Veloso");
		songsLibraryController.addSong("Odeio", "MPB", authors, "Ce", "songs/08 Odeio.mp3");
		songsLibraryController.addSong("Homem", "MPB", authors, "Ce", "songs/09 Homem.mp3");
		
		authors.clear();
		authors.add("Mozart");
		authors.add("Carolyn Sampson");
		songsLibraryController.addSong("Exsultate", "Classical", authors, "Exsultate, jubilate!", "songs/Exsultate.mp3");
		
		authors.clear();
		authors.add("OVO");
		songsLibraryController.addSong("O mundo", "Pop", authors, "Ovo", "songs/mundo.mp3");
		songsLibraryController.addSong("Dormir", "Pop", authors, "Ovo", "songs/02 Dormir.mp3");
		
		authors.clear();
		authors.add("Mario Laginha");
		authors.add("Bernardo Sassetti");
		songsLibraryController.addSong("Despedida", "Jazz", authors, "A duas maos", "songs/09 Despedida.mp3");

		System.out.println("\n----------------------------------------------- ");		
		System.out.println("           Music library loaded                 ");
		System.out.println("-----------------------------------------------\n ");
		System.out.println(songsLibraryController);

		
		// create two playlists
		playlistsController.createPlaylist("On the Go");
		playlistsController.createPlaylist("Relax");
		System.out.println("\n----------------------------------------------- ");		
		System.out.println("                     Two playlists created        ");
		System.out.println("-----------------------------------------------");

		// add musics to play list 1
		songsLibraryController.selectSong(0);
		playlistsController.selectPlaylist(2);
		playlistsController.addSong();	
		songsLibraryController.selectSong(2);
		playlistsController.addSong();
		songsLibraryController.selectSong(5);
		playlistsController.addSong();

		System.out.println("\n----------------------------------------------- ");
		System.out.println("        Songs 0, 2 and 5 added to On the Go       ");
		System.out.println("----------------------------------------------- ");

		// add musics to play list 2
		songsLibraryController.selectSong(1);
		playlistsController.selectPlaylist(3);
		playlistsController.addSong();	
		songsLibraryController.selectSong(2);
		playlistsController.addSong();
		songsLibraryController.selectSong(8);
		playlistsController.addSong();
		
		System.out.println("\n----------------------------------------------- ");
		System.out.println("        Songs 3, 2 and 8 added to Relax         ");
		System.out.println("----------------------------------------------- ");
		System.out.println(playlistsController);
	}

}
