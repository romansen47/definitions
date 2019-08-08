package definitions.solver;

import definitions.structures.abstr.Scalar;
import definitions.structures.field.scalar.impl.Real;
import definitions.structures.finitedimensional.real.vectors.Function;
import definitions.structures.finitedimensional.real.vectors.impl.GenericFunction;

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
			public Scalar value(final Scalar input) {
				double ans = ExpliciteEulerSolver.this.initialData;
				double xval = -Math.PI;
				while (xval < input.getValue()) {
					ans += ExpliciteEulerSolver.this.eps
							* ExpliciteEulerSolver.this.fun.value(new Real(xval)).getValue();
					xval += ExpliciteEulerSolver.this.eps;
				}
				return new Real(ans);
			}
		};
	}
}
