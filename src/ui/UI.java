package ui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import domain.MusicLibraryController;
import domain.Playlist;
import domain.PlaylistListController;
import domain.SmartPlaylist;
import domain.Song;

public class UI {
	
	/**
	 * Arvore com os elementos do documento (lado esquerdo)
	 */
	private Tree tree;

	/**
	 * Cola entre os dois widgets: tree e canvas (ao centro)
	 */
	private Sash sash;

	/**
	 * Tela onde se desenham os elementos (lado direito)
	 */
	private Table table;

	/**
	 * Elementos associados a cada no da arvore
	 */
	private Map<TreeItem, RowAction> selections = new HashMap<TreeItem, RowAction>();
	
	private MusicLibraryController songsLibraryController;
	private PlaylistListController playlistsController;

	private boolean selectedPlaylist;
	
	public UI(PlaylistListController playlistListController,
			MusicLibraryController musicLibraryController) {
		this.playlistsController = playlistListController;
		this.songsLibraryController = musicLibraryController;
	}

	/**
	 * @author fmartins
	 *
	 * Controla a seleccao de elementos na arvore.
	 */
	private class TableSelectionController implements SelectionListener {
		public void widgetDefaultSelected(SelectionEvent event) {
			int position = ((Table) event.getSource()).getSelectionIndex();
			if (selectedPlaylist) {
				playlistsController.selectSong(position);
				playlistsController.play();
			} else {
				songsLibraryController.selectSong(position);
				songsLibraryController.play();
			}
		}

		public void widgetSelected(SelectionEvent event) {
			int position = ((Table) event.getSource()).getSelectionIndex();
			if (selectedPlaylist) 
				playlistsController.selectSong(position);
			else
				songsLibraryController.selectSong(position);
		}
	}
	
	/**
	 * @author fmartins
	 *
	 * Controla a seleccao de elementos na arvore.
	 */
	private class TreeSelectionController implements Listener {
		public void handleEvent(Event e) {
			for (TreeItem item : tree.getSelection()) {
				RowAction a = selections.get(item);
				if (a.getPos() != 0) {
					playlistsController.selectPlaylist(a.getPos() - 1);
					selectedPlaylist = true;
				} else
					selectedPlaylist = false;
				a.fillTable();
			}
		}
	}
	
	/**
	 * @author fmartins
	 *
	 * Controla o ajuste horizontal dos elementos na janela
	 */
	private class SashController extends SelectionAdapter {
		public void widgetSelected(SelectionEvent event) {
			// re-estabelece a ligacao da aresta do lado esquerdo, utilizando
			// o valor de x do evento para determinar o offset a partir da esqueda da janela
			((FormData) sash.getLayoutData()).left = new FormAttachment(0, event.x);

			// Forca o redesenho da janela, para que o redimensionamento dos
			// componentes tenha efeito.
			sash.getParent().layout();
		}
	}


	
	public void run() {
		loadSongsAndPlaylists();
		
		// cria a janela
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FormLayout());
		shell.setSize(640, 480);

		// cria os elementos da janela
		createGUIControls(shell);

