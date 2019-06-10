package definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.Function;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.FunctionTuple;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.Tuple;

public interface IFiniteDimensionalFunctionSpace extends EuclideanSpace {

	double[] getIntervall();

	default double getIntegral(Function vec1, Function vec2) throws Throwable {
		double left = getIntervall()[0];
		double right = getIntervall()[1];
		final double eps = (right - left) * getEpsilon();
		double ans = 0;
		double x = left;
		while (x < right) {
			ans += vec1.value(x) * vec2.value(x);
			x += eps;
		}
		return ans * eps;
	}

	double getEpsilon();

	@Override
	default Vector getBaseVec(Vector baseVec) throws Throwable {
		for (Vector vec : genericBaseToList()) {
			if (baseVec.equals(vec)) {
				return vec;
			}
		}
		return null;
	}

	@Override
	default Vector stretch(Vector vec, double r) throws Throwable {
		if (vec instanceof Function) {
			return stretch((Function) vec, r);
		}
		return stretch(vec, r);
	}

	public Function stretch(Function vec, double r) throws Throwable;

	default double norm(Function vec) throws Throwable {
		return Math.sqrt(product(vec, vec));
	}

	default double getDistance(Function fun1, Function fun2) throws Throwable {
		Function diff = new FunctionTuple() {
			@Override
			public double value(double input) throws Throwable {
				return fun1.value(input) - fun2.value(input);
			}
		};
		return norm(diff);
	}

	@Override
	default Vector add(Vector vec1, Vector vec2) throws Throwable {
		if (vec1 instanceof Function && vec2 instanceof Function) {
			final List<Vector> base = genericBaseToList();
			final Map<Vector, Double> coordinates = new HashMap<>();
			for (final Vector vec : base) {
				coordinates.put(vec, get(vec1).getCoordinates().get(getBaseVec(vec))
						+ get(vec2).getCoordinates().get(getBaseVec(vec)));
			}
			return new FunctionTuple(coordinates);
		}
		if (vec1 instanceof FiniteVector && vec2 instanceof FiniteVector) {
			return this.add(vec1, vec2);
		}
		return this.add(vec1, vec2);
	}

	@Override
	default Vector get(Vector vec) throws Throwable {
		Map<Vector, Double> map = new HashMap<>();
		for (Vector baseVec : getBase()) {
			map.put(baseVec, vec.getCoordinates().get(baseVec));
		}
		return new Tuple(map);
	}

	default Function nullFunction() throws Throwable {
		Map<Vector, Double> nul = new HashMap<>();
		for (Vector baseVec : getBase()) {
			nul.put(baseVec, 0.0);
		}
		return new FunctionTuple(nul);
	}

	@Override
	default Vector nullVec() throws Throwable {
		return nullFunction();
	}

	@Override
	default Vector get(Map<Vector, Double> tmp) throws Throwable {
		Function vec = nullFunction();
		for (final Vector basevec : tmp.keySet()) {
			vec = (Function) add(vec, stretch(basevec, tmp.get(basevec).doubleValue()));
		}
		return vec;
	}

	List<Vector> getBase();

	@Override
	Vector getCoordinates(Vector vec) throws Throwable;
}
