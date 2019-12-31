package definitions.structures.abstr.vectorspaces;

import definitions.structures.abstr.vectorspaces.vectors.Vector;

/**
 * 
 * @author ro
 *
 *         An algebra is a vector space with an additional associative mapping
 *         (Vector,Vector) -> Vector.
 *
 */
public interface Algebra extends VectorSpace, Ring {

	Vector product(Vector vec1, Vector vec2);

}
