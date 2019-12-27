package definitions.structures.abstr.mappings;

import java.util.Map;

import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.groups.Monoid;
import definitions.structures.abstr.groups.MonoidElement;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

/**
 * The homomorphism interface.
 * 
 * @author ro
 *
 */
public interface Homomorphism extends Mapping, Vector {

	/**
	 * Method to apply the homomorphism on a vector.
	 * 
	 * @param vec the vector.
	 * @return the image on the vector.
	 */
	default Vector get(Vector vec) {
		MonoidElement vector = (MonoidElement) vec;
		return (Vector) get(vector);
	}

	/**
	 * the generic matrix in correspondance with the ordered bases of the source and
	 * the target space.
	 * 
	 * @return
	 */
	Scalar[][] getGenericMatrix();

	/**
	 * Method to get the restriction of the homomorphism to the base of the source
	 * vector space.
	 * 
	 * @return the image of the base vector.
	 */
	Map<Vector, Map<Vector, Scalar>> getLinearity();

	/**
	 * Method to get the image of the restriction of the homomorphism to the base of
	 * the source vector space.
	 * 
	 * @param vec the base vector.
	 * @return the image of the base vector.
	 */
	default Map<Vector, Scalar> getLinearity(final Vector vec1) {
		return this.getLinearity().get(vec1);
	}

	@Override
	Monoid getSource();

	@Override
	Monoid getTarget();

}
