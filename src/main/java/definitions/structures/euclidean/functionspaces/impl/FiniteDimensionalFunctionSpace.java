package definitions.structures.euclidean.functionspaces.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.vectors.impl.FunctionTuple;
import definitions.structures.euclidean.vectors.specialfunctions.Sine;
import definitions.structures.euclidean.vectorspaces.impl.FiniteDimensionalVectorSpace;
import exceptions.DevisionByZeroException;
import settings.GlobalSettings;

/**
 *
 * @author RoManski
 *
 *         Concrete implementation of a finite dimensional function space.
 */

public class FiniteDimensionalFunctionSpace extends FiniteDimensionalVectorSpace implements EuclideanFunctionSpace {

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
	protected static final double eps = GlobalSettings.INTEGRAL_FEINHEIT;

	/**
	 * Plain constructor.
	 *
	 * @param field the base field
	 *
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
	 * @throws DevisionByZeroException if devision by zero occures
	 */
	public FiniteDimensionalFunctionSpace(final Field field, final List<Vector> genericBase, final double left,
			final double right, final boolean orthonormalize) throws DevisionByZeroException {
		super(field, genericBase);
		this.interval = new double[2];
		this.interval[0] = left;
		this.interval[1] = right;
		final List<Vector> newBase;
		if (orthonormalize) {
			newBase = this.getOrthonormalBase(genericBase);
		} else {
			newBase = genericBase;
			this.assignOrthonormalCoordinates(newBase, field);
		}
		this.setBase(newBase);
	}

	/**
	 * Method to fill a list with sine functions.
	 *
	 * @param n       the highest degree of the trigonometric polynomials.
	 * @param d       frequency
	 * @param tmpBase the list.
	 */
	protected void getCosineFunctions(final int n, final double d, final List<Vector> tmpBase) {
		/*
		 * be careful, no parallel streams allowed here!
		 */
		IntStream.range(1, n + 1).parallel()
				.forEach(i -> tmpBase.add(new Sine(RealLine.getInstance().get(Math.sqrt(Math.abs(d) / Math.PI)),
						RealLine.getInstance().get(0.5 * Math.PI), RealLine.getInstance().get(d * i)) {
				}));
	}

	/**
	 * Method to fill a list with sine functions.
	 *
	 * @param n       the highest degree of the trigonometric polynomials.
	 * @param d       frequency
	 * @param tmpBase the list.
	 */
	protected void getSineFunctions(final int n, final double d, final List<Vector> tmpBase) {
		/*
		 * be careful, no parallel streams allowed here!
		 */
		IntStream.range(1, n + 1).parallel()
				.forEach(i -> tmpBase.add(new Sine(RealLine.getInstance().get(Math.sqrt(Math.abs(d) / Math.PI)),
						RealLine.getInstance().getZero(), RealLine.getInstance().get(d * i)) {
				}));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getEpsilon() {
		return FiniteDimensionalFunctionSpace.eps;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double[] getInterval() {
		return this.interval;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getLeft() {
		return this.getInterval()[0];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getRight() {
		return this.getInterval()[1];
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
			return this.integral((Function) vec1, (Function) vec2);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Function normalize(final Vector vec) {
		Vector norm = this.norm(vec);
		if (norm.equals(getField().nullVec())) {
			LogManager.getLogger(FiniteDimensionalFunctionSpace.class).error("Devision by 0!");
		}
		return this.stretch(vec, (Scalar) this.getField().getMuliplicativeMonoid().getInverseElement(norm));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Function nullVec() {
		if (this.nullVec == null) {
			final Map<Vector, Scalar> nul = new ConcurrentHashMap<>();
			final Scalar zero = this.getField().getZero();
			this.genericBaseToList().parallelStream().forEach(vec -> nul.put(vec, zero));
			this.nullVec = new FunctionTuple(nul, this);
		}
		return (FunctionTuple) this.nullVec;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Function stretch(final Vector vec, final Scalar r) {
		return EuclideanFunctionSpace.super.stretch(vec, r);
	}

}
