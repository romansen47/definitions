package definitions.structures.abstr.vectorspaces;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import exceptions.DevisionByZeroException;

/**
 *
 * @author RoManski
 *
 *         A normed space N is a vector space over a field F which is also a
 *         metric space with a mapping FxN-|R. The distance function is induced
 *         by the norm.
 */
public interface NormedSpace extends VectorSpace, MetricSpace {

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Real distance(final Vector vec1, final Vector vec2) {
		return (Real) this.norm(this.addition(vec1, this.stretch(vec2, (Scalar) this.getField().getMinusOne())));
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
	Scalar norm(Vector vec);

	/**
	 * Any non-zero vector can be normalized. The normalization of a vector is a
	 * vector pointing towards the same direction with norm 1.
	 *
	 * @param vec the vector to be normalized.
	 * @return the normalized vector. *
	 * @throws DevisionByZeroException if devision by zero occures
	 */
	default Vector normalize(final Vector vec) throws DevisionByZeroException {
		Vector norm = this.norm(vec);
		return this.stretch(vec, this.getField().getInverseElement(norm));
	}

}
