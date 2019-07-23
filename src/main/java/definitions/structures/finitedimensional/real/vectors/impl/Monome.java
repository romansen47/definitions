package definitions.structures.finitedimensional.real.vectors.impl;

import definitions.structures.abstr.Scalar;
import definitions.structures.finitedimensional.real.vectors.Real;

public class Monome extends GenericFunction {

	final int degree;

	public Monome(int degree) {
		super();
		this.degree = degree;
	}

	@Override
	public Scalar value(Scalar input) {
		return new Real(Math.pow(input.getValue(), this.degree));
	}

	@Override
	public String toString() {
		return "Monome of degree " + this.degree;
	}

}
