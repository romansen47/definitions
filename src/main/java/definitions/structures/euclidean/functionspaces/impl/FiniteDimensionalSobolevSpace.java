package definitions.structures.euclidean.functionspaces.impl;

import java.util.List;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.mappings.impl.DerivativeOperator;
import definitions.structures.euclidean.mappings.impl.FiniteDimensionalDerivativeOperator;
import exceptions.DevisionByZeroException;

/**
 * Concrete implementation of a finite dimensional sobolev function space.
 *
 * @author ro
 *
 */
public class FiniteDimensionalSobolevSpace extends FiniteDimensionalFunctionSpace {

	/**
	 *
	 */
	private static final long serialVersionUID = 2547484050898391066L;
	private DerivativeOperator derivativeBuilder;

	/**
	 * The sobolev degree.
	 */
	private Integer degree;

	/**
	 * Constructor. Converts function space to sobolev space.
	 *
	 * @param field  the base field
	 * @param space  the function space.
	 * @param degree the sobolev degree of the converted space.
	 * @param ortho  tells if base should be normalized
	 * @throws DevisionByZeroException if devision by zero occures
	 */
	public FiniteDimensionalSobolevSpace(final Field field, final EuclideanFunctionSpace space, final int degree,
			final boolean ortho) throws DevisionByZeroException {
		super(field, space.genericBaseToList(), space.getInterval()[0], space.getInterval()[1], false);
		this.degree = degree;
		if (ortho) {
			setBase(getOrthonormalBase(base));
			assignOrthonormalCoordinates(base, field);
		}
	}

	/**
	 * Constructor.
	 *
	 * @param field  the base field
	 * @param degree the sobolev degree.
	 */
	protected FiniteDimensionalSobolevSpace(final Field field, final int degree) {
		super(field);
		this.degree = degree;
	}

	/**
	 * Constructor.
	 *
	 * @param field       the base field
	 * @param genericBase the base
	 * @param left        the inf of the interval.
	 * @param right       the sup of the intervall.
	 * @param degree      the sobolev degree.
	 * @throws DevisionByZeroException if devision by zero occures
	 */
	public FiniteDimensionalSobolevSpace(final Field field, final List<Vector> genericBase, final double left,
			final double right, final int degree) throws DevisionByZeroException {
		super(field, genericBase, left, right, true);
		this.degree = degree;
		getDerivativeBuilder();
	}

	/**
	 * Constructor.
	 *
	 * @param field       the base field
	 * @param genericBase the base
	 * @param left        the inf of the interval.
	 * @param right       the sup of the intervall.
	 * @param degree      the sobolev degree.
	 * @param ortho       tells if base should be normalized.
	 * @throws DevisionByZeroException if devision by zero occures
	 */
	public FiniteDimensionalSobolevSpace(final Field field, final List<Vector> genericBase, final double left,
			final double right, final int degree, final boolean ortho) throws DevisionByZeroException {
		super(field, genericBase, left, right, ortho);
		this.degree = degree;
	}

	/**
	 * Getter for the sobolev degree.
	 *
	 * @return the sobolev degree
	 */
	public final Integer getDegree() {
		if (degree == null) {
			degree = base.size();
		}
		return degree;
	}

	public DerivativeOperator getDerivativeBuilder() {
		if (derivativeBuilder == null) {
			setDerivativeBuilder(new FiniteDimensionalDerivativeOperator(this, this));
		}
		return derivativeBuilder;
	}

	@Override
	public Scalar innerProduct(final Vector vec1, final Vector vec2) {
		if ((vec1 instanceof Function) && (vec2 instanceof Function)) {
			if ((((FiniteVectorMethods) vec1).getCoordinates() != null)
					&& (((FiniteVectorMethods) vec2).getCoordinates() != null)) {
				return super.innerProduct(vec1, vec2);
			} else {
				double product = 0;
				Vector tmp1 = vec1;
				Vector tmp2 = vec2;
				product += ((Real) super.innerProduct(tmp1, tmp2)).getDoubleValue();
				for (int i = 0; i < getDegree(); i++) {
					if ((((FiniteVectorMethods) tmp1).getCoordinates() == null)
							|| (((FiniteVectorMethods) tmp2).getCoordinates() == null) || (derivativeBuilder == null)) {
						tmp1 = ((Function) tmp1).getDerivative();
						tmp2 = ((Function) tmp2).getDerivative();
					} else {
						tmp1 = derivativeBuilder.get(this.get(((FiniteVectorMethods) tmp1).getCoordinates()));
						tmp2 = derivativeBuilder.get(this.get(((FiniteVectorMethods) tmp2).getCoordinates()));
					}
					product += ((Real) super.innerProduct(tmp1, tmp2)).getDoubleValue();
				}
				return getField().get(product);
			}
		}
		return super.innerProduct(vec1, vec2);
	}

	public void setDerivativeBuilder(final DerivativeOperator derivativeBuilder) {
		this.derivativeBuilder = derivativeBuilder;
	}

}
