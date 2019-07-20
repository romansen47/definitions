package definitions.structures.finitedimensional.vectors.specialfunctions;

import definitions.structures.finitedimensional.vectors.impl.GenericFunction;

public class LinearFunction extends GenericFunction {

	final double a;
	final double b;

	public LinearFunction(double a, double b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public double value(double input) {
		return this.a + (this.b * input);
	}

	@Override
	public String toString() {
		return "x -> " + this.a + "+" + this.b + "*x ";
	}

}
