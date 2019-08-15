package definitions.structures.euclidean.functionspaces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.FunctionSpace;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectors.impl.FunctionTuple;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

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
						return (Scalar) getField().add(((Function) vec1).value(input), ((Function) vec2).value(input));
					}
				};
			}
			final List<Vector> base = genericBaseToList();
			final Map<Vector, Scalar> coordinates = new HashMap<>();
			final Vector newVec1 = functionTuple(vec1);
			final Vector newVec2 = functionTuple(vec2);
			for (final Vector vec : base) {
				coordinates.put(vec, new Real(newVec1.getCoordinates().get(getBaseVec(vec)).getValue()
						+ newVec2.getCoordinates().get(getBaseVec(vec)).getValue()));
			}
			return new FunctionTuple(coordinates, this);
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
			nul.put(baseVec, RealLine.getInstance().getZero());
		}
		return new FunctionTuple(nul, this);
	}

	@Override
	default Vector nullVec() {
		return nullFunction();
	}

//	@Override
//	default Vector get(final Map<Vector, Scalar> tmp) {
//		Function vec = nullFunction();
//		for (final Vector basevec : tmp.keySet()) {
//			vec = (Function) add(vec, stretch(basevec, tmp.get(basevec)));
//		}
//		return vec;
//	}

//	@Override
//	Vector getCoordinates(Vector vec);

	@Override
	default Function stretch(final Vector vec, final Scalar r) {
		if (vec instanceof GenericFunction) {
			return new GenericFunction() {
				@Override
				public Scalar value(final Scalar input) {
					return (Scalar) getField().stretch(((Function) vec).value(input),r);
				}
			};
		} else {
			final Map<Vector, Scalar> coordinates = vec.getCoordinates();
			final Map<Vector, Scalar> stretched = new ConcurrentHashMap<>();
			for (final Vector vec1 : coordinates.keySet()) {
				stretched.put(vec1, new Real(coordinates.get(vec1).getValue() * r.getValue()));
			}
			return new FunctionTuple(stretched, this);
		}
	}
}
