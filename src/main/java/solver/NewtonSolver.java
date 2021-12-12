package solver;

import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.euclidean.vectors.impl.GenericFunction;

public class NewtonSolver {

	final double initialData;
	final double eps;
	final GenericFunction function;

	public NewtonSolver(final double init, final double eps, final GenericFunction fun) {
		initialData = init;
		this.eps = eps;
		function = fun;
	}

	private double doStep(final double lastVal) throws Throwable {
		return lastVal - (((Scalar) function.value(RealLine.getInstance().get(lastVal))).getDoubleValue()
				/ ((Scalar) function.getDerivative().value(RealLine.getInstance().get(lastVal))).getDoubleValue());
	}

	public double solve() throws Throwable {
		double lastVal = initialData;
		double newVal = this.doStep(lastVal);
		while (Math.abs(newVal - lastVal) > eps) {
			lastVal = newVal;
			newVal = this.doStep(lastVal);
		}
		return newVal;
	}
}
