package definitions.structures.finitedimensional.real.vectors.specialfunctions;

import definitions.structures.abstr.Scalar;
import definitions.structures.field.scalar.Real;
import definitions.structures.finitedimensional.real.vectors.impl.GenericFunction;

public class Sine extends GenericFunction {

	private final Scalar a;
	private final Scalar b;
	private final Scalar c;

	public Sine(Scalar a, Scalar b, Scalar c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	public Sine(double a, double b, double c) {
		this.a = new Real(a);
		this.b = new Real(b);
		this.c = new Real(c);
	}

	@Override
	public Scalar value(Scalar input) {
		return new Real(
				this.a.getValue() * Math.sin(this.b.getValue() + (this.c.getValue() * input.getValue())));
	}

	@Override
	public String toString() {
		return "x -> " + this.a + "*sin(" + this.b + "+" + this.c + "*x) ";
	}

}
