package math.matrix.functions;

import math.matrix.IMatrix;
import math.matrix.Identity;

public class Derivative implements IDerivative {

	final private IFunction function;

	public Derivative(IFunction function) {
		this.function = function;
	}

	@Override
	public IFunction jacobi(IMatrix mat) {
		return new Function() {
			@Override
			public IMatrix value(IMatrix input) throws Exception {
				return (function.value(mat.add(input.scaleBy(eps))).add((function.value(mat)).scaleBy(-1)))
						.scaleBy(Math.pow(eps, -1));
			}
		};
	}

	@Override
	public IMatrix jacobiMatrix(IMatrix mat) throws Exception {
		final IFunction jacobi = jacobi(mat);
		final IMatrix vector = jacobi.value(new Identity(mat.getLength()));
		return vector;
	}

	/**
	 * @return the function
	 */
	public IFunction getFunction() {
		return function;
	}

}
