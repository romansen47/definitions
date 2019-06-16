package definitions.structures.generic.finitedimensional.defs.vectors;

import definitions.structures.generic.finitedimensional.defs.vectors.impl.FunctionTuple;

public class ExponentialFunction extends FunctionTuple implements Function {

	public ExponentialFunction(final double[] coordinates) throws Throwable {
		super(coordinates);
	}

	@Override
	public double value(final double input) {
		return Math.exp(input);
	}

}
