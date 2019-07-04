package definitions.structures.finitedimensional.functionspaces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import definitions.structures.abstr.FunctionSpace;
import definitions.structures.abstr.Vector;
import definitions.structures.finitedimensional.vectors.Function;
import definitions.structures.finitedimensional.vectors.impl.FunctionTuple;
import definitions.structures.finitedimensional.vectors.impl.GenericFunction;
import definitions.structures.finitedimensional.vectorspaces.EuclideanSpace;

/**
 * 
 * @author RoManski
 *
 *         A finite dimensional function space is an euclidean function space.
 */
public interface EuclideanFunctionSpace extends EuclideanSpace, FunctionSpace {

	@Override
	default Vector add(final Vector vec1, final Vector vec2) {
		if ((vec1 instanceof Function) && (vec2 instanceof Function)) {
			if ((vec1 instanceof GenericFunction) || (vec2 instanceof GenericFunction)) {
				return new GenericFunction() {
					@Override
					public double value(final double input) {
						return ((Function) vec1).value(input) + ((Function) vec2).value(input);
					}
				};
			}
			final List<Vector> base = genericBaseToList();
			final Map<Vector, Double> coordinates = new HashMap<>();
			for (final Vector vec : base) {
				coordinates.put(vec,
						vec1.getCoordinates().get(getBaseVec(vec)) + vec2.getCoordinates().get(getBaseVec(vec)));
			}
			return new FunctionTuple(coordinates);
		}
		return null;
	}

	/**
	 * Method to get the zero function.
	 * 
	 * @return a zero-function tuple. @
	 */
	default Function nullFunction() {
		final Map<Vector, Double> nul = new HashMap<>();
		for (final Vector baseVec : genericBaseToList()) {
			nul.put(baseVec, 0.0);
		}
		return new FunctionTuple(nul);
	}

	@Override
	default Vector nullVec() {
		return nullFunction();
	}

	@Override
	default Vector get(final Map<Vector, Double> tmp) {
		Function vec = nullFunction();
		for (final Vector basevec : tmp.keySet()) {
			vec = (Function) add(vec, stretch(basevec, tmp.get(basevec).doubleValue()));
		}
		return vec;
	}

	@Override
	Vector getCoordinates(Vector vec);
}
