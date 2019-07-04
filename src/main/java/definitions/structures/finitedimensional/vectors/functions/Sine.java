package definitions.structures.finitedimensional.vectors.functions;

import definitions.structures.finitedimensional.vectors.impl.GenericFunction;

public class Sine extends GenericFunction {

	private final double a;
	private final double b;
	private final double c;

	public Sine(double a, double b, double c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	@Override
	public double value(double input) {
		return this.a * Math.sin(this.b + (this.c * input));
	}

	@Override
	public String toString() {
		return "x -> " + this.a + "*sin(" + this.b + "+" + this.c + "*x) ";
	}

}
