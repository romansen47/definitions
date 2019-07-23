package definitions.structures.abstr;

import definitions.structures.finitedimensional.field.impl.RealLine;
import definitions.structures.finitedimensional.real.vectors.Real;

/**
 * 
 * @author RoManski
 *
 *         A normed space is a vector space with a norm.
 */
public interface NormedSpace extends VectorSpace {


	@Override
	default Field getField() {
		return RealLine.getRealLine();
	}
	
	/**
	 * The defined norm.
	 * 
	 * @param vec the vector to compute the norm for.
	 * @return the norm of the vector.
	 */
	double norm(Vector vec);

	/**
	 * Any non-zero vector can be normalized. The normalization of a vector is a
	 * vector pointing towards the same direction with norm 1.
	 * 
	 * @param vec the vector to be normalized.
	 * @return the normalized vector.
	 */
	default Vector normalize(final Vector vec) {
		return stretch(vec, new Real(1 / norm(vec)));
	}

}
