/**
 *
 */
package plotter;

import java.awt.Color;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import settings.GlobalSettings;
import solver.StdDraw;

/**
 * @author RoManski
 *
 */
public interface Plotter {

	default void plot(final Plotable fun, final double left, final double right) {
		final StdDraw stddraw = new StdDraw();
		final int count = 500;
		final double delta = (right - left) / count;
		this.preparePlot(fun, left, right, stddraw, count, delta);
		double z = 0;
		StdDraw.setPenRadius(0.001);
		for (double i = 0; i < (count - 1); i += 1) {
			z = left + (delta * i);
			StdDraw.setPenColor(Color.blue);
			for (final Vector vec : ((Function) fun).getField().genericBaseToList()) {
				final Scalar sc = (Scalar) ((Function) fun).value(((Function) fun).getField().getField().get(z));
				final Scalar sc2 = (Scalar) ((Function) fun)
						.value(((Function) fun).getField().getField().get(z + delta));
				StdDraw.line(z, this.getValue((sc.getCoordinates().get(((Function) fun).getField().getBaseVec(vec)))),
						z + delta,
						this.getValue(sc2.getCoordinates().get(((Function) fun).getField().getBaseVec(vec))));
			}
		}

	}

	default void plotCompare(final Plotable fun1, final Plotable fun2, final double left, final double right) {
		final StdDraw stddraw = new StdDraw();
		final int count = 1000;
		final double delta = (right - left) / count;
		this.preparePlot(fun1, left, right, stddraw, count, delta);
		Scalar tmp = ((Function) fun1).getField().get(left);
		double alpha = ((Real) ((Function) fun1).value(tmp)).getDoubleValue();
		double beta = ((Real) ((Function) fun2).value(tmp)).getDoubleValue();
		double z = 0;
		for (double i = 0; i < (count - 1); i += 1) {
			z = left + (delta * i);
			tmp = ((Function) fun1).getField().get(z + delta);
			final double alphaNext = ((Real) ((Function) fun1).value(tmp)).getDoubleValue();
			final double betaNext = ((Real) ((Function) fun2).value(tmp)).getDoubleValue();
			StdDraw.setPenRadius(0.0035);
			StdDraw.setPenColor(Color.blue);
			StdDraw.line(z, alpha, z + delta, alphaNext);
			StdDraw.setPenRadius(0.0025);
			StdDraw.setPenColor(Color.red);
			StdDraw.line(z, beta, z + delta, betaNext);
			alpha = alphaNext;
			beta = betaNext;
		}
		StdDraw.save(GlobalSettings.PLOTS + Integer.toString(hashCode()) + ".png");
	}

	default void preparePlot(final Plotable fun, final double left, final double right, final StdDraw stddraw,
			final int count, final double delta) {
		double x = 0;
		Field f = ((Function) fun).getField();
		Scalar h = (Scalar) ((Function) fun).value(((Function) fun).getField().getField().get((right - left) / 2.));
		double min = this.getValue(h);
		double max = min;
		for (double i = 0; i < (count - 1); i += 1) {
			x = left + (delta * i);
			final double y = this
					.getValue(((Scalar) ((Function) fun).value(((Function) fun).getField().getField().get(x))));
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

	default Double getValue(Scalar h) {
		if (h instanceof Real) {
			return ((Real) h).doubleValue();
		}
		return null;
	}

}
