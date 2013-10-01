package domain;

/**
 * Classe que cria Players
 * @author Grupo 12
 */
public class PlayerFactory {
	
	/**
	 * Devolve uma instancia do JLPlayerAdapter, o player pretendido
	 * @return uma instancia do JLPlayerAdapter, o player pretendido
	 */
	public static IPlayer getPlayer(){
		return JLPlayerAdapter.getInstance();
	}
	
}
