package definitions.structures.abstr;

import definitions.structures.field.scalar.impl.Real;

/**
 * 
 * @author RoManski
 *
 *         A hilbert space is a normed space where the norm is induced by a
 *         scalar product.
 */
public interface InnerProductSpace extends NormedSpace {

	/**
	 * the scalar product of two vectors.
	 * 
	 * @param vec1 the first vector
	 * @param vec2 the second vector
	 * @return the scalar product of vec1 and vec2
	 */
	Scalar innerProduct(Vector vec1, Vector vec2);

	/**
	 * Method to project one vector onto another.
	 * 
	 * @param w reference vector.
	 * @param v projected vector.
	 * @return projection of v on w.
	 */
	Vector projection(Vector w, Vector v);

	@Override
	default Real norm(final Vector vec) {
		return new Real(Math.sqrt(innerProduct(vec, vec).getValue()));
	}

}
