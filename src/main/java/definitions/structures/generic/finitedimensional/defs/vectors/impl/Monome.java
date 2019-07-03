package definitions.structures.generic.finitedimensional.defs.vectors.impl;

public class Monome extends GenericFunction {

	final int degree;

	public Monome(int degree) {
		super();
		this.degree = degree;
	}

	@Override
	public double value(double input) {
		return Math.pow(input, this.degree);
	}

	@Override
	public String toString() {
		return "Monome of degree " + this.degree;
	}

}
