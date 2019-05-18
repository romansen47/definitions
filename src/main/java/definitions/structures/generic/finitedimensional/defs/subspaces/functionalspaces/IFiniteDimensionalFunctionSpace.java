package definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces;

import definitions.structures.abstr.IVector;
import definitions.structures.generic.finitedimensional.defs.spaces.IFiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces.functions.IFunction;
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
		for (IFiniteVector vec : genericBaseToList().get(0).getGenericBase()) {
			if (baseVec.equals(vec)) {
				return vec;
			}
		}
		return null;
	}

	@Override
	default IVector stretch(IFiniteVector vec, double r) throws Throwable {
		if (vec instanceof IFunction && vec.getDim() == dim()) {
			return stretch(vec, r);
		}
		throw new Throwable();
	}

}
