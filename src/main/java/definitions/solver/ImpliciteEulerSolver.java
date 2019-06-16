package definitions.solver;

import definitions.structures.generic.finitedimensional.defs.vectors.Function;

public class ImpliciteEulerSolver extends ExpliciteEulerSolver {

	public ImpliciteEulerSolver(Function fun, double initialValue, double eps) {
		super(fun, initialValue, eps);
	}

	@Override
	public Function solve() {
		// TODO:
		return null;
	}

}
