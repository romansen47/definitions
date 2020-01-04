package definitions.structures.euclidean.vectors.impl;

import definitions.structures.abstr.algebra.fields.scalars.Scalar;

public abstract class Monome extends GenericFunction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6765574043986345237L;
	final int degree;

	public Monome(final int degree) {
		super();
		this.degree = degree;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Monome of degree " + this.degree;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Scalar value(final Scalar input) {
		return this.getField().get(Math.pow(input.getValue(), this.degree));
	}

}
