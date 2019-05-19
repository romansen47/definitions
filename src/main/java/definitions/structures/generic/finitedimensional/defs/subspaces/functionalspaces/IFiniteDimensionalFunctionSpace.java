package definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import definitions.structures.abstr.IVector;
import definitions.structures.generic.finitedimensional.defs.spaces.IFiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.IFiniteVector;

public interface IFiniteDimensionalFunctionSpace extends IFiniteDimensionalVectorSpace {

	double[] getIntervall();

	@Override
	default double product(IVector vec1, IVector vec2) throws Throwable {
		if (vec1 instanceof IFunction && vec2 instanceof IFunction) {
			return product((IFunction) vec1, (IFunction) vec2);
		}
		throw new Throwable();
	}

	default double product(IFunction fun1, IFunction fun2) throws Throwable {
		return getIntegral(fun1, fun2);
	}

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

	default IVector stretch(IFiniteVector vec, double r) throws Throwable {
		IFunction ans = stretch((IFunction) vec, r);
		return ans;
	}

	public IFunction stretch(IFunction vec, double r) throws Throwable;

	default double norm(IFunction vec) throws Throwable {
		return Math.sqrt(product(vec, vec));
	}

	default double getDistance(IFunction fun1, IFunction fun2) throws Throwable {
		IFunction diff = (IFunction) add(fun1, stretch(fun2, -1));
		return norm(diff);
	}

	default IFunction add(IFiniteVector vec1, IFiniteVector vec2) throws Throwable {
		if (vec1.getDim() == vec2.getDim() && vec1.getDim() == dim()) {
			final List<IFiniteVector> base = genericBaseToList();
			final Map<IFiniteVector, Double> coordinates = new HashMap<>();
			for (final IFiniteVector vec : base) {
				coordinates.put(getBaseVec(vec),
						vec1.getCoordinates().get(getBaseVec(vec)) + vec2.getCoordinates().get(getBaseVec(vec)));
			}
			return new Function(coordinates);
		} else {
			throw new Exception();
		}
	}

}
