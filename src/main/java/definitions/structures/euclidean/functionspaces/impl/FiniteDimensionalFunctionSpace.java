package definitions.structures.euclidean.functionspaces.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.vectors.impl.FunctionTuple;
import definitions.structures.euclidean.vectors.specialfunctions.Sine;
import definitions.structures.euclidean.vectorspaces.impl.FiniteDimensionalVectorSpace;
import plotter.Plotable;
import settings.GlobalSettings;
import settings.Measurable;

/**
 * 
 * @author RoManski
 *
 *         Concrete implementation of a finite dimensional function space.
 */

public class FiniteDimensionalFunctionSpace extends FiniteDimensionalVectorSpace implements EuclideanFunctionSpace {

	private static final long serialVersionUID = -8669475459309858828L;

	private Vector nullVec = null;

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
	protected FiniteDimensionalFunctionSpace(Field field) {
		super(field);
	}

	public FiniteDimensionalFunctionSpace(Field field, final List<Vector> genericBase, final double left,
			final double right, boolean orthonormalize) {
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

	@Override
	public double[] getInterval() {
		return this.interval;
	}

	@Override
	public double getEpsilon() {
		return this.eps;
	}

	@Override
	
	public Vector normalize(final Vector vec) {
		return this.stretch(vec, this.getField().get(this.norm(vec).getValue()).getInverse());
	}

	@Override
	public Scalar innerProduct(final Vector vec1, final Vector vec2) {
		if ((((FiniteVectorMethods) vec1).getCoordinates() != null)
				&& (((FiniteVectorMethods) vec2).getCoordinates() != null)) {
			return super.innerProduct(vec1, vec2);
		} else {
			return this.integral((Function) vec1, (Function) vec2);
		}
	}

	@Override
	public Vector nullVec() {
		if (this.nullVec == null) {
			final Map<Vector, Scalar> nul = new HashMap<>();
			for (final Vector vec : this.genericBaseToList()) {
				nul.put(vec, (Scalar) this.getField().getZero());
			}
			this.nullVec = new FunctionTuple(nul, this);
		}
		return this.nullVec;
	}

	@Override
	@Measurable
	public double getLeft() {
		return this.getInterval()[0];
	}

	@Override
	@Measurable
	public double getRight() {
		return this.getInterval()[1];
	}

	@Measurable
	public void plotBase() {
		for (final Vector vec : this.genericBaseToList()) {
			((Plotable) vec).plot(this.getLeft(), this.getRight());
		}
	}

	/**
	 * Method to fill a list with sine functions.
	 * 
	 * @param n       the highest degree of the trigonometric polynomials.
	 * @param d
	 * @param tmpBase the list.
	 */
	@Measurable
	protected void getSineFunctions(final int n, double d, final List<Vector> tmpBase) {
		for (int i = 1; i < (n + 1); i++) {
			final Vector sin = new Sine(RealLine.getInstance().get(Math.sqrt(Math.abs(d) / Math.PI)), RealLine.getInstance().getZero(),
					RealLine.getInstance().get(d * i)) {
				private static final long serialVersionUID = -6683701759680058862L;
			};
			tmpBase.add(sin);
		}
	}

	/**
	 * Method to fill a list with sine functions.
	 * 
	 * @param n       the highest degree of the trigonometric polynomials.
	 * @param d
	 * @param tmpBase the list.
	 */
	@Measurable
	protected void getCosineFunctions(final int n, double d, final List<Vector> tmpBase) {
		for (int i = 1; i < (n + 1); i++) {
			final Vector cos = new Sine(RealLine.getInstance().get(Math.sqrt(Math.abs(d) / Math.PI)), RealLine.getInstance().get(0.5 * Math.PI),
					RealLine.getInstance().get(d * i)) {
				private static final long serialVersionUID = 7151322718389633337L;
			};
			tmpBase.add(cos);
		}
	}

	/*
	 * These overrides are for tracing purposes only
	 */

	@Override
	
	public Vector add(final Vector vec1, final Vector vec2) {
		return EuclideanFunctionSpace.super.add(vec1, vec2);
	}

	@Override
	
	public Vector stretch(final Vector vec, final Scalar r) {
		return EuclideanFunctionSpace.super.stretch(vec, r);
	}

	@Override
	
	public Vector projection(final Vector w, final Vector v) {
		return this.stretch(v, this.innerProduct(w, v));
	}

	@Override
	
	public void show() {
		super.show();
	}

}
