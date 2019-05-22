package definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.IFiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.IFiniteVector;

public interface IFiniteDimensionalFunctionSpace extends IFiniteDimensionalVectorSpace {

	double[] getIntervall();

	default double getIntegral(IFunction vec1, IFunction vec2) throws Throwable {
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
	default IFiniteVector getBaseVec(IFiniteVector baseVec) throws Throwable {
		for (IFiniteVector vec : getGenericBase()) {
			if (baseVec.equals(vec)) {
				return vec;
			}
		}
		return null;
	}

	@Override
	default Vector stretch(Vector vec, double r) throws Throwable {
		if (vec instanceof IFunction) {
			IFunction ans = stretch((IFunction) vec, r);
			return ans;
		}
		return null;
	}

	public IFunction stretch(IFunction vec, double r) throws Throwable;

	default double norm(IFunction vec) throws Throwable {
		return Math.sqrt(product(vec, vec));
	}

	default double getDistance(IFunction fun1, IFunction fun2) throws Throwable {
		IFunction diff = (IFunction) add(fun1, stretch(fun2, -1));
		return norm(diff);
	}

	@Override
	default Vector add(Vector vec1, Vector vec2) throws Throwable {
		if (vec1 instanceof IFunction && vec2 instanceof IFunction) {
			final List<IFiniteVector> base = genericBaseToList();
			final Map<IFiniteVector, Double> coordinates = new HashMap<>();
			for (final IFiniteVector vec : base) {
				coordinates.put(vec, ((IFiniteVector) (get((IFunction) vec1))).getCoordinates().get(getBaseVec(vec))
						+ ((IFiniteVector) (get((IFunction) vec2))).getCoordinates().get(getBaseVec(vec)));
			}
			return new Function(coordinates);
		}
		if (vec1 instanceof IFiniteVector && vec2 instanceof IFiniteVector) {
			return ((IFiniteDimensionalVectorSpace) this).add(vec1, vec2);
		}
		return ((VectorSpace) this).add(vec1, vec2);
	}

	@Override
	default Vector get(IFiniteVector vec) throws Throwable {
		Map<IFiniteVector, Double> map = new HashMap<>();
		int i = 0;
		for (IFiniteVector baseVec : getBase()) {
			map.put(baseVec, vec.getCoordinates().get(baseVec));
		}
		return new FiniteVector(map);
	}

	default IFunction nullFunction() throws Throwable {
		Map<IFiniteVector, Double> nul = new HashMap<>();
		for (IFiniteVector baseVec : getBase()) {
			nul.put(baseVec, 0.0);
		}
		return new Function(nul);
	}

	@Override
	default IFiniteVector nullVec() throws Throwable {
		return nullFunction();
	}

	@Override
	default Vector get(Map<IFiniteVector, Double> tmp) throws Throwable {
		IFunction vec = nullFunction();
		for (final IFiniteVector basevec : tmp.keySet()) {
			vec = (IFunction) add(vec, stretch(getBaseVec(basevec), tmp.get(basevec).doubleValue()));
		}
		return vec;
	}

	List<IFiniteVector> getBase();

}
