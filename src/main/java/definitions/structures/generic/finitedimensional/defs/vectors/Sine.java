package definitions.structures.generic.finitedimensional.defs.vectors;

import definitions.structures.generic.finitedimensional.defs.vectors.impl.FunctionTuple;

public class Sine extends FunctionTuple implements Function {

	final double norm = 1 / Math.sqrt(Math.PI);

	public Sine(final double[] coordinates) throws Throwable {
		super(coordinates);
	}

	@Override
	public double value(final double input) throws Throwable {
		return this.norm * Math.sin(input);
	}

	@Override
	public String toString() {
		return "renormed sine";
	}

}
