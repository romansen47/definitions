/**
 * 
 */
package definitions.structures.abstr.mappings;

import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

/**
 * @author ro
 *
 */
public interface Mapping {

	/**
	 * Method to apply the mapping on a vector.
	 * 
	 * @param vec
	 *            the vector.
	 * @return the image on the vector.
	 */
	Vector get(Vector vec);

	/**
	 * Getter for the source space.
	 * 
	 * @return the source space.
	 */
	VectorSpace getSource();

	/**
	 * Getter for the target space.
	 * 
	 * @return the target space.
	 */
	VectorSpace getTarget();

}
