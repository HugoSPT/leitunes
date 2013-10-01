package adts;

/**
 * @author fmartins
 *
 * Represents searchable objects.
 */
public interface Searchable {

	/**
	 * @param regexp a regular expression used for match
	 * @return if object matches regexp.
	 */
	boolean matches (String regexp);
}
