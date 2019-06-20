package definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.impl;

import definitions.structures.abstr.VectorSpace;

import definitions.structures.generic.finitedimensional.defs.vectors.Function;

public interface FunctionSpace extends VectorSpace {

	double[] getIntervall();

	double getEpsilon();

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

	default double getIntegral(final Function vec1, final Function vec2) throws Throwable {
		return getIntegral(vec1,vec2,getIntervall()[0],getIntervall()[1],getEpsilon());
	}

}
