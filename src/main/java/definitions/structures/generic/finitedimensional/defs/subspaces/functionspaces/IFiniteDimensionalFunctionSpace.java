package definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.impl.FunctionSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.Function;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.FunctionTuple;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.GenericFunction;

/**
 * 
 * @author RoManski
 *
 * A finite dimensional function space is an euclidean function space. 
 */
public interface IFiniteDimensionalFunctionSpace extends EuclideanSpace,FunctionSpace {

	@Override
	default Vector add(final Vector vec1, final Vector vec2) throws Throwable {
		if ((vec1 instanceof Function) && (vec2 instanceof Function)) {
			if ((vec1 instanceof GenericFunction) || (vec2 instanceof GenericFunction)) {
				return new GenericFunction() {
					@Override
					public double value(final double input) throws Throwable {
						return ((Function) vec1).value(input) + ((Function) vec2).value(input);
					}
				};
			}
			final List<Vector> base = genericBaseToList();
			final Map<Vector, Double> coordinates = new HashMap<>();
			for (final Vector vec : base) {
				coordinates.put(vec, vec1.getCoordinates().get(getBaseVec(vec))
						+ vec2.getCoordinates().get(getBaseVec(vec)));
			}
			return new FunctionTuple(coordinates);
		}
		return null;
	}

	/**
	 * method for zero function.
	 * @return a zero-function tuple.
	 * @throws Throwable
	 */
	default Function nullFunction() throws Throwable {
		final Map<Vector, Double> nul = new HashMap<>();
		for (final Vector baseVec : genericBaseToList()) {
			nul.put(baseVec, 0.0);
		}
		return new FunctionTuple(nul);
	}

	@Override
	default Vector nullVec() throws Throwable {
		return nullFunction();
	}

	@Override
	default Vector get(final Map<Vector, Double> tmp) throws Throwable {
		Function vec = nullFunction();
		for (final Vector basevec : tmp.keySet()) {
			vec = (Function) add(vec, stretch(basevec, tmp.get(basevec).doubleValue()));
		}
		return vec;
	}

	@Override
	Vector getCoordinates(Vector vec) throws Throwable;
}
