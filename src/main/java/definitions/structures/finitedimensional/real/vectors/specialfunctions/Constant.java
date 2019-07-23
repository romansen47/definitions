package definitions.structures.finitedimensional.real.vectors.specialfunctions;

import definitions.structures.abstr.Scalar;
import definitions.structures.finitedimensional.real.vectors.impl.GenericFunction;

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
	final Scalar constantValue;

	/**
	 * Constructor
	 * 
	 * @param value the constant value.
	 */
	public Constant(final Scalar value) {
		this.constantValue = value;
	}

	@Override
	public Scalar value(final Scalar input) {
		return this.constantValue;
	}

	@Override
	public String toString() {
		return "constant " + this.constantValue + "-function ";
	}

}
