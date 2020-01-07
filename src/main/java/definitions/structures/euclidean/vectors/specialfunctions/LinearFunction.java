package definitions.structures.euclidean.vectors.specialfunctions;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.euclidean.vectors.impl.GenericFunction;

public abstract class LinearFunction extends GenericFunction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1217714308801686785L;
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
		return RealLine.getInstance();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Scalar value(final Scalar input) {
		return this.getField().get(this.a.getDoubleValue() + (this.b.getDoubleValue() * input.getDoubleValue()));
	}

}
