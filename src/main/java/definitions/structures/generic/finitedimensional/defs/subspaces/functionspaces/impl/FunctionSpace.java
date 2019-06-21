package definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.impl;

import definitions.structures.abstr.VectorSpace;

import definitions.structures.generic.finitedimensional.defs.vectors.Function;

/**
 * 
 * @author RoManski
 * 
 * A function space knows what an integral is. It is defined on an intervall and carries an
 * small parameter eps due to approximative methods.
 */
public interface FunctionSpace extends VectorSpace {

	/**
	 * Function spaces are defined on finite intervalls.
	 * @return the intervall
	 */
	double[] getInterval();

	/**
	 * Function spaces use finite dimensional approximations.
	 * @return the correctness parameter.
	 */
	double getEpsilon();

	/**
	 * General static integral method for the product of two functions.
	 * @param vec1 first function.
	 * @param vec2 second function.
	 * @param left the interval is [left,right].
	 * @param right the interval is [left,right].
	 * @param eps the correctness parameter..
	 * @return the integral over vec1*vec2.
	 * @throws Throwable
	 */
	static double getIntegral(final Function vec1, final Function vec2, double left, double right, double eps)
			throws Throwable {
		double ans = 0;
		double x = left;
		while (x < right) {
			ans += vec1.value(x) * vec2.value(x);
			x += eps;
		}
		return ans * eps;
	}

	/**
	 * Concrete scalar product.
	 * @param vec1 first function.
	 * @param vec2 second function.
	 * @return the integral over vec1*vec2.
	 * @throws Throwable
	 */
	default double getIntegral(final Function vec1, final Function vec2) throws Throwable {
		return getIntegral(vec1,vec2,getInterval()[0],getInterval()[1],getEpsilon());
	}

}
