package definitions.structures.abstr;

/**
 * 
 * @author RoManski
 *
 * A hilbert space is a normed space where the norm is induced by a scalar product.
 */
public interface HilbertSpace extends NormedSpace {

	/**
	 * the scalar product of two vectors.
	 * @param vec1 the first vector
	 * @param vec2 the second vector
	 * @return the scalar product of vec1 and vec2
	 * @throws Throwable
	 */
	double product(Vector vec1, Vector vec2) throws Throwable;

	@Override
	default double norm(final Vector vec) throws Throwable {
		return Math.sqrt(product(vec, vec));
	}

	/**
	 * Method to project one vector on another.
	 * 
	 * @param w reference vector.
	 * @param v projected vector.
	 * @return projection of v on w.
	 * @throws Throwable
	 */
	Vector projection(Vector w, Vector v) throws Throwable;
	
}
