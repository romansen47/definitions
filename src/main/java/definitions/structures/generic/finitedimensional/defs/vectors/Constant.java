package definitions.structures.generic.finitedimensional.defs.vectors;

import definitions.structures.generic.finitedimensional.defs.vectors.impl.FunctionTuple;

public class Constant extends FunctionTuple {

	final double value;

	public Constant(double[] coordinates, double value) throws Throwable {
		super(coordinates);
		this.value = value;
	}

	@Override
	public double value(double input) {
		return value;
	}

	@Override
	public String toString() {
		return "constant " + value + "-function ";
	}
}
