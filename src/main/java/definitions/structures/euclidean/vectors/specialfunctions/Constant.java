package definitions.structures.euclidean.vectors.specialfunctions;

import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.euclidean.vectors.impl.GenericFunction;

/**
 * Constant function.
 *
 * @author ro
 *
 */
public abstract class Constant extends GenericFunction {

	/**
	 *
	 */
	private static final long serialVersionUID = -8572553792866612998L;
	/**
	 * The constant value.
	 */
	private final Scalar constantValue;

	/**
	 * Constructor
	 *
	 * @param value the constant value.
	 */
	public Constant(final Scalar value) {
		constantValue = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "constant " + getConstantValue() + "-function ";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Scalar value(final Scalar input) {
		return getConstantValue();
	}

	public Scalar getConstantValue() {
		return constantValue;
	}

}
