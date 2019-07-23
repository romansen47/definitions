package definitions.structures.finitedimensional.real.functionspaces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import definitions.structures.abstr.FunctionSpace;
import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.finitedimensional.real.vectors.Function;
import definitions.structures.finitedimensional.real.vectors.Real;
import definitions.structures.finitedimensional.real.vectors.impl.FunctionTuple;
import definitions.structures.finitedimensional.real.vectors.impl.GenericFunction;
import definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;

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
					public Scalar value(final Scalar input) {
						return new Real(((Function) vec1).value(input).getValue() + ((Function) vec2).value(input).getValue());
					}
				};
			}
			final List<Vector> base = genericBaseToList();
			final Map<Vector, Scalar> coordinates = new HashMap<>();
			final Vector newVec1 = functionTuple(vec1);
			final Vector newVec2 = functionTuple(vec2);
			for (final Vector vec : base) {
				coordinates.put(vec,
						new Real(newVec1.getCoordinates().get(getBaseVec(vec)).getValue() +
								newVec2.getCoordinates().get(getBaseVec(vec)).getValue()));
			}
			return new FunctionTuple(coordinates);
		}
		return null;
	}

	/**
	 * Convert generic function to function tuple
	 * 
	 * @param vec the function
	 * @return the projection of vec
	 */
	default Vector functionTuple(Vector vec) {
		if (vec.getCoordinates() == null) {
			return ((Function) vec).getProjection(this);
		}
		return vec;
	}

	/**
	 * Method to get the zero function.
	 * 
	 * @return a zero-function tuple. @
	 */
	default Function nullFunction() {
		final Map<Vector, Scalar> nul = new HashMap<>();
		for (final Vector baseVec : genericBaseToList()) {
			nul.put(baseVec, new Real(0.0));
		}
		return new FunctionTuple(nul);
	}

	@Override
	default Vector nullVec() {
		return nullFunction();
	}

	@Override
	default Vector get(final Map<Vector, Scalar> tmp) {
		Function vec = nullFunction();
		for (final Vector basevec : tmp.keySet()) {
			vec = (Function) add(vec, stretch(basevec,tmp.get(basevec)));
		}
		return vec;
	}

	@Override
	Vector getCoordinates(Vector vec);

}
