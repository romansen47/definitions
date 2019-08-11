package definitions.structures.finitedimensional.vectors.specialfunctions;

import definitions.structures.abstr.Scalar;
import definitions.structures.field.scalar.impl.Real;
import definitions.structures.finitedimensional.vectors.impl.GenericFunction;

public class LinearFunction extends GenericFunction {

	final Scalar a;
	final Scalar b;

	public LinearFunction(Scalar a, Scalar b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public Scalar value(Scalar input) {
		return new Real(this.a.getValue() + (this.b.getValue() * input.getValue()));
	}

	@Override
	public String toString() {
		return "x -> " + this.a + "+" + this.b + "*x ";
	}

}
