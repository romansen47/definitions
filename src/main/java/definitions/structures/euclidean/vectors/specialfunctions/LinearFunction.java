package definitions.structures.euclidean.vectors.specialfunctions;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectors.impl.GenericFunction;

public abstract class LinearFunction extends GenericFunction {

	final Scalar a;
	final Scalar b;

	public LinearFunction(final Scalar a, final Scalar b) {
		this.a = a;
		this.b = b;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "x -> " + this.a + "+" + this.b + "*x ";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field getField() {
		return Generator.getInstance().getFieldGenerator().getRealLine();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Scalar value(final Scalar input) {
		return this.getField().get(((Real) this.a).getDoubleValue()
				+ (((Real) this.b).getDoubleValue() * ((Real) input).getDoubleValue()));
	}

}
