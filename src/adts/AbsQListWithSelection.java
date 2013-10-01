package adts;

import java.util.List;
import java.util.Iterator;

public abstract class AbsQListWithSelection<E> implements QListWithSelection<E> {

	//Index do elemento seleccionado
	private int selected;
	private List<E> list;

	/**
	 * Cria um objecto do tipo AbsQListWithSelection
	 * com nenhum elemento seleccionado
	 */
	public AbsQListWithSelection(){
		selected = -1;
		list = listInit();
	}

	public abstract List<E> listInit();
	
	/**
	 * Devolve a posicao do elemento seleccionado
	 * @return a posicao do elemento
	 * @requires someSelected();
	 * @Override
	 */
	public int getIndexSelected() {
		return selected;
	}

	/**
	 * Selecciona o elemento que esta na posicao seguinte ao
	 * elemento seleccionado se getIndexSelected()<size()-1
	 * senão deixa de haver 1 elemento seleccionado
	 * @Override
	 */
	public void next() {
		if(selected < size()-1)
			selected++;
		else
			selected = -1;
	}

	/**
	 * Selecciona o elemento que esta na posicao anterior ao
	 * elemento seleccionado se getIndexSelected()>0
	 * senão deixa de haver 1 elemento seleccionado
	 * @Override
	 */
	public void previous() {
		if(selected > 0)
			selected--;
		else
			selected = -1;
	}

	/**
	 * Selecciona o elemento na posição pretendida
	 * @param i a posição pretendida
	 * @requires 0<=i<size();
	 * @Override
	 */
	public void select(int i) {
		selected = i;
	}

	/**
	 * Indica se algum elemento esta seleccionado
	 * @return se esta seleccionado
	 * @Override
	 */
	public boolean someSelected(){
		return selected >= 0 && selected < list.size();
	}
	
	@Override
	public void add(E e) throws UnsupportedOperationException {
		list.add(e);
	}

	@Override
	public E getSelected() {
		return list.get(selected);
	}

	@Override
	public void moveSelected(int i) throws UnsupportedOperationException {
		list.add(i, list.get(selected));
		list.remove(selected);
		selected = i;
	}

	@Override
	public void remove() throws UnsupportedOperationException {
		if (someSelected()){
			list.remove(selected);
			selected = -1;
		}
	}

	@Override
	public E get(int i) {
		return list.get(i);
	}

	@Override
	public Iterator<E> iterator() {
		return list.iterator();
	}

	@Override
	public int size() {
		return list.size();
	}
	
	public void remove(E e){
		list.remove(e);
	}
	
	protected boolean has(E e){
		return list.contains(e);
	}
}
