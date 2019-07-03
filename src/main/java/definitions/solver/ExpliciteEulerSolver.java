package definitions.solver;

import definitions.structures.generic.finitedimensional.defs.vectors.Function;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.GenericFunction;

public class ExpliciteEulerSolver implements Solver {

	public Function fun;
	double initialData;
	final double eps;

	public ExpliciteEulerSolver(final Function fun, final double initialValue, final double eps) {
		this.fun = fun;
		this.initialData = initialValue;
		this.eps = eps;
	}

	@Override
	public Function solve() {
		return new GenericFunction() {
			@Override
			public double value(final double input) {
				double ans = ExpliciteEulerSolver.this.initialData;
				double xval = -Math.PI;
				while (xval < input) {
					ans += ExpliciteEulerSolver.this.eps * ExpliciteEulerSolver.this.fun.value(xval);
					xval += ExpliciteEulerSolver.this.eps;
				}
				return ans;
			}
		};
	}
}