		// despacha os eventos
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
		
/*		// play the selected music from the library
		songsLibraryController.selectSong(0);
		try {
			songsLibraryController.play();
			Thread.sleep(5000);
			songsLibraryController.still();
			Thread.sleep(5000);
			songsLibraryController.play();
			Thread.sleep(5000);
			songsLibraryController.stop();
			Thread.sleep(5000);
			songsLibraryController.play();
			Thread.sleep(5000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/		
		System.exit(0);
	}

	/**
	 * Preenche a arvore com os elementos a serem visualizados: documento,
	 * layers e elementos de cada layer.
	 */
	private void populateTree() {
		// Object filling the table with the music library
		RowAction fillSongsLibrary = new RowAction (table, null, 0) {
			@Override
			void fillData() {
				for (Song s : songsLibraryController.getMatches(".*")) 
					fillRow (s);
			}
		};
		
		// library
		TreeItem root = new TreeItem(tree, 0);
		root.setText("Library");
		selections.put(root, fillSongsLibrary);
		
		TreeItem treeItem = new TreeItem(root, 0);
		treeItem.setText("Genres");
		selections.put(treeItem, fillSongsLibrary);
		addGenresItems(treeItem, songsLibraryController.getGenres());
		
		treeItem = new TreeItem(root, 0);
		treeItem.setText("Authors");
		selections.put(treeItem, fillSongsLibrary);
		addAuthorItems(treeItem, songsLibraryController.getAuthors());
		
		treeItem = new TreeItem(root, 0);
		treeItem.setText("Albums");
		selections.put(treeItem, fillSongsLibrary);
		addAlbumsItems(treeItem, songsLibraryController.getAlbums());	
		
		// Smart playlists
		TreeItem smartPlaylists = new TreeItem(tree, 0);
		smartPlaylists.setText("Smart Playlists");
		selections.put(smartPlaylists, fillSongsLibrary);
		
		// Playlists
		TreeItem playlists = new TreeItem(tree, 0);
		playlists.setText("Playlists");
		selections.put(playlists, fillSongsLibrary);

		int i = 1;
		for (Iterator<Playlist> it = playlistsController.iterator(); it.hasNext(); i++) {
			Playlist playlist = it.next();
			String value = playlist.getName();
			TreeItem tItem = new TreeItem(playlist instanceof SmartPlaylist ? smartPlaylists : playlists , 0);
			tItem.setText(value);
			selections.put(tItem, new RowAction (table, playlist, i) {
				@Override
				void fillData() {
					int j = 1;
					for (Iterator<Song> it = ((Playlist) criteria).iterator(); it.hasNext(); j++) {
						Song s = it.next();
						fillRow (s, j);
					}
				}
			});
		}		
		
//		currentSelection = getImages();

		root.setExpanded(true);
		playlists.setExpanded(true);
	}

	/**
	 * @param treeItem
	 */
	private void addAuthorItems(TreeItem treeItem, Iterable<String> Mp3Catalog) {
		boolean unknown = false;
		for (String mp3Tag : Mp3Catalog) {
			String value = mp3Tag;
			if (value == "")
				unknown = true;
			else {
				TreeItem tItem = new TreeItem(treeItem, 0);
				tItem.setText(mp3Tag);
				selections.put(tItem, new RowAction (table, mp3Tag, 0) {
					@Override
					void fillData() {
						for (Song s : songsLibraryController.getArtists((String) criteria)) 
							fillRow (s);
					}
				});
			}
		}		
		if (unknown) {
			TreeItem tItem = new TreeItem(treeItem, 0);
			tItem.setText("Unknown");
			selections.put(tItem, new RowAction (table, "", 0) {
				@Override
				void fillData() {
					for (Song s : songsLibraryController.getArtists((String) criteria)) 
						fillRow (s);
				}
			});
		}
	}
	
	
	/**
	 * @param treeItem
	 */
	private void addGenresItems(TreeItem treeItem, Iterable<String> Mp3Catalog) {
		boolean unknown = false;
		for (String mp3Tag : Mp3Catalog) {
			String value = mp3Tag;
			if (value == "")
				unknown = true;
			else {
				TreeItem tItem = new TreeItem(treeItem, 0);
				tItem.setText(mp3Tag);
				selections.put(tItem, new RowAction (table, mp3Tag, 0) {
					@Override
					void fillData() {
						for (Song s : songsLibraryController.getGenres((String) criteria)) 
							fillRow (s);
					}
				});
			}
		}		
		if (unknown) {
			TreeItem tItem = new TreeItem(treeItem, 0);
			tItem.setText("Unknown");
			selections.put(tItem, new RowAction (table, "", 0) {
				@Override
				void fillData() {
					for (Song s : songsLibraryController.getGenres((String) criteria)) 
						fillRow (s);
				}
			});
		}
	}
	
