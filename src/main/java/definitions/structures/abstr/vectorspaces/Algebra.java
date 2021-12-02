package definitions.structures.abstr.vectorspaces;

import definitions.structures.abstr.vectorspaces.vectors.Vector;

/**
 *
 * @author ro
 *
 *         An algebra is a vector space with an additional associative mapping
 *         (Vector,Vector) - Vector.
 *
 */
public interface Algebra extends VectorSpace, Ring {

	/**
	 * an algebra A is a vector space with an operation AxA-A
	 *
	 * @param vec1 first argument
	 * @param vec2 second argument
	 * @return the product
	 */
	Vector product(final Vector vec1, final Vector vec2);

}
