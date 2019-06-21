package definitions.structures.abstr;

/**
 * 
 * @author RoManski
 *
 * A normed space is a vector space with a norm.
 */
public interface NormedSpace extends VectorSpace {

	/**
	 * The defined norm.
	 * @param vec the vector to compute the norm for.
	 * @return the norm of the vector.
	 * @throws Throwable
	 */
	double norm(Vector vec) throws Throwable;

	/**
	 * Any non-zero vector can be normalized. The normalization of a vector is a vector pointing towards the same direction with norm 1.
	 * @param vec the vector to be normalized.
	 * @return the normalized vector.
	 * @throws Throwable
	 */
	default Vector normalize(final Vector vec) throws Throwable {
		return stretch(vec, 1 / norm(vec));
	}

}