	/**
	 * @param treeItem
	 */
	private void addAlbumsItems(TreeItem treeItem, Iterable<String> Mp3Catalog) {
		boolean unknown = false;
		for (String mp3Tag : Mp3Catalog) {
			String value = mp3Tag;
			if (value == "")
				unknown = true;
			else {
				TreeItem tItem = new TreeItem(treeItem, 0);
				tItem.setText(mp3Tag);
				selections.put(tItem, new RowAction (table, mp3Tag, 0) {
					@Override
					void fillData() {
						for (Song s : songsLibraryController.getAlbums((String) criteria)) 
							fillRow (s);
					}
				});
			}
		}		
		if (unknown) {
			TreeItem tItem = new TreeItem(treeItem, 0);
			tItem.setText("Unknown");
			selections.put(tItem, new RowAction (table, "", 0) {
				@Override
				void fillData() {
					for (Song s : songsLibraryController.getAlbums((String) criteria)) 
						fillRow (s);
				}
			});
		}
	}
	
	/**
	 * Cria os elementos graficos (widgets) a desenhar no ecra.
	 *
	 * @param shell A janela onde serão colocados os widgets
	 */
	private void createGUIControls(Shell shell) {
		// widget que permite ajustar o tamanho do árvore e do canvas
		sash = new Sash (shell, SWT.BORDER | SWT.VERTICAL);
		FormData data = new FormData();
		data.top = new FormAttachment(0, 0);      // liga ao topo da janela
		data.bottom = new FormAttachment(100, 0); // liga ao fundo da janela
		data.left = new FormAttachment(25, 0);    // ocupa 25% da janela
		sash.setLayoutData(data);
		sash.addSelectionListener(new SashController());

		// arvore com os elementos do documento
		tree = new Tree(shell, SWT.BORDER);
		tree.addListener(SWT.Selection, new TreeSelectionController());	
		data = new FormData();
		data.top = new FormAttachment(0, 0);      // liga ao topo da janela
		data.bottom = new FormAttachment(100, 0); // liga ao fundo da janela
		data.left = new FormAttachment(0, 0);     // liga ao lado esquedo da janela
		data.right = new FormAttachment(sash, 0); // liga a cola
		tree.setLayoutData(data);

		// a lista das musicas
		table = new Table (shell, SWT.BORDER | SWT.FULL_SELECTION | SWT.VIRTUAL);
		table.addSelectionListener(new TableSelectionController ());
		table.setLinesVisible (true);
		table.setHeaderVisible (true);
		data = new FormData();
		data.top = new FormAttachment(0, 0);      // liga do topo da janela
		data.bottom = new FormAttachment(100, 0); // liga ao fundo da janela
		data.left = new FormAttachment(sash, 0);  // liga a cola
		data.right = new FormAttachment(100, 0);  // liga ao lado direito da janela
		table.setLayoutData(data);
		
		String[] titles = {" ", "Name", "Author", "Album", "Genre", "Rating", "Play count"};
		for (int i=0; i<titles.length; i++) {
			TableColumn tableColumn = new TableColumn (table, SWT.NONE);
			tableColumn.setText (titles [i]);
		}	
		
		// preenche a arvore
		TreeColumn column = new TreeColumn(tree, SWT.LEAD);
		column.setText("Layers");
		populateTree();
		column.pack();
	}
	/**
	 * Load some songs and playlists 
	 */
	private void loadSongsAndPlaylists() {

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


		
		// create two playlists
		playlistsController.createPlaylist("On the Go");
		playlistsController.createPlaylist("Relax");

		// add musics to play list 1
		songsLibraryController.selectSong(0);
		playlistsController.selectPlaylist(2);
		playlistsController.addSong();	
		songsLibraryController.selectSong(2);
		playlistsController.addSong();
		songsLibraryController.selectSong(5);
		playlistsController.addSong();

		// add musics to play list 2
		songsLibraryController.selectSong(1);
		playlistsController.selectPlaylist(3);
		playlistsController.addSong();	
		songsLibraryController.selectSong(2);
		playlistsController.addSong();
		songsLibraryController.selectSong(8);
		playlistsController.addSong();
		}
	}
