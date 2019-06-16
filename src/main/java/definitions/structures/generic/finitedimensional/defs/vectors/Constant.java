package definitions.structures.generic.finitedimensional.defs.vectors;

import definitions.structures.generic.finitedimensional.defs.vectors.impl.FunctionTuple;

public class Constant extends FunctionTuple {

	final double value;

	public Constant(final double[] coordinates, final double value) throws Throwable {
		super(coordinates);
		this.value = value;
	}

	@Override
	public double value(final double input) {
		return this.value;
	}

	@Override
	public String toString() {
		return "constant " + this.value + "-function ";
	}
}
