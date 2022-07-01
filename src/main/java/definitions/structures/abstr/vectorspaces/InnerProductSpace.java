package definitions.structures.abstr.vectorspaces;

import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Complex;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

/**
 *
 * @author RoManski
 *
 *         A hilbert space is a normed space where the norm is induced by a
 *         scalar product.
 */
public interface InnerProductSpace extends NormedSpace {

	/**
	 * the scalar product of two vectors.
	 *
	 * @param vec1 the first vector
	 * @param vec2 the second vector
	 * @return the scalar product of vec1 and vec2
	 */
	Scalar innerProduct(Vector vec1, Vector vec2);

	/**
	 * the norm in an inner product space is induced by the inner product
	 */
	@Override
	default Scalar norm(final Vector vec) {
		Scalar innerProduct = innerProduct(vec, vec);
		if (innerProduct instanceof Complex) {
			return RealLine.getInstance()
					.get(Math.sqrt(((Real) ((Complex) innerProduct(vec, vec)).getReal()).getDoubleValue()));
		}
//		if (innerProduct instanceof Quaternion) {
//			return RealLine.getInstance().get(Math.sqrt(((Real) ((Quaternion) innerProduct(vec, vec)).getReal()).getDoubleValue()));
//		}
		return RealLine.getInstance().get(Math.sqrt(((Real) innerProduct).getDoubleValue()));
	}

	/**
	 * Method to project one vector onto another.
	 *
	 * @param w reference vector.
	 * @param v projected vector.
	 * @return projection of v on w.
	 */
	default Vector projection(final Vector w, final Vector v) {
		return stretch(v, innerProduct(w, v));
	}

}
