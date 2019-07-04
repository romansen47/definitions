package definitions.structures.abstr;

import java.util.Map;

/**
 * The homomorphism interface.
 * 
 * @author ro
 *
 */
public interface Homomorphism {

	/**
	 * Method to apply the homomorphism on a vector.
	 * 
	 * @param vec the vector.
	 * @return the image on the vector.
	 */
	Vector get(Vector vec);

	/**
	 * Method to get the image of the restriction of the homomorphism to the base of
	 * the source vector space.
	 * 
	 * @param vec the base vector.
	 * @return the image of the base vector.
	 */

	default Map<Vector, Double> getLinearity(final Vector vec1) {
		return getLinearity().get(vec1);
	}

	/**
	 * Method to get the restriction of the homomorphism to the base of the source
	 * vector space.
	 * 
	 * @return the image of the base vector.
	 */
	Map<Vector, Map<Vector, Double>> getLinearity();

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

	/**
	 * the generic matrix in correspondance with the ordered bases of the source and
	 * the target space.
	 * 
	 * @return
	 */
	double[][] getGenericMatrix();

}
