package definitions.structures.finitedimensional.real.vectors.specialfunctions;

import definitions.structures.abstr.Scalar;
import definitions.structures.field.scalar.impl.Real;
import definitions.structures.finitedimensional.real.vectors.impl.GenericFunction;

public class Sine extends GenericFunction {

	private final Scalar magnitude;
	private final Scalar translation;
	private final Scalar frequency;

	public Sine(Scalar a, Scalar b, Scalar c) {
		this.magnitude = a;
		this.translation = b;
		this.frequency = c;
	}

	public Sine(double a, double b, double c) {
		this.magnitude = new Real(a);
		this.translation = new Real(b);
		this.frequency = new Real(c);
	}

	@Override
	public Scalar value(Scalar input) {
		return new Real(this.magnitude.getValue() * Math.sin(this.getTranslation().getValue() + (this.getFrequency().getValue() * input.getValue())));
	}

	@Override
	public String toString() {
		return "x -> " + this.magnitude + "*sin(" + this.getTranslation() + "+" + this.getFrequency() + "*x) ";
	}

	/**
	 * @return the frequency
	 */
	public Scalar getFrequency() {
		return frequency;
	}

	/**
	 * @return the translation
	 */
	public Scalar getTranslation() {
		return translation;
	}

}
