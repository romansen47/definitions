package definitions.structures.euclidean.functionspaces.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.vectors.impl.FunctionTuple;
import definitions.structures.euclidean.vectors.specialfunctions.Sine;
import definitions.structures.euclidean.vectorspaces.impl.FiniteDimensionalVectorSpace;
import settings.GlobalSettings;

/**
 *
 * @author RoManski
 *
 *         Concrete implementation of a finite dimensional function space.
 */

public class FiniteDimensionalFunctionSpace extends FiniteDimensionalVectorSpace implements EuclideanFunctionSpace {

	private static final long serialVersionUID = -8669475459309858828L;

	/**
	 * the origin
	 */
	private Vector nullVec;

	/**
	 * the interval.
	 */
	protected double[] interval;

	/**
	 * The correctness parameter.
	 */
	protected final double eps = GlobalSettings.INTEGRAL_FEINHEIT;

	/**
	 * Plain constructor. @
	 */
	protected FiniteDimensionalFunctionSpace(final Field field) {
		super(field);
	}

	/**
	 *
	 * @param field          the field
	 * @param genericBase    the given base
	 * @param left           the left border
	 * @param right          the right border
	 * @param orthonormalize if true, also normalize the genericBase
	 */
	public FiniteDimensionalFunctionSpace(final Field field, final List<Vector> genericBase, final double left,
			final double right, final boolean orthonormalize) {
		super(field, genericBase);
		interval = new double[2];
		interval[0] = left;
		interval[1] = right;
		final List<Vector> newBase;
		if (orthonormalize) {
			newBase = getOrthonormalBase(genericBase);
		} else {
			newBase = genericBase;
			assignOrthonormalCoordinates(newBase, field);
		}
		setBase(newBase);
	}

	/**
	 * Method to fill a list with sine functions.
	 *
	 * @param n       the highest degree of the trigonometric polynomials.
	 * @param d       frequency
	 * @param tmpBase the list.
	 */
	protected void getCosineFunctions(final int n, final double d, final List<Vector> tmpBase) {
		for (int i = 1; i < (n + 1); i++) {
			final Vector cos = new Sine(RealLine.getInstance().get(Math.sqrt(Math.abs(d) / Math.PI)),
					RealLine.getInstance().get(0.5 * Math.PI), RealLine.getInstance().get(d * i)) {
				private static final long serialVersionUID = 7151322718389633337L;
			};
			tmpBase.add(cos);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getEpsilon() {
		return eps;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double[] getInterval() {
		return interval;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getLeft() {
		return getInterval()[0];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getRight() {
		return getInterval()[1];
	}

	/**
	 * Method to fill a list with sine functions.
	 *
	 * @param n       the highest degree of the trigonometric polynomials.
	 * @param d       frequency
	 * @param tmpBase the list.
	 */
	protected void getSineFunctions(final int n, final double d, final List<Vector> tmpBase) {
		for (int i = 1; i < (n + 1); i++) {
			final Vector sin = new Sine(RealLine.getInstance().get(Math.sqrt(Math.abs(d) / Math.PI)),
					RealLine.getInstance().getZero(), RealLine.getInstance().get(d * i)) {
				private static final long serialVersionUID = -6683701759680058862L;
			};
			tmpBase.add(sin);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Scalar innerProduct(final Vector vec1, final Vector vec2) {
		if ((((FiniteVectorMethods) vec1).getCoordinates() != null)
				&& (((FiniteVectorMethods) vec2).getCoordinates() != null)) {
			return super.innerProduct(vec1, vec2);
		} else {
			return integral((Function) vec1, (Function) vec2);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Function normalize(final Vector vec) {
		return stretch(vec,
				getField().getInverseElement(getField().get(((Real) norm(vec)).getDoubleValue())));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Function nullVec() {
		if (nullVec == null) {
			final Map<Vector, Scalar> nul = new HashMap<>();
			for (final Vector vec : genericBaseToList()) {
				nul.put(vec, (Scalar) getField().getZero());
			}
			nullVec = new FunctionTuple(nul, this);
		}
		return (FunctionTuple) nullVec;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Function stretch(final Vector vec, final Scalar r) {
		return EuclideanFunctionSpace.super.stretch(vec, r);
	}

}
