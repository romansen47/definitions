package definitions.structures.generic.finitedimensional.defs.vectors;

import java.awt.Color;

import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.IFiniteDimensionalFunctionSpace;
import deprecated.proprietary.StdDraw;

public interface Function extends FiniteVector {

	default double getLeft() {
		return -Math.PI;
	}

	default double getRight() {
		return Math.PI;
	}

	double value(double input) throws Throwable;

//	@Override
//	default Map<Vector, Double> getCoordinates(EuclideanSpace space) throws Throwable {
//		Map<Vector, Double> newCoordinates = new ConcurrentHashMap<>();
//		for (Vector baseVec : space.genericBaseToList()) {
//			newCoordinates.put(baseVec, space.product(this, baseVec));
//		}
//		return newCoordinates;
//	}

	default boolean equals(Function other, IFiniteDimensionalFunctionSpace source) throws Throwable {
		final int n = 100;
		double a = source.getIntervall()[0];
		double b = source.getIntervall()[1];
		for (int i = 0; i < n; i++) {
			if (value(a + i * (b - a) / 99.) != other.value(a + i * (b - a) / 99.)) {
				return false;
			}
		}
		return true;
	}

	@SuppressWarnings("unused")
	default void plot(double left, double right) throws Throwable {

		int count = 1000;

		double delta = (right - left) / count;
		double x = 0;
		double min = value((right - left) / 2.);
		double max = min;
		for (double i = 0; i < count - 1; i += 1) {
			x = left + delta * i;
			double y = value(x);
			if (y > max) {
				max = y;
			}
			if (y < min) {
				min = y;
			}
		}
		double d = max - min;
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
		for (double i = 0; i < count - 1; i += 1) {
			z = left + delta * i;
			StdDraw.setPenColor(Color.black);
			StdDraw.line(z, value(z), z + delta, value(z + delta));
		}
	}

	@SuppressWarnings("unused")
	default void plotCompare(double left, double right, Function fun) throws Throwable {

		int count = 1000;

		double delta = (right - left) / count;
		double x = 0;
		double min = value((right - left) / 2.);
		double max = min;
		for (double i = 0; i < count - 1; i += 1) {
			x = left + delta * i;
			double y = value(x);
			if (y > max) {
				max = y;
			}
			if (y < min) {
				min = y;
			}
		}
		double d = max - min;
		if (delta == 0) {
			min = min - 100;
			max = max + 100;
		}
		final StdDraw stddraw = new StdDraw();
		stddraw.setCanvasSize(1000, 700);
		StdDraw.setXscale(left, right);
		StdDraw.setYscale(min - 0.2 * d, max + 0.5 * d);
		double z = 0;
		for (double i = 0; i < count - 1; i += 1) {
			z = left + delta * i;
			StdDraw.setPenRadius(0.004);
			StdDraw.setPenColor(Color.blue);
			StdDraw.line(z, value(z), z + delta, value(z + delta));
			StdDraw.setPenRadius(0.002);
			StdDraw.setPenColor(Color.red);
			StdDraw.line(z, fun.value(z), z + delta, fun.value(z + delta));
		}
		double ans = 0;
	}
}
