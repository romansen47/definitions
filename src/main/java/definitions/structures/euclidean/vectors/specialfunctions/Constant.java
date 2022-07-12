package definitions.structures.euclidean.vectors.specialfunctions;

import java.util.Objects;

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
	 * The constant value.
	 */
	protected final Scalar constantValue;

	/**
	 * Constructor
	 *
	 * @param value the constant value.
	 */
	protected Constant(final Scalar value) {
		this.constantValue = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "constant " + this.getConstantValue() + "-function ";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Scalar value(final Scalar input) {
		return this.getConstantValue();
	}

	/**
	 * getter for the value
	 *
	 * @return the constant value
	 */
	public Scalar getConstantValue() {
		return this.constantValue;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.constantValue);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj) || (this.getClass() != obj.getClass())) {
			return false;
		}
		Constant other = (Constant) obj;
		return Objects.equals(this.constantValue, other.constantValue);
	}

}
