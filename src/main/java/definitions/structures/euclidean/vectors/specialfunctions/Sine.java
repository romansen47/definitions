package definitions.structures.euclidean.vectors.specialfunctions;

import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.fields.scalars.impl.Real;
import definitions.structures.euclidean.vectors.impl.GenericFunction;

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
		return new Real(this.magnitude.getValue()
				* Math.sin(this.getTranslation().getValue() + (this.getFrequency().getValue() * input.getValue())));
	}

	@Override
	public String toString() {
		return "x -> " + this.magnitude + "*sin(" + this.getTranslation() + "+" + this.getFrequency() + "*x) ";
	}

	/**
	 * @return the frequency
	 */
	public Scalar getFrequency() {
		return this.frequency;
	}

	/**
	 * @return the translation
	 */
	public Scalar getTranslation() {
		return this.translation;
	}

}
