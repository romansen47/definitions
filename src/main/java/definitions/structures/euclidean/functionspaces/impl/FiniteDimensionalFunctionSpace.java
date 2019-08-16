package definitions.structures.euclidean.functionspaces.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.solver.Plotable;
import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.vectors.impl.FunctionTuple;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import definitions.structures.euclidean.vectors.specialfunctions.Sine;
import definitions.structures.euclidean.vectorspaces.impl.FiniteDimensionalVectorSpace;

/**
 * 
 * @author RoManski
 *
 *         Concrete implementation of a finite dimensional function space.
 */
public class FiniteDimensionalFunctionSpace extends FiniteDimensionalVectorSpace implements EuclideanFunctionSpace {

	private Vector nullVec;

	/**
	 * the interval.
	 */
	protected double[] interval;

	/**
	 * The correctness parameter.
	 */
	protected final double eps = 1.e-4;

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
		setBase(genericBase);
		if (orthonormalize) {
			newBase = this.getOrthonormalBase(genericBase);
		} else {
			newBase = genericBase;
		}
		assignOrthonormalCoordinates(newBase,field);
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
	public List<Vector> getOrthonormalBase(final List<Vector> base) {
		final List<Vector> newBase = new ArrayList<>();
		for (final Vector vec : base) {
			Vector tmp = this.nullVec();
			for (final Vector vec2 : newBase) {
				tmp = this.add(tmp, this.projection(vec, vec2));
			}
			final Vector ans = this.normalize(this.add(vec, this.stretch(tmp, getField().get(-1))));
			newBase.add(ans);
		}
		for (final Vector baseVec : newBase) {
			final Map<Vector, Scalar> coordinates = new ConcurrentHashMap<>();
			for (final Vector otherBaseVec : newBase) {
				if (baseVec.equals(otherBaseVec)) {
					coordinates.put(baseVec, new Real(1.));
				} else {
					coordinates.put(otherBaseVec, new Real(0.));
				}
			}
			baseVec.setCoordinates(coordinates);
		}
		return newBase;
	}

	@Override
	public Vector normalize(final Vector vec) {
		return this.stretch(vec, this.norm(vec).getInverse());
	}

	@Override
	public Scalar innerProduct(final Vector vec1, final Vector vec2) {
		if ((vec1 instanceof FunctionTuple) && (vec2 instanceof FunctionTuple)
				&& (((FunctionTuple) vec1).getGenericBase() == ((FunctionTuple) vec2).getGenericBase())) {
			return super.innerProduct(vec1, vec2);
		} else {
			return this.integral((Function) vec1, (Function) vec2);
		}
	}

	@Override
	public Vector nullVec() {
		if (nullVec == null) {
			Map<Vector, Scalar> nul = new HashMap<>();
			for (Vector vec : genericBaseToList()) {
				nul.put(vec, (Scalar) getField().getZero());
			}
			nullVec = new FunctionTuple(nul, this);
		}
		return nullVec;
	}

	@Override
	public double getLeft() {
		return this.getInterval()[0];
	}

	@Override
	public double getRight() {
		return this.getInterval()[1];
	}

	public void plotBase() {
		for (final Vector vec : this.genericBaseToList()) {
			((Plotable) vec).plot(this.getLeft(), this.getRight());
		}
	}

	/**
	 * Method to fill a list with sine functions.
	 * 
	 * @param n
	 *            the highest degree of the trigonometric polynomials.
	 * @param d
	 * @param tmpBase
	 *            the list.
	 */
	protected void getSineFunctions(final int n, double d, final List<Vector> tmpBase) {
		for (int i = 1; i < (n + 1); i++) {
			final Vector sin = new Sine(new Real(Math.sqrt(Math.abs(d) / Math.PI)), RealLine.getInstance().getZero(),
					new Real(d * i)) {
						@Override
						public Field getField() {
							return field;
						}
			};
			tmpBase.add(sin);
		}
	}

	/**
	 * Method to fill a list with sine functions.
	 * 
	 * @param n
	 *            the highest degree of the trigonometric polynomials.
	 * @param d
	 * @param tmpBase
	 *            the list.
	 */
	protected void getCosineFunctions(final int n, double d, final List<Vector> tmpBase) {
		for (int i = 1; i < (n + 1); i++) {
			final Vector cos = new Sine(new Real(Math.sqrt(Math.abs(d) / Math.PI)), new Real(0.5 * Math.PI),
					new Real(d * i)) {
						@Override
						public Field getField() {
							return field;
						}};
			tmpBase.add(cos);
		}
	}
}
