package definitions.structures.abstr;

/**
 * 
 * @author RoManski
 * 
 *         We consider real vector spaces. A vector space is a non-empty
 *         collection of 'things', which can be added and streched.
 */
public interface VectorSpace {

	/**
	 * Not yet implemented.
	 * 
	 * @param vec the vector to check for.
	 * @return whether vec is an element of the space.
	 */
	boolean contains(Vector vec);

	/**
	 * Vector space is not empty.
	 * 
	 * @return the zero vector.
	 */
	Vector nullVec();

	/**
	 * Addition of vectors.
	 * 
	 * @param vec1 summand a.
	 * @param vec2 summand b.
	 * @return the addition of a and b.
	 */
	Vector add(Vector vec1, Vector vec2);

	/**
	 * Scalar Multiplication by real numbers.
	 * 
	 * @param vec1 the vector to stretch.
	 * @param r    the factor.
	 * @return the stretched vector.
	 */
	Vector stretch(Vector vec1, double r);

	/**
	 * For debugging purposes.
	 * 
	 * @return The string.
	 */
	@Override
	String toString();

}
