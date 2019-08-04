package definitions.solver;

import definitions.structures.field.scalar.impl.Real;
import definitions.structures.finitedimensional.real.vectors.impl.GenericFunction;

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
		double newVal = this.doStep(lastVal);
		while (Math.abs(newVal - lastVal) > this.eps) {
			lastVal = newVal;
			newVal = this.doStep(lastVal);
		}
		return newVal;
	}

	private double doStep(double lastVal) throws Throwable {
		return lastVal - (this.function.value(new Real(lastVal)).getValue() / 
				this.function.getDerivative().value(new Real(lastVal)).getValue());
	}
}
