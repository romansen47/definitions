package definitions.solver;

import definitions.structures.generic.finitedimensional.defs.vectors.impl.GenericFunction;

public class NewtonSolver {

	final double initialData;
	final double eps;
	final GenericFunction function;

	public NewtonSolver(double init, double eps, GenericFunction fun) {
		this.initialData = init;
		this.eps = eps;
		this.function = fun;
	}

	public double solve() throws Throwable {
		double lastVal = this.initialData;
		double newVal = doStep(lastVal);
		while (Math.abs(newVal - lastVal) > this.eps) {
			lastVal = newVal;
			newVal = doStep(lastVal);
		}
		return newVal;
	}

	private double doStep(double lastVal) throws Throwable {
		return lastVal - this.function.value(lastVal) / this.function.getDerivative().value(lastVal);
	}
}
