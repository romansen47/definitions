/**
 *
 */
package deprecated.math.matrix.regression;

import deprecated.math.matrix.IMatrix;
import deprecated.math.matrix.Matrix;
import deprecated.math.matrix.functions.Derivative;
import deprecated.math.matrix.functions.IDerivative;
import deprecated.math.matrix.functions.IFunction;

/**
 * @author RoManski
 *
 */
public class GradientMinimizer implements IMinimizer {

	final double stepsize;
	final double precission;

	public GradientMinimizer(final double eps, final double precission) {
		this.stepsize = eps;
		this.precission = precission;
	}

	@Override
	public IMatrix find(final IFunction function, final IMatrix init) throws Exception {
		double norm = Double.MAX_VALUE;
		final IMatrix tmp = new Matrix(init.getValues().clone());
		final IDerivative derivative = new Derivative(function);
		final IMatrix direction = derivative.jacobiMatrix(tmp);
		while (norm > this.precission) {
			norm = this.goAlongMinimizingDirection(derivative, tmp, direction.scaleBy(-1));
		}
		return tmp;
	}

	private double goAlongMinimizingDirection(final IDerivative derivative, IMatrix tmp, final IMatrix direction)
			throws Exception {
		final IMatrix delta = derivative.jacobi(tmp).value(direction.scaleBy(-1));
		tmp = tmp.add(delta.toUnit().scaleBy(this.getStepsize()));
		return tmp.norm();
	}

	/**
	 * @return the stepsize
	 */
	public final double getStepsize() {
		return this.stepsize;
	}

	/**
	 * @return the precission
	 */
	public double getPrecission() {
		return this.precission;
	}

}
