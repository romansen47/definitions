/**
 * 
 */
package definitions.structures.abstr.mappings;

import java.io.Serializable;

import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.vectorspaces.VectorSpace;

/**
 * @author ro
 *
 */
public interface VectorSpaceMapping extends MonoidHomomorphism, Serializable {

	/**
	 * Method to apply the mapping on a vector.
	 * 
	 * @param vec the vector.
	 * @return the image on the vector.
	 */
	Element get(Element vec);

	/**
	 * Getter for the source space.
	 * 
	 * @return the source space.
	 */
	@Override
	VectorSpace getSource();

	/**
	 * Getter for the target space.
	 * 
	 * @return the target space.
	 */
	@Override
	VectorSpace getTarget();

}
