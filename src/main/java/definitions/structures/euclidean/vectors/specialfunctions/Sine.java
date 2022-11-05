package definitions.structures.euclidean.vectors.specialfunctions;

import java.util.Objects;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectors.impl.GenericFunction;

public abstract class Sine extends GenericFunction {

	private final Scalar magnitude;
	private final Scalar translation;
	private final Scalar frequency;

	public Sine(final double a, final double b, final double c) {
		/*
		 * by default sine is defined
		 */
		this(Generator.getInstance().getFieldGenerator().getRealLine().get(a),
				Generator.getInstance().getFieldGenerator().getRealLine().get(b),
				Generator.getInstance().getFieldGenerator().getRealLine().get(c),
				Generator.getInstance().getFieldGenerator().getRealLine());
	}

	public Sine(final Scalar a, final Scalar b, final Scalar c) {
		this(((Real) a).getDoubleValue(), ((Real) b).getDoubleValue(), ((Real) c).getDoubleValue());
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "x -> " + this.magnitude + "*sin(" + this.getTranslation() + "+" + this.getFrequency() + "*x) ";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Scalar value(final Scalar input) {
		return this.getField()
				.get(((Real) this.magnitude).getDoubleValue() * Math.sin(((Real) this.getTranslation()).getDoubleValue()
						+ (((Real) this.getFrequency()).getDoubleValue() * ((Real) input).getDoubleValue())));
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.frequency, this.magnitude, this.translation);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj) || (this.getClass() != obj.getClass())) {
			return false;
		}
		Sine other = (Sine) obj;
		return Objects.equals(this.frequency, other.frequency) && Objects.equals(this.magnitude, other.magnitude)
				&& Objects.equals(this.translation, other.translation);
	}

}
