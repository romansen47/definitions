package definitions.structures.abstr.vectorspaces;

import definitions.Unweavable;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

/**
 * 
 * @author RoManski
 * 
 *         A function space knows what an integral is. It is defined on an
 *         interval and carries an small parameter eps due to approximative
 *         methods.
 */
public interface FunctionSpace extends VectorSpace, Unweavable {

	/**
	 * Function spaces use finite dimensional approximations.
	 * 
	 * @return the correctness parameter.
	 */
	double getEpsilon();

	/**
	 * General static integral method for the product of two functions.
	 * 
	 * @param vec1  first function.
	 * @param vec2  second function.
	 * @param left  the interval is [left,right].
	 * @param right the interval is [left,right].
	 * @param eps   the correctness parameter..
	 * @return the integral over vec1*vec2.
	 */
	default Scalar getIntegral(final Function vec1, final Function vec2, final double left, final double right,
			final double eps) {
		Scalar ans = (Scalar) this.getField().nullVec();
		final Scalar newEps = this.getField().get(eps);
		Scalar x = this.getField().get(left);
		final Scalar epsNew = this.getField().get(eps);
		while (x.getValue() < right) {
			final Vector tmp1 = vec1.value(x);
			final Vector tmp2 = vec2.value(x);
			ans = (Scalar) (this.getField().add(ans, this.getField()
					.product(this.getField().product(tmp1, this.getField().conjugate((Scalar) tmp2)), epsNew)));
			x = (Scalar) this.getField().add(x, newEps);
		}
		return ans;
	}

	/**
	 * Function spaces are defined on finite intervals.
	 * 
	 * @return the interval
	 */
	double[] getInterval();

	/**
	 * getter for inf of the interval.
	 * 
	 * @return the inf of the interval.
	 */
	double getLeft();

	/**
	 * Getter for the sup of the interval.
	 * 
	 * @return the sup of the interval.
	 */
	double getRight();

	/**
	 * Concrete scalar product.
	 * 
	 * @param vec1 first function.
	 * @param vec2 second function.
	 * @return the integral over vec1*vec2.
	 */
	default Scalar integral(final Function vec1, final Function vec2) {
		return this.getIntegral(vec1, vec2, this.getInterval()[0], this.getInterval()[1], this.getEpsilon());
	}

}
