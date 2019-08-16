package definitions.structures.euclidean.vectors.specialfunctions;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.euclidean.vectors.impl.GenericFunction;

/**
 * Constant function.
 * 
 * @author ro
 *
 */
public abstract class Constant extends GenericFunction {

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
