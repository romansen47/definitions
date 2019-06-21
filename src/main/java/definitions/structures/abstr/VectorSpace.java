package definitions.structures.abstr;

/**
 * 
 * @author RoManski
 *	
 * We consider real vector spaces. A vector space is a non-empty collection of 'things', which can be added and streched.
 */
public interface VectorSpace {

	/**
	 * Not yet implemented.
	 * @param vec the vector to check for.
	 * @return 
	 * @throws Throwable
	 */
	boolean contains(Vector vec) throws Throwable;

	/**
	 * Vector space is not empty.
	 * @return the zero vector.
	 * @throws Throwable
	 */
	Vector nullVec() throws Throwable;

	/**
	 * Addition of vectors.
	 * @param vec1 summand a.
	 * @param vec2 summand b.
	 * @return the addition of a and b
	 * @throws Throwable
	 */
	Vector add(Vector vec1, Vector vec2) throws Throwable;

	/**
	 * Scalar Multiplication by real numbers.
	 * @param vec1 the vector to stretch.
	 * @param r the factor.
	 * @return the stretched vector
	 * @throws Throwable
	 */
	Vector stretch(Vector vec1, double r) throws Throwable;

	/**
	 * For debugging purposes.
	 * @return The string.
	 */
	@Override
	String toString();

}
