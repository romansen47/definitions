package definitions.structures.euclidean.vectors.specialfunctions;

import java.util.Objects;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.euclidean.vectors.impl.GenericFunction;

public abstract class Sine extends GenericFunction {

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
	}

	public Sine(final Scalar a, final Scalar b, final Scalar c) {
		this(((Real) a).getDoubleValue(), ((Real) b).getDoubleValue(), ((Real) c).getDoubleValue());
	}

	public Sine(final Scalar a, final Scalar b, final Scalar c, final Field field) {
		setField(field);
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
		return "x -> " + magnitude + "*sin(" + getTranslation() + "+" + getFrequency() + "*x) ";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Scalar value(final Scalar input) {
		return getField().get(((Real) magnitude).getDoubleValue() * Math.sin(((Real) getTranslation()).getDoubleValue()
				+ (((Real) getFrequency()).getDoubleValue() * ((Real) input).getDoubleValue())));
	}

	@Override
	public int hashCode() {
		return Objects.hash(frequency, magnitude, translation);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sine other = (Sine) obj;
		return Objects.equals(frequency, other.frequency) && Objects.equals(magnitude, other.magnitude)
				&& Objects.equals(translation, other.translation);
	}

}
