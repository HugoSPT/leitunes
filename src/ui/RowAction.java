package ui;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import domain.Song;

abstract class RowAction {
	
	private Table table;
	private int pos;
	protected Object criteria;
	
	RowAction (Table table, Object criteria, int pos) {
		this.table = table;
		this.criteria = criteria;
		this.pos = pos;
	}
	
	void fillTable () {
		table.removeAll();
		fillData ();
		packColumns ();
	}
	
	abstract void fillData ();
	
	public int getPos () {
		return pos;
	}
	
	void fillRow (Song s, String n) {
		TableItem item = new TableItem (table, SWT.NONE);
		item.setText (0, n);		
		addSongRow(s, item);
	}
	
	void fillRow (Song s, int i) {
		fillRow (s, i + "");
	}
	
	void fillRow (Song s) {
		fillRow (s, "");
	}

	/**
	 * @param s
	 * @param item
	 */
	private void addSongRow(Song s, TableItem item) {
		item.setText (1, s.getSongTitle());
		
		String authorsStr = "";
		List<String> authors = s.getArtists();
		if (authors.size() == 1) {
			authorsStr = authors.get(0);
		} else if (authors.size() == 2) {
			authorsStr = authors.get(0) + " and " + authors.get(1);
		} else if (authors.size() > 2) {
			authorsStr = authors.get(0) + "et al.";
		}
		item.setText (2, authorsStr);

		item.setText (3, s.getAlbum() == null ? "" : s.getAlbum());
		item.setText (4, s.getGenre() == null ? "" : s.getGenre());
		item.setText (5, s.getRating() + "");		
		item.setText (6, s.getTimesPlayed() + "");
	}
	
	private void packColumns () {
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumn (i).pack ();
		}	
	}
	
}