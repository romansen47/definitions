package definitions.structures.abstr.vectorspaces;

import definitions.structures.abstr.groups.Monoid;
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

	@Override
	Monoid getMuliplicativeMonoid();

	Vector product(Vector vec1, Vector vec2);

	Vector getOne();

}
