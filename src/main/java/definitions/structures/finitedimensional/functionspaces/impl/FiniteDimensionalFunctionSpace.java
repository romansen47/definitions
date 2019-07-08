package definitions.structures.finitedimensional.functionspaces.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.Vector;
import definitions.structures.finitedimensional.functionspaces.EuclideanFunctionSpace;
import definitions.structures.finitedimensional.vectors.Function;
import definitions.structures.finitedimensional.vectors.impl.FunctionTuple;
import definitions.structures.finitedimensional.vectors.impl.GenericFunction;
import definitions.structures.finitedimensional.vectorspaces.EuclideanSpace;
import definitions.structures.finitedimensional.vectorspaces.impl.FiniteDimensionalVectorSpace;

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
	protected final double eps = 1.e-4;

	/**
	 * Plain constructor. @
	 */
	protected FiniteDimensionalFunctionSpace() {
	}

	public FiniteDimensionalFunctionSpace(final List<Vector> genericBase, final double left, final double right) {
		super(genericBase);
		this.interval = new double[2];
		this.interval[0] = left;
		this.interval[1] = right;
		final List<Vector> newBase = this.getOrthonormalBase(genericBase);
		for (final Vector vec : newBase) {
			final Map<Vector, Double> tmpCoord = new ConcurrentHashMap<>();
			for (final Vector otherVec : newBase) {
				if (vec == otherVec) {
					tmpCoord.put(otherVec, 1.0);
				} else {
					tmpCoord.put(otherVec, 0.0);
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
	public Function stretch(final Vector vec, final double r) {
		if (vec instanceof GenericFunction) {
			return new GenericFunction() {
				@Override
				public double value(final double input) {
					return r * ((Function) vec).value(input);
				}
			};
		} else {
			final Map<Vector, Double> coordinates = vec.getCoordinates();
			final Map<Vector, Double> stretched = new ConcurrentHashMap<>();
			for (final Vector vec1 : coordinates.keySet()) {
				stretched.put(vec1, coordinates.get(vec1) * r);
			}
			return new FunctionTuple(stretched);
		}
	}

	@Override
	public List<Vector> getOrthonormalBase(final List<Vector> base) {
		final List<Vector> newBase = new ArrayList<>();
		for (final Vector vec : base) {
			Vector tmp = this.nullVec();
			for (final Vector vec2 : newBase) {
				tmp = this.add(tmp, this.projection(vec, vec2));
			}
			final Vector ans = this.normalize(this.add(vec, this.stretch(tmp, -1)));
			newBase.add(ans);
		}
		for (final Vector baseVec : newBase) {
			final Map<Vector, Double> coordinates = new ConcurrentHashMap<>();
			for (final Vector otherBaseVec : newBase) {
				if (baseVec.equals(otherBaseVec)) {
					coordinates.put(baseVec, 1.);
				} else {
					coordinates.put(otherBaseVec, 0.);
				}
			}
			baseVec.setCoordinates(coordinates);
		}
		return newBase;
	}

	@Override
	public Vector normalize(final Vector vec) {
		return this.stretch(vec, 1 / this.norm(vec));
	}

	@Override
	public double product(final Vector vec1, final Vector vec2) {
		if ((vec1 instanceof FunctionTuple) && (vec2 instanceof FunctionTuple)
				&& (((FunctionTuple) vec1).getGenericBase() == ((FunctionTuple) vec2).getGenericBase())) {
			return ((EuclideanSpace) this).product(vec1, vec2);
		} else {
			return this.integral((Function) vec1, (Function) vec2);
		}
	}

	@Override
	public Vector nullVec() {
		return new GenericFunction() {
			@Override
			public double value(final double input) {
				return 0.;
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

}
