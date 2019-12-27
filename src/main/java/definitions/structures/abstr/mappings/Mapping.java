/**
 * 
 */
package definitions.structures.abstr.mappings;

import java.io.Serializable;

import definitions.structures.abstr.groups.Monoid;
import definitions.structures.abstr.groups.MonoidElement;

/**
 * @author ro
 *
 */
public interface Mapping extends Serializable {

	/**
	 * Method to apply the mapping on a vector.
	 * 
	 * @param vec the vector.
	 * @return the image on the vector.
	 */
	MonoidElement get(MonoidElement vec);

	/**
	 * Getter for the source space.
	 * 
	 * @return the source space.
	 */
	Monoid getSource();

	/**
	 * Getter for the target space.
	 * 
	 * @return the target space.
	 */
	Monoid getTarget();

}
