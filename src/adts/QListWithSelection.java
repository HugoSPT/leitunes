package adts;

public interface QListWithSelection<E> extends QList<E> {
	
	/**
	 * Adiciona o elemento ao fim da lista e esse elemento
	 * fica seleccionado
	 * @param e o elemento a adicionar
	 * @throws UnsupportedOperationException
	 */
	void add(E e) throws UnsupportedOperationException;
	
	/**
	 * Selecciona o elemento na posição pretendida
	 * @param i a posição pretendida
	 * @requires 0<=i<size();
	 */
	void select(int i);

	/**
	 * Indica se algum elemento esta seleccionado
	 * @return se esta seleccionado
	 */
	boolean someSelected();
	
	/**
	 * Devolve a posicao do elemento seleccionado
	 * @return a posicao do elemento
	 * @requires someSelected();
	 */
	int getIndexSelected();
	
	/**
	 * Move o elemento seleccionado para a posicao escolhida
	 * @param i a posicao escolhida
	 * @throws UnsupportedOperationException
	 * @requires 0<=i<size() && someSelected();
	 */
	void moveSelected(int i) throws UnsupportedOperationException;
	
	/**
	 * Selecciona o elemento que esta na posicao seguinte ao
	 * elemento seleccionado se getIndexSelected()<size()-1
	 * senão deixa de haver 1 elemento seleccionado
	 */
	void next();
	
	/**
	 * Selecciona o elemento que esta na posicao anterior ao
	 * elemento seleccionado se getIndexSelected()>0
	 * senão deixa de haver 1 elemento seleccionado
	 */
	void previous();
	
	/**
	 * Apaga o elemento seleccionado se someSelected()
	 * senão não faz nada
	 * @throws UnsupportedOperationException
	 */
	void remove() throws UnsupportedOperationException;
	
	/**
	 * Devolve o elemento seleccionado
	 * @return o elemento seleccionado
	 * @requires someSelected();
	 */
	E getSelected();
}
