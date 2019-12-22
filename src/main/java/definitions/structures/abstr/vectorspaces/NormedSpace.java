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
	/**
	 * {@inheritDoc}
	 */
	@Override
	default Real getDistance(final Vector vec1, final Vector vec2) {
		return this.norm(this.add(vec1, this.stretch(vec2, this.getField().get(-1))));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	Field getField();

	/**
	 * The defined norm.
	 * 
	 * @param vec the vector to compute the norm for.
	 * @return the norm of the vector.
	 */
	Real norm(Vector vec);

	/**
	 * Any non-zero vector can be normalized. The normalization of a vector is a
	 * vector pointing towards the same direction with norm 1.
	 * 
	 * @param vec the vector to be normalized.
	 * @return the normalized vector.
	 */
	default Vector normalize(final Vector vec) {
		return this.stretch(vec, this.norm(vec).getInverse());
	}

}
