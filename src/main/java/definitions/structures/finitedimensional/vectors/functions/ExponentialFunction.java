package definitions.structures.finitedimensional.vectors.functions;

import definitions.structures.finitedimensional.vectors.impl.GenericFunction;

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
	final private double a;

	/**
	 * the parameter b.
	 */
	final private double b;

	/**
	 * Constructor.
	 * 
	 * @param a the parameter a.
	 * @param b the parameter b.
	 */
	public ExponentialFunction(double a, double b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public double value(double input) {
		return Math.exp(this.a + (this.b * input));
	}

	@Override
	public String toString() {
		return "x -> exp(" + this.a + "+" + this.b + "*x ";
	}
}
