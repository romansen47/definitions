package definitions.structures.euclidean.vectors.specialfunctions;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.euclidean.vectors.impl.GenericFunction;

public abstract class Sine extends GenericFunction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4429300404671715544L;
	private final Scalar magnitude;
	private final Scalar translation;
	private final Scalar frequency;

	public Sine(final double a, final double b, final double c) {
		/*
		 * by default sine is defined
		 */
		this(RealLine.getInstance().get(a), RealLine.getInstance().get(b), RealLine.getInstance().get(c),
				RealLine.getInstance());

//		this.setField(RealLine.getInstance());
//		this.magnitude = this.getField().get(a);
//		this.translation = this.getField().get(b);
//		this.frequency = this.getField().get(c);
	}

	public Sine(final Scalar a, final Scalar b, final Scalar c) {
		this(a.getValue(), b.getValue(), c.getValue());
	}

	public Sine(final Scalar a, final Scalar b, final Scalar c, final Field field) {
		this.setField(field);
		this.magnitude = a;
		this.translation = b;
		this.frequency = c;
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

	@Override
	public String toString() {
		return "x -> " + this.magnitude + "*sin(" + this.getTranslation() + "+" + this.getFrequency() + "*x) ";
	}

	@Override
	public Scalar value(final Scalar input) {
		return this.getField().get(this.magnitude.getValue()
				* Math.sin(this.getTranslation().getValue() + (this.getFrequency().getValue() * input.getValue())));
	}

}
