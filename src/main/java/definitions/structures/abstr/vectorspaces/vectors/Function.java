package definitions.structures.abstr.vectorspaces.vectors;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.Proceed;
import definitions.Unweavable;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.vectors.impl.FunctionTuple;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import definitions.structures.euclidean.vectors.specialfunctions.Constant;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import plotter.Plotable;
import plotter.Plotter;
import settings.GlobalSettings;
import solver.StdDraw;

/**
 * Function.
 *
 * @author ro
 *
 */
public interface Function extends Vector, Plotable, FiniteVectorMethods, Unweavable {

	Function derivative = null;
	/**
	 * static constant 1-function.
	 */
	Function one = new Constant(RealLine.getInstance().getOne()) {

		Field ownfield = this.getField();

		@Override
		public Field getField() {
			return this.ownfield;
		}

	};

	/**
	 * Method to determine whether another function produces the same values.
	 *
	 * @param other  the other function.
	 * @param source the source vector space.
	 * @return the equality.
	 */
	default boolean equals(final Function other, final EuclideanFunctionSpace source) {
		final int n = GlobalSettings.FUNCTION_EQUALITY_FEINHEIT;
		final double a = source.getInterval()[0];
		final double b = source.getInterval()[1];
		double x;
		for (int i = 0; i < n; i++) {
			x = a + ((i * (b - a)) / 99.);
			if (Math.abs(((Real) this.value(this.getField().get(x))).getDoubleValue()
					- ((Real) other.value(RealLine.getInstance().get(x)))
							.getDoubleValue()) > GlobalSettings.DERIVATIVE_FEINHEIT) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Method to compute the coordinates of the projection onto a vector space.
	 *
	 * @param space the vector space.
	 * @return the coordinates of the projection.
	 */

	default Map<Vector, Scalar> getCoordinates(final EuclideanSpace space) {
		final Map<EuclideanSpace, Map<Vector, Scalar>> coordinatesMap = this.getCoordinatesMap();
		if (coordinatesMap != null) {
			if (coordinatesMap.get(space) != null) {
				return coordinatesMap.get(space);
			}
		}
		final Map<Vector, Scalar> newCoordinates = new ConcurrentHashMap<>();
		space.genericBaseToList().stream()
				.forEach(baseVec -> newCoordinates.put(baseVec, space.innerProduct(this, baseVec)));
		return newCoordinates;
	}

	Map<EuclideanSpace, Map<Vector, Scalar>> getCoordinatesMap();

	/**
	 * Method to compute the derivative of the function.
	 *
	 * @return the derivative.
	 */
	@Proceed
	default Function getDerivative() {
		final Field f = this.getField();
		if (Function.derivative == null) {
			final Function fun = this;
			return new GenericFunction() {

				@Override
				public Field getField() {
					return f;
				}

				@Override
				public Scalar value(final Scalar input) {
					final double dy = ((Real) fun
							.value(f.get(((Real) input).getDoubleValue() + GlobalSettings.DERIVATIVE_FEINHEIT)))
									.getDoubleValue()
							- ((Real) fun.value(input)).getDoubleValue();
					final double dx = GlobalSettings.DERIVATIVE_FEINHEIT;
					return f.get(dy / dx);
				}

			};
		} else {
			return Function.derivative;
		}
	}

	/**
	 * Method to compute the derivative of the function.
	 *
	 * @param space the given space
	 * @return the derivative.
	 */
	@Proceed
	default Function getDerivative(final EuclideanSpace space) {
		return this.getDerivative().getProjection(space);
	}

	/**
	 * Method to compute the n-th derivative.
	 *
	 * @param n the derivative degree.
	 * @return the n-th derivative of the function.
	 */

	@Proceed
	default Function getDerivative(final int n) {
		if (n == 0) {
			return this;
		} else {
			return this.getDerivative().getDerivative(n - 1);
		}
	}

	default Field getField() {
		return RealLine.getInstance();
	}

	/**
	 * Method to compute the projection onto another space.
	 *
	 * @param source the vector space.
	 * @return the projection.
	 */

	@Proceed
	default Function getProjection(final EuclideanSpace source) {
		if (this instanceof FunctionTuple) {
			return this;
		}
		final Map<Vector, Scalar> coord = this.getCoordinates(source);
		if (coord != null) {
			return (Function) source.get(coord);
		}
		return new FunctionTuple(coord, source);
	}

	/**
	 * Method to compute the projection of the derivative onto a vector space.
	 *
	 * @param space the vector space.
	 * @return the projection of the derivative.
	 */
	@Proceed
	default Function getProjectionOfDerivative(final EuclideanFunctionSpace space) {
		return this.getDerivative(space);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default void plot(final double left, final double right) {
		((Plotter) Generator.getInstance()).plot(this, left, right);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default void plotCompare(final double left, final double right, final Function fun) {
		((Plotter) Generator.getInstance()).plotCompare(this, fun, left, right);
	}

	default void preparePlot(final double left, final double right, final StdDraw stddraw, final int count,
			final double delta) {
		((Plotter) Generator.getInstance()).preparePlot(this, left, right, stddraw, count, delta);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	void setCoordinates(Map<Vector, Scalar> coordinates);

	/**
	 * Evaluation of the function.
	 *
	 * @param input the input parameter.
	 * @return the image of the input.
	 */
	@Proceed
	Vector value(Scalar input);

}
