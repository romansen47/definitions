package definitions.structures.abstr.vectorspaces.vectors;

import java.awt.Color;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.fields.scalars.impl.Real;
import definitions.structures.abstr.mappings.VectorField;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.vectors.impl.FunctionTuple;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import definitions.structures.euclidean.vectors.specialfunctions.Constant;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import solver.Plotable;
import solver.StdDraw;

/**
 * Function.
 * 
 * @author ro
 *
 */
public interface Function extends Vector, Plotable {

	/**
	 * Functions carry around a correctness parameter.
	 */
	double eps = 1.e-13;

	Function derivative = null;
	/**
	 * static constant 1-function.
	 */
	Function one = new Constant(RealLine.getInstance().getOne()) {
		private static final long serialVersionUID = 7890164185650800915L;
		final Field ownfield = field;

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
	Scalar value(Scalar input);

	/**
	 * Method to determine whether another function produces the same values.
	 * 
	 * @param other  the other function.
	 * @param source the source vector space.
	 * @return the equality.
	 */
	default boolean equals(final Function other, final EuclideanFunctionSpace source) {
		final int n = 100;
		final double a = source.getInterval()[0];
		final double b = source.getInterval()[1];
		double x;
		for (int i = 0; i < n; i++) {
			x = a + ((i * (b - a)) / 99.);
			if (Math.abs(value(new Real(x)).getValue() - other.value(new Real(x)).getValue()) > eps) {
				return false;
			}
		}
		return true;
	}

	default void preparePlot(final double left, final double right, StdDraw stddraw, int count, double delta) {
		double x = 0;
		double min = value(getField().get((right - left) / 2.)).getValue();
		double max = min;
		for (double i = 0; i < (count - 1); i += 1) {
			x = left + (delta * i);
			final double y = value(getField().get(x)).getValue();
			if (y > max) {
				max = y;
			}
			if (y < min) {
				min = y;
			}
		}
		final double d = max - min;
		if (d == 0) {
			min = min - 50;
			max = max + 50;
		} else {
			min = min - (0.2 * d);
			max = max + (0.5 * d);
		}
		stddraw.setCanvasSize(1000, 700);
		StdDraw.setXscale(left, right);
		StdDraw.setYscale(min, max);
	}

	@Override
	default void plot(final double left, final double right) {
		final StdDraw stddraw = new StdDraw();
		final int count = 1000;
		final double delta = (right - left) / count;
		preparePlot(left, right, stddraw, count, delta);
		double z = 0;
		StdDraw.setPenRadius(0.001);
		for (double i = 0; i < (count - 1); i += 1) {
			z = left + (delta * i);
			StdDraw.setPenColor(Color.blue);
			for (final Vector vec : getField().genericBaseToList()) {
				StdDraw.line(z, value(getField().get(z)).getCoordinates().get(vec).getValue(), z + delta,
						value(getField().get(z + delta)).getCoordinates().get(vec).getValue());
			}
		}
	}

	@Override
	default void plotCompare(final double left, final double right, final Function fun) {
		final StdDraw stddraw = new StdDraw();
		final int count = 1000;
		final double delta = (right - left) / count;
		preparePlot(left, right, stddraw, count, delta);
		Scalar tmp = getField().get(left);
		double alpha = value(tmp).getValue();
		double beta = fun.value(tmp).getValue();
		double z = 0;
		for (double i = 0; i < (count - 1); i += 1) {
			z = left + (delta * i);
			tmp = getField().get(z + delta);
			final double alphaNext = value(tmp).getValue();
			final double betaNext = fun.value(tmp).getValue();
			StdDraw.setPenRadius(0.0035);
			StdDraw.setPenColor(Color.blue);
			StdDraw.line(z, alpha, z + delta, alphaNext);
			StdDraw.setPenRadius(0.0025);
			StdDraw.setPenColor(Color.red);
			StdDraw.line(z, beta, z + delta, betaNext);
			alpha = alphaNext;
			beta = betaNext;
		}
		StdDraw.save("src/test/resources/" + Integer.toString(this.hashCode()) + ".png");
	}

	/**
	 * Method to compute the derivative of the function.
	 * 
	 * @return the derivative.
	 */
	default Function getDerivative() {
		final Field f = getField();
		if (derivative == null) {
			final Function fun = this;
			return new GenericFunction() {
				private static final long serialVersionUID = -1492641310343584079L;

				@Override
				public Scalar value(final Scalar input) {
					final double dy = fun.value(new Real(input.getValue() + eps)).getValue()
							- fun.value(input).getValue();
					final double dx = eps;
					return new Real(dy / dx);
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
	default Function getDerivative(EuclideanSpace space) {
		return getDerivative().getProjection(space);
	}

	/**
	 * Method to compute the n-th derivative.
	 * 
	 * @param n the derivative degree.
	 * @return the n-th derivative of the function.
	 */
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
	default Function getProjection(EuclideanSpace source) {
		if (this instanceof FunctionTuple) {// && source.contains(this)) {
			return this;
		}
		return new FunctionTuple(getCoordinates(source), source);
	}

	@Override
	void setCoordinates(Map<Vector, Scalar> coordinates);

	Map<EuclideanSpace, Map<Vector, Scalar>> getCoordinatesMap();

	default Field getField() {
		return RealLine.getInstance();
	}
}
