package definitions.structures.abstr.mappings;

import java.util.Map;

import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.monoids.Monoid;
import definitions.structures.abstr.algebra.monoids.MonoidElement;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

/**
 * The homomorphism interface.
 * 
 * @author ro
 *
 */
public interface VectorSpaceHomomorphism extends Vector, MonoidHomomorphism {

	/**
	 * Method to apply the homomorphism on a vector.
	 * 
	 * @param vec the vector.
	 * @return the image on the vector.
	 */
	default Vector get(final Vector vec) {
		final MonoidElement vector = vec;
		return (Vector) this.get(vector);
	}

	/**
	 * the generic matrix in correspondance with the ordered bases of the source and
	 * the target space.
	 * 
	 * @return
	 */
	Scalar[][] getGenericMatrix();

	/**
	 * Method to get the image of the restriction of the homomorphism to the base of
	 * the source vector space. It gives a map
	 * 
	 * e |-> A(e)
	 * 
	 * where A is the linear mapping and where e is a base element of the source
	 * vector space.
	 * 
	 * @param vec the base vector.
	 * @return the image of the base vector.
	 */
	default Map<Vector, Scalar> getImageVectorOfBaseVector(final Vector vec1) {
		return this.getLinearity().get(vec1);
	}

	/**
	 * Method to get the restriction of the homomorphism to the base of the source
	 * vector space.
	 * 
	 * @return the image of the base vector.
	 */
	Map<Vector, Map<Vector, Scalar>> getLinearity();

	/**
	 * ${inheritDoc}
	 */
	@Override
	Monoid getSource();

	@Override
	Monoid getTarget();

}
