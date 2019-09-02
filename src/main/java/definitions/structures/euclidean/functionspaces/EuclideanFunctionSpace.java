package definitions.structures.euclidean.functionspaces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.FunctionSpace;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
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
	/**
	 * {@inheritDoc}
	 */
	@Override
	default Vector add(final Vector vec1, final Vector vec2) {
		final Field f = getField();
		if (vec1.equals(nullVec())) {
			return vec2;
		}
		if (vec2.equals(nullVec())) {
			return vec1;
		}
		if ((vec1 instanceof Function) && (vec2 instanceof Function)) {
			if ((((FiniteVectorMethods) vec1).getCoordinates() == null)
					|| (((FiniteVectorMethods) vec2).getCoordinates() == null)) {
				return new GenericFunction() {
					private static final long serialVersionUID = -2989863516320429371L;

					@Override
					public Scalar value(final Scalar input) {
						return (Scalar) getField().add(((Function) vec1).value(input), ((Function) vec2).value(input));
					}

					@Override
					public Field getField() {
						return f;
					}
				};
			}
			final List<Vector> base = genericBaseToList();
			final Map<Vector, Scalar> coordinates = new HashMap<>();
			final Vector newVec1 = functionTuple(vec1);
			final Vector newVec2 = functionTuple(vec2);
			for (final Vector vec : base) {
				coordinates.put(vec,
						getField().get(((FiniteVectorMethods) newVec1).getCoordinates().get(getBaseVec(vec)).getValue()
								+ ((FiniteVectorMethods) newVec2).getCoordinates().get(getBaseVec(vec)).getValue()));
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
		if (vec instanceof FunctionTuple) {
			return vec;
		}
		if (((FiniteVectorMethods) vec).getCoordinates() == null
				|| ((FiniteVectorMethods) vec).getCoordinates().isEmpty()) {
			return ((Function) vec).getProjection(this);
		}
		return this.get(((FiniteVectorMethods) vec).getCoordinates());
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Vector nullVec() {
		return nullFunction();
	}

	// @Override
	// default Vector get(final Map<Vector, Scalar> tmp) {
	// Function vec = nullFunction();
	// for (final Vector basevec : tmp.keySet()) {
	// vec = (Function) add(vec, stretch(basevec, tmp.get(basevec)));
	// }
	// return vec;
	// }

	// @Override
	// Vector getCoordinates(Vector vec);
	/**
	 * {@inheritDoc}
	 */
	@Override
	default Function stretch(final Vector vec, final Scalar r) {
		if (vec.equals(nullVec()) || r.equals(getField().getZero())) {
			return (Function) nullVec();
		}
		if (r.equals(getField().getOne())) {
			return (Function) vec;// ((Function) vec).getProjection(this);
		}
		final Field f = getField();
		if (((FiniteVectorMethods) vec).getCoordinates() == null) {
			return new GenericFunction() {
				private static final long serialVersionUID = -3311201318061885649L;

				@Override
				public Scalar value(final Scalar input) {
					return (Scalar) getField().product(((Function) vec).value(input), r);
				}

				@Override
				public Field getField() {
					return f;
				}
			};
		} else {
			final Map<Vector, Scalar> coordinates = ((FiniteVectorMethods) vec).getCoordinates();
			final Map<Vector, Scalar> stretched = new ConcurrentHashMap<>();
			for (final Vector vec1 : coordinates.keySet()) {
				stretched.put(vec1, (Scalar) getField().product(coordinates.get(vec1), r));
			}
			return new FunctionTuple(stretched, this);
		}
	}
}
