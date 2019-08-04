package definitions.structures.finitedimensional.real.functionspaces.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.field.impl.RealLine;
import definitions.structures.field.scalar.impl.Real;
import definitions.structures.finitedimensional.real.functionspaces.EuclideanFunctionSpace;
import definitions.structures.finitedimensional.real.vectors.Function;
import definitions.structures.finitedimensional.real.vectors.Plotable;
import definitions.structures.finitedimensional.real.vectors.impl.FunctionTuple;
import definitions.structures.finitedimensional.real.vectors.impl.GenericFunction;
import definitions.structures.finitedimensional.real.vectors.specialfunctions.Sine;
import definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;
import definitions.structures.finitedimensional.real.vectorspaces.impl.FiniteDimensionalVectorSpace;

/**
 * 
 * @author RoManski
 *
 *         Concrete implementation of a finite dimensional function space.
 */
public class FiniteDimensionalFunctionSpace extends FiniteDimensionalVectorSpace implements EuclideanFunctionSpace {

	/**
	 * the interval.
	 */
	protected double[] interval;

	/**
	 * The correctness parameter.
	 */
	protected final double eps = 1.e-5;

	/**
	 * Plain constructor. @
	 */
	protected FiniteDimensionalFunctionSpace() {
	}

	public FiniteDimensionalFunctionSpace(final List<Vector> genericBase, final double left, final double right,
			boolean orthonormalize) {
		super(genericBase);
		this.interval = new double[2];
		this.interval[0] = left;
		this.interval[1] = right;
		final List<Vector> newBase;
		if (orthonormalize) {
			newBase = this.getOrthonormalBase(genericBase);
		} else {
			newBase = genericBase;
		}
		for (final Vector vec : newBase) {
			final Map<Vector, Scalar> tmpCoord = new ConcurrentHashMap<>();
			for (final Vector otherVec : newBase) {
				if (vec == otherVec) {
					tmpCoord.put(otherVec, RealLine.getInstance().getOne());
				} else {
					tmpCoord.put(otherVec, RealLine.getInstance().getZero());
				}
			}
			vec.setCoordinates(tmpCoord);
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
	public List<Vector> getOrthonormalBase(final List<Vector> base) {
		final List<Vector> newBase = new ArrayList<>();
		for (final Vector vec : base) {
			Vector tmp = this.nullVec();
			for (final Vector vec2 : newBase) {
				tmp = this.add(tmp, this.projection(vec, vec2));
			}
			final Vector ans = this.normalize(this.add(vec, this.stretch(tmp, new Real(-1))));
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
			return new Real(this.integral((Function) vec1, (Function) vec2));
		}
	}

	@Override
	public Vector nullVec() {
		return new GenericFunction() {
			@Override
			public Scalar value(final Scalar input) {
				return new Real(0.);
			}
		};
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
		for (Vector vec:genericBaseToList()) {
			((Plotable)vec).plot(getLeft(), getRight());
		}
	}
	


	/**
	 * Method to fill a list with sine functions.
	 * 
	 * @param n       the highest degree of the trigonometric polynomials.
	 * @param d
	 * @param tmpBase the list.
	 */
	protected void getSineFunctions(final int n, double d, final List<Vector> tmpBase) {
		for (int i = 1; i < (n + 1); i++) {
			final Vector sin = new Sine(
					new Real(Math.sqrt(Math.abs(d) / Math.PI)),
					RealLine.getInstance().getZero(),
					new Real( d * i));
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
	protected void getCosineFunctions(final int n, double d, final List<Vector> tmpBase) {
		for (int i = 1; i < (n + 1); i++) {
			final Vector cos = new Sine(
					new Real(Math.sqrt(Math.abs(d) / Math.PI)),
					new Real(0.5 * Math.PI),
					new Real(d * i));
			tmpBase.add(cos);
		}
	}
}
