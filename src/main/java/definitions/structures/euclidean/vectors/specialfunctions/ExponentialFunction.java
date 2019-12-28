package definitions.structures.euclidean.vectors.specialfunctions;

import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.RealZero;
import definitions.structures.euclidean.vectors.impl.GenericFunction;

/**
 * Exponential function y=exp(a+b*x).
 * 
 * @author ro
 *
 */
public abstract class ExponentialFunction extends GenericFunction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1297369141413774111L;

	/**
	 * the parameter a.
	 */
	final private Scalar a;

	/**
	 * the parameter b.
	 */
	final private Scalar b;

	public ExponentialFunction() {
		this.a = RealZero.getZero();
		this.b = RealLine.getInstance().getOne();
	}

	/**
	 * Constructor.
	 * 
	 * @param a the parameter a.
	 * @param b the parameter b.
	 */
	public ExponentialFunction(final Scalar a, final Scalar b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public String toString() {
		return "x -> exp(" + this.a + "+" + this.b + "*x ";
	}

	@Override
	public Scalar value(final Scalar input) {
		return this.getField().get(Math.exp(this.a.getValue() + (this.b.getValue() * input.getValue())));
	}
}
