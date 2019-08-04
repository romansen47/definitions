package definitions.structures.finitedimensional.real.vectors.specialfunctions;

import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.impl.RealZero;
import definitions.structures.field.impl.RealLine;
import definitions.structures.field.scalar.impl.Real;
import definitions.structures.finitedimensional.real.vectors.impl.GenericFunction;

/**
 * Exponential function y=exp(a+b*x).
 * 
 * @author ro
 *
 */
public class ExponentialFunction extends GenericFunction {

	/**
	 * the parameter a.
	 */
	final private Scalar a;

	/**
	 * the parameter b.
	 */
	final private Scalar b;

	/**
	 * Constructor.
	 * 
	 * @param a the parameter a.
	 * @param b the parameter b.
	 */
	public ExponentialFunction(Scalar a, Scalar b) {
		this.a = a;
		this.b = b;
	}

	public ExponentialFunction() {
		this.a=RealZero.getZero();
		this.b=RealLine.getInstance().getOne();
	}

	@Override
	public Scalar value(Scalar input) {
		return new Real(Math.exp(this.a.getValue() + (this.b.getValue() * input.getValue())));
	}

	@Override
	public String toString() {
		return "x -> exp(" + this.a + "+" + this.b + "*x ";
	}
}
