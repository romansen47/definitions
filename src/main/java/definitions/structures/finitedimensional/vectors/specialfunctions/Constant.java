package definitions.structures.finitedimensional.vectors.specialfunctions;

import definitions.structures.finitedimensional.vectors.impl.GenericFunction;

/**
 * Constant function.
 * 
 * @author ro
 *
 */
public class Constant extends GenericFunction {

	/**
	 * The constant value.
	 */
	final double constantValue;

	/**
	 * Constructor
	 * 
	 * @param value the constant value.
	 */
	public Constant(final double value) {
		this.constantValue = value;
	}

	@Override
	public double value(final double input) {
		return this.constantValue;
	}

	@Override
	public String toString() {
		return "constant " + this.constantValue + "-function ";
	}

}
