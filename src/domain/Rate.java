package domain;

/**
 * Enumerado que representa ratings das musicas
 * @author Grupo 12
 */
enum Rate {
	VERY_BAD,
	BAD,
	NORMAL,
	GOOD,
	VERY_GOOD;

	/**
	 * Devolve uma representacao em numeros da Rate
	 */
	public String toString() {
		if ( this == VERY_BAD)
			return "-2";
		else if (this == BAD)
			return "-1";
		else if(this == NORMAL)
			return "0";
		else if(this == GOOD)
			return "1";
		else
			return "2";
	}

}