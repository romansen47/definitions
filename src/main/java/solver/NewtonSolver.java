package solver;

import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.euclidean.vectors.impl.GenericFunction;

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
		return lastVal - (this.function.value(RealLine.getInstance().get(lastVal)).getValue()
				/ this.function.getDerivative().value(RealLine.getInstance().get(lastVal)).getValue());
	}
}
