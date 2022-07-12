package definitions.structures.euclidean.functionspaces;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.FunctionSpace;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectors.impl.FunctionTuple;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 *
 * An euclidean function spaceis a finite dimensional function space.
 * 
 * @author RoManski
 *
 */
public interface EuclideanFunctionSpace extends EuclideanSpace, FunctionSpace {

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Function addition(final Vector vec1, final Vector vec2) {
		final Field f = this.getField();
		if (vec1.equals(nullVec())) {
			return (Function) vec2;
		}
		if (vec2.equals(nullVec())) {
			return (Function) vec1;
		}
		if ((vec1 instanceof Function) && (vec2 instanceof Function)) {
			if ((((FiniteVectorMethods) vec1).getCoordinates() == null)
					|| (((FiniteVectorMethods) vec2).getCoordinates() == null)) {
				return new GenericFunction() {
					@Override
					public Field getField() {
						return f;
					}

					@Override
					public Scalar value(final Scalar input) {
						return (Scalar) getField().addition(((Function) vec1).value(input),
								((Function) vec2).value(input));
					}
				};
			}
			final List<Vector> base = genericBaseToList();
			final Map<Vector, Scalar> coordinates = new ConcurrentHashMap<>();
			final Vector newVec1 = functionTuple(vec1);
			final Vector newVec2 = functionTuple(vec2);
			for (Vector vec : base) {
				coordinates.put(vec,
						f.get(((Real) ((FiniteVectorMethods) newVec1).getCoordinates().get(getBaseVec(vec)))
								.getDoubleValue()
								+ ((Real) ((FiniteVectorMethods) newVec2).getCoordinates().get(getBaseVec(vec)))
										.getDoubleValue()));
			}
			return new FunctionTuple(coordinates, this) {
				@Override
				public Field getField() {
					return f;
				}
			};
		}
		return null;
	}

	/**
	 * Convert generic function to function tuple
	 *
	 * @param vec the function
	 * @return the projection of vec
	 */
	default Function functionTuple(final Vector vec) {
		if (vec instanceof FunctionTuple) {
			return (Function) vec;
		}
		if ((((FiniteVectorMethods) vec).getCoordinates() == null)
				|| ((FiniteVectorMethods) vec).getCoordinates().isEmpty()) {
			return ((Function) vec).getProjection(this);
		}
		return (Function) this.get(((FiniteVectorMethods) vec).getCoordinates());
	}

	@Override
	default Vector get(final Map<Vector, Scalar> tmp) {
		return new FunctionTuple(tmp, this);
	}

	/**
	 * Method to get the zero function.
	 *
	 * @return a zero-function tuple. @
	 */
	default Function nullFunction() {
		final Map<Vector, Scalar> nul = new ConcurrentHashMap<>();
		genericBaseToList().stream().forEach(baseVec -> nul.put(baseVec, RealLine.getInstance().getZero()));
		return new FunctionTuple(nul, this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Function nullVec() {
		return nullFunction();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Function stretch(final Vector vec, final Scalar r) {
		if (vec.equals(nullVec()) || r.equals(this.getField().getZero())) {
			return nullVec();
		}
		if (r.equals(this.getField().getOne())) {
			return (Function) vec;
		}
		final Field f = this.getField();
		if (((FiniteVectorMethods) vec).getCoordinates() == null) {
			return new GenericFunction() {
				@Override
				public Field getField() {
					return f;
				}

				@Override
				public Scalar value(final Scalar input) {
					return (Scalar) getField().product(((Function) vec).value(input), r);
				}
			};
		} else {
			final Map<Vector, Scalar> coordinates = ((FiniteVectorMethods) vec).getCoordinates();
			final Map<Vector, Scalar> stretched = new ConcurrentHashMap<>();
			coordinates.keySet().stream()
					.forEach(vec1 -> stretched.put(vec1, (Scalar) this.getField().product(coordinates.get(vec1), r)));
			return new FunctionTuple(stretched, this);
		}
	}
}
