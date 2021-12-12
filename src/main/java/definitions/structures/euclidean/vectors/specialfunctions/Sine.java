package definitions.structures.euclidean.vectors.specialfunctions;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
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
		this(a.getDoubleValue(), b.getDoubleValue(), c.getDoubleValue());
	}

	public Sine(final Scalar a, final Scalar b, final Scalar c, final Field field) {
		this.setField(field);
		magnitude = a;
		translation = b;
		frequency = c;
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "x -> " + magnitude + "*sin(" + this.getTranslation() + "+" + this.getFrequency() + "*x) ";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Scalar value(final Scalar input) {
		return this.getField().get(magnitude.getDoubleValue() * Math.sin(this.getTranslation().getDoubleValue()
				+ (this.getFrequency().getDoubleValue() * input.getDoubleValue())));
	}

}
