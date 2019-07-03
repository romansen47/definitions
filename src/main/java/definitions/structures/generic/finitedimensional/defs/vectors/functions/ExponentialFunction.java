package definitions.structures.generic.finitedimensional.defs.vectors.functions;

import definitions.structures.generic.finitedimensional.defs.vectors.impl.GenericFunction;

public class ExponentialFunction extends GenericFunction {

	final private double a;
	final private double b;

	public ExponentialFunction(double a, double b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public double value(double input) {
		return Math.exp(this.a + (this.b * input));
	}

	@Override
	public String toString() {
		return "x -> exp(" + this.a + "+" + this.b + "*x ";
	}
}
