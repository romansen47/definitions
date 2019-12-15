package definitions.structures.abstr.vectorspaces.vectors;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.IGenerator;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.vectors.impl.FunctionTuple;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import definitions.structures.euclidean.vectors.specialfunctions.Constant;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import plotter.Plotable;
import plotter.Plotter;
import settings.GlobalSettings;
import settings.annotations.Proceed;
import solver.StdDraw;

/**
 * Function.
 * 
 * @author ro
 *
 */
public interface Function extends Vector, Plotable, FiniteVectorMethods {

	final static IGenerator gen = Generator.getInstance();
	/**
	 * Functions carry around a correctness parameter.
	 */
	double eps = GlobalSettings.DERIVATIVE_FEINHEIT;

	Function derivative = null;
	/**
	 * static constant 1-function.
	 */
	Function one = new Constant(RealLine.getInstance().getOne()) {
		private static final long serialVersionUID = 7890164185650800915L;
		final Field ownfield = getField();

		@Override
		public Field getField() {
			return ownfield;
		}

	};

	/**
	 * Evaluation of the function.
	 * 
	 * @param input the input parameter.
	 * @return the image of the input.
	 */
	@Proceed
	Scalar value(Scalar input);

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
			if (Math.abs(value(getField().get(x)).getValue()
					- other.value(RealLine.getInstance().get(x)).getValue()) > eps) {
				return false;
			}
		}
		return true;
	}

	default void preparePlot(final double left, final double right, StdDraw stddraw, int count, double delta) {
		((Plotter) gen).preparePlot(this, left, right, stddraw, count, delta);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default void plot(final double left, final double right) {
		((Plotter) gen).plot(this, left, right);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default void plotCompare(final double left, final double right, final Function fun) {
		((Plotter) gen).plotCompare(this, fun, left, right);
	}

	/**
	 * Method to compute the derivative of the function.
	 * 
	 * @return the derivative.
	 */
	@Proceed
	default Function getDerivative() {
		final Field f = getField();
		if (derivative == null) {
			final Function fun = this;
			return new GenericFunction() {
				private static final long serialVersionUID = -1492641310343584079L;

				@Override
				public Scalar value(final Scalar input) {
					final double dy = fun.value(f.get(input.getValue() + eps)).getValue() - fun.value(input).getValue();
					final double dx = eps;
					return f.get(dy / dx);
				}

				@Override
				public Field getField() {
					return f;
				}

			};
		} else {
			return derivative;
		}
	}

	/**
	 * Method to compute the derivative of the function.
	 * 
	 * @return the derivative.
	 */

	@Proceed
	default Function getDerivative(EuclideanSpace space) {
		return getDerivative().getProjection(space);
	}

	/**
	 * Method to compute the n-th derivative.
	 * 
	 * @param n the derivative degree.
	 * @return the n-th derivative of the function.
	 */

	@Proceed
	default Function getDerivative(int n) {
		// if (n < 0) {
		// return getPrimitiveIntegral(-n);
		// }
		if (n == 0) {
			return this;
		} else {
			return getDerivative().getDerivative(n - 1);
		}
	}

	/**
	 * Method to compute the primitive integral function.
	 * 
	 * @return the integral function.
	 */
	// default Function getPrimitiveIntegral() {
	// final EuclideanSpace space = (EuclideanSpace)
	// Generator.getGenerator().getTrigonometricSpace(20);
	// final Function projection = getProjection(space);
	// return new FunctionTuple(new GenericFunction() {
	// @Override
	// public Scalar value(Scalar input) {
	// return new Real(FunctionSpace.getIntegral(projection, one, ((FunctionSpace)
	// space).getLeft(),
	// input.getValue(), eps));
	// }
	//
	// }.getCoordinates(space));
	// }

	/**
	 * Method to get an n-th primitive integral.
	 * 
	 * @param n the degree.
	 * @return an n-th integral.
	 */
	// default Function getPrimitiveIntegral(int n) {
	// if (n < 0) {
	// return getDerivative(-n);
	// }
	// if (n == 0) {
	// return this;
	// } else {
	// return getPrimitiveIntegral().getPrimitiveIntegral(n - 1);
	// }
	// }

	/**
	 * Method to compute the projection of the derivative onto a vector space.
	 * 
	 * @param space the vector space.
	 * @return the projection of the derivative.
	 */

	@Proceed
	default Function getProjectionOfDerivative(EuclideanFunctionSpace space) {
		return this.getDerivative(space);
	}

	/**
	 * Method to compute the coordinates of the projection onto a vector space.
	 * 
	 * @param space the vector space.
	 * @return the coordinates of the projection.
	 */

	default Map<Vector, Scalar> getCoordinates(final EuclideanSpace space) {
		final Map<EuclideanSpace, Map<Vector, Scalar>> coordinatesMap = getCoordinatesMap();
		if (coordinatesMap != null) {
			if (coordinatesMap.get(space) != null) {
				return coordinatesMap.get(space);
			}
		}
		final Map<Vector, Scalar> newCoordinates = new ConcurrentHashMap<>();
		for (final Vector baseVec : space.genericBaseToList()) {
			newCoordinates.put(baseVec, space.innerProduct(this, baseVec));
		}
		return newCoordinates;
	}

	/**
	 * Method to compute the projection onto another space.
	 * 
	 * @param source the vector space.
	 * @return the projection.
	 */

	@Proceed
	default Function getProjection(EuclideanSpace source) {
		if (this instanceof FunctionTuple) {
			return this;
		}
		Map<Vector, Scalar> coord = getCoordinates(source);
		if (coord != null) {
//			return (Function) source.get(getCoordinates());
			return (Function) source.get(coord);
		}
		return new FunctionTuple(coord, source);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	void setCoordinates(Map<Vector, Scalar> coordinates);

	Map<EuclideanSpace, Map<Vector, Scalar>> getCoordinatesMap();

	default Field getField() {
		return RealLine.getInstance();
	}

}
