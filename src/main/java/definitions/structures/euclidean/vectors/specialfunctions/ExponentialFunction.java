package definitions.structures.euclidean.vectors.specialfunctions;

import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
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
		a = RealZero.getZero();
		b = RealLine.getInstance().getOne();
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "x -> exp(" + a + "+" + b + "*x ";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Scalar value(final Scalar input) {
		return getField().get(Math.exp(((Real) a).getDoubleValue() + (((Real) b).getDoubleValue() * ((Real) input).getDoubleValue())));
	}
}
