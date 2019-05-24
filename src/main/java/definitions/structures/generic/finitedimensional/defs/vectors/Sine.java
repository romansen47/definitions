package definitions.structures.generic.finitedimensional.defs.vectors;

import definitions.structures.generic.finitedimensional.defs.vectors.impl.Tuple;

public class Sine extends Tuple implements Function {

	public Sine(double[] coordinates) throws Throwable {
		super(coordinates);
	}

	@Override
	public double value(double input) throws Throwable {
		return Math.sin(input);
	}

	@Override
	public String toString() {
		return "sine function";
	}

}
