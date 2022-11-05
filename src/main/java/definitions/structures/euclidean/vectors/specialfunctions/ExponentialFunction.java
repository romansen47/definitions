package definitions.structures.euclidean.vectors.specialfunctions;

import java.util.Objects;

import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.abstr.algebra.fields.scalars.impl.RealZero;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectors.impl.GenericFunction;

/**
 * Exponential function y=exp(a+b*x).
 *
 * @author ro
 *
 */
public abstract class ExponentialFunction extends GenericFunction {

	/**
	 * the parameter a.
	 */
	private final Scalar a;

	/**
	 * the parameter b.
	 */
	private final Scalar b;

	protected ExponentialFunction() {
		this.a = RealZero.getZero();
		this.b = Generator.getInstance().getFieldGenerator().getRealLine().getOne();
	}

	/**
	 * Constructor.
	 *
	 * @param a the parameter a.
	 * @param b the parameter b.
	 */
	protected ExponentialFunction(final Scalar a, final Scalar b) {
		this.a = a;
		this.b = b;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "x -> exp(" + this.a + "+" + this.b + "*x ";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Scalar value(final Scalar input) {
		return this.getField().get(Math.exp(((Real) this.a).getDoubleValue()
				+ (((Real) this.b).getDoubleValue() * ((Real) input).getDoubleValue())));
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.a, this.b);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj) || (this.getClass() != obj.getClass())) {
			return false;
		}
		ExponentialFunction other = (ExponentialFunction) obj;
		return Objects.equals(this.a, other.a) && Objects.equals(this.b, other.b);
	}
}
