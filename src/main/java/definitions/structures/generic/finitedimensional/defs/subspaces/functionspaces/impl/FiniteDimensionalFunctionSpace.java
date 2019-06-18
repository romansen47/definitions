package definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.FiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.IFiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.Function;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.FunctionTuple;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.GenericFunction;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.Tuple;

public class FiniteDimensionalFunctionSpace extends FiniteDimensionalVectorSpace
		implements IFiniteDimensionalFunctionSpace {

	protected double[] intervall;
	protected final double eps = 1.e-4;

	protected FiniteDimensionalFunctionSpace() throws Throwable {
	}

	@Override
	public double product(final Vector vec1, final Vector vec2) throws Throwable {
		if ((vec1 instanceof FunctionTuple) && (vec2 instanceof FunctionTuple)
				&& (((FunctionTuple) vec1).getGenericBase() == ((FunctionTuple) vec2).getGenericBase())) {
			return super.product(vec1, vec2);
		} else {
			return this.getIntegral((Function) vec1, (Function) vec2);
		}
	}

	public FiniteDimensionalFunctionSpace(final List<Vector> genericBase, final double left, final double right)
			throws Throwable {
		super(genericBase);
		this.intervall = new double[2];
		this.intervall[0] = left;
		this.intervall[1] = right;
		List<Vector> newBase=getOrthonormalBase(genericBase);
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
		setBase(newBase);
	}

	@Override
	public double[] getIntervall() {
		return this.intervall;
	}

	@Override
	public double getEpsilon() {
		return this.eps;
	}

	@Override
	public Function stretch(final Function vec, final double r) throws Throwable {
		if (vec instanceof GenericFunction) {
			return new GenericFunction() {
				@Override
				public double value(final double input) throws Throwable {
					return r * vec.value(input);
				}
			};
		} else {
			final Map<Vector, Double> coordinates = vec.getCoordinates();
			final Map<Vector, Double> stretched = new ConcurrentHashMap<>();
			for (final Vector vec1 : this.getBase()) {
				stretched.put(vec1, coordinates.get(this.getBaseVec(vec1)) * r);
			}
			return new FunctionTuple(stretched);
		}
	}

	@Override
	public List<Vector> getOrthonormalBase(final List<Vector> base) throws Throwable {
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

	public Vector normalize(final Vector vec) throws Throwable {
		return stretch((Function)vec, 1 / norm((Function)vec));
	}
	
	@Override
	public Vector nullVec() throws Throwable {
		return new GenericFunction() {
			@Override
			public double value(final double input) {
				return 0.;
			}
		};
//		return nullFunction();
	}

//	@Override
//	public Vector getCoordinates(Vector vec) throws Throwable {
//		Map<Vector, Double> coordinates = new HashMap<>();
//		for (Vector baseVec : genericBaseToList()) {
//			coordinates.put(baseVec, product(vec, baseVec));
//		}
//		return get(coordinates);
//	}

}
