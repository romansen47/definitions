package definitions.structures.abstr.vectorspaces;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

/**
 * 
 * @author RoManski
 *
 *         A normed space is a vector space with a norm.
 */
public interface NormedSpace extends VectorSpace, MetricSpace {

	@Override
	Field getField();

	/**
	 * The defined norm.
	 * 
	 * @param vec the vector to compute the norm for.
	 * @return the norm of the vector.
	 */
	Real norm(Vector vec);

	@Override
	default Real getDistance(Vector vec1, Vector vec2) {
		return norm(add(vec1, stretch(vec2, new Real(-1))));
	}

	/**
	 * Any non-zero vector can be normalized. The normalization of a vector is a
	 * vector pointing towards the same direction with norm 1.
	 * 
	 * @param vec the vector to be normalized.
	 * @return the normalized vector.
	 */
	default Vector normalize(final Vector vec) {
		return stretch(vec, norm(vec).getInverse());
	}

}
