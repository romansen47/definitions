package definitions.structures.finitedimensional.real.vectors;

import java.awt.Color;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.solver.StdDraw;
import definitions.structures.abstr.FunctionSpace;
import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.field.impl.RealLine;
import definitions.structures.field.scalar.Real;
import definitions.structures.finitedimensional.real.Generator;
import definitions.structures.finitedimensional.real.functionspaces.EuclideanFunctionSpace;
import definitions.structures.finitedimensional.real.vectors.impl.FunctionTuple;
import definitions.structures.finitedimensional.real.vectors.impl.GenericFunction;
import definitions.structures.finitedimensional.real.vectors.specialfunctions.Constant;
import definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;

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
	double eps = 1.e-3;

	/**
	 * static constant 1-function.
	 */
	Function one = new Constant(RealLine.getInstance().getOne());

	Function derivative = null;

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

	@Override
	default void plot(final double left, final double right) {

		final int count = 1000;

		final double delta = (right - left) / count;
		double x = 0;
		double min = value(new Real((right - left) / 2.)).getValue();
		double max = min;
		for (double i = 0; i < (count - 1); i += 1) {
			x = left + (delta * i);
			final double y = value(new Real(x)).getValue();
			if (y > max) {
				max = y;
			}
			if (y < min) {
				min = y;
			}
		}
		if (delta == 0) {
			min = min - 100;
			max = max + 100;
		}
		final StdDraw stddraw = new StdDraw();
		stddraw.setCanvasSize(1000, 700);
		StdDraw.setXscale(left, right);
		StdDraw.setYscale(1.5 * min, 1.5 * max);

		double z = 0;
		StdDraw.setPenRadius(0.001);
		for (double i = 0; i < (count - 1); i += 1) {
			z = left + (delta * i);
			StdDraw.setPenColor(Color.blue);
			StdDraw.line(z, value(new Real(z)).getValue(), z + delta, value(new Real(z + delta)).getValue());
		}
	}

	@Override
	default void plotCompare(final double left, final double right, final Function fun) {
		final int count = 1000;
		final double delta = (right - left) / count;
		double x = 0;
		double min = value(new Real((right - left) / 2.)).getValue();
		double max = min;
		for (double i = 0; i < (count - 1); i += 1) {
			x = left + (delta * i);
			final double y = value(new Real(x)).getValue();
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
		final StdDraw stddraw = new StdDraw();
		stddraw.setCanvasSize(1000, 700);
		StdDraw.setXscale(left, right);
		StdDraw.setYscale(min, max);

		Scalar tmp = new Real(left);
		double alpha = value(tmp).getValue();
		double beta = fun.value(tmp).getValue();
		double z = 0;
		for (double i = 0; i < (count - 1); i += 1) {
			z = left + (delta * i);
			tmp = new Real(z + delta);
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

//		StdDraw.save(Integer.toString(this.hashCode()) + ".png");

	}

	/**
	 * Method to compute the derivative of the function.
	 * 
	 * @return the derivative.
	 */
	default Function getDerivative() {
		if (derivative==null) {
		final Function fun = this;
		return new GenericFunction() {
			@Override
			public Scalar value(final Scalar input) {
				double dy = fun.value(new Real(input.getValue() + eps)).getValue() - fun.value(input).getValue();
				double dx = eps;
				return new Real(dy / dx);
			}
		};
		}
		else {
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
		if (n < 0) {
			return getPrimitiveIntegral(-n);
		}
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
	default Function getPrimitiveIntegral() {
		final EuclideanSpace space = (EuclideanSpace) Generator.getGenerator().getTrigonometricSpace(20);
		final Function projection = getProjection(space);
		return new FunctionTuple(new GenericFunction() {
			@Override
			public Scalar value(Scalar input) {
				return new Real(FunctionSpace.getIntegral(projection, one, ((FunctionSpace) space).getLeft(),
						input.getValue(), eps));
			}
		}.getCoordinates(space));
	}

	/**
	 * Method to get an n-th primitive integral.
	 * 
	 * @param n the degree.
	 * @return an n-th integral.
	 */
	default Function getPrimitiveIntegral(int n) {
		if (n < 0) {
			return getDerivative(-n);
		}
		if (n == 0) {
			return this;
		} else {
			return getPrimitiveIntegral().getPrimitiveIntegral(n - 1);
		}
	}

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
		if (this instanceof FunctionTuple && source.contains(this)) {
			return this;
		}
		return new FunctionTuple(getCoordinates(source));
	}
}
