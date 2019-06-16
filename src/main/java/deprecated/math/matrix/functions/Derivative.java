package deprecated.math.matrix.functions;

import deprecated.math.matrix.IMatrix;
import deprecated.math.matrix.Identity;

public class Derivative implements IDerivative {

	final private IFunction function;

	public Derivative(final IFunction function) {
		this.function = function;
	}

	@Override
	public IFunction jacobi(final IMatrix mat) {
		return new Function() {
			@Override
			public IMatrix value(final IMatrix input) throws Exception {
				return (Derivative.this.function.value(mat.add(input.scaleBy(eps)))
						.add((Derivative.this.function.value(mat)).scaleBy(-1))).scaleBy(Math.pow(eps, -1));
			}
		};
	}

	@Override
	public IMatrix jacobiMatrix(final IMatrix mat) throws Exception {
		final IFunction jacobi = this.jacobi(mat);
		final IMatrix vector = jacobi.value(new Identity(mat.getLength()));
		return vector;
	}

	/**
	 * @return the function
	 */
	public IFunction getFunction() {
		return this.function;
	}

}
