/**
 *
 */
package plotter;

import java.awt.Color;
import java.sql.Timestamp;
import java.util.Map;

import org.apache.logging.log4j.LogManager;

import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Quaternion;
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
		LogManager.getLogger(getClass()).debug("plotting {} over [{},{}]", fun, left, right);
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
				final Scalar sc = (Scalar) ((Function) fun).value(((Function) fun).getField().get(z));
				final Scalar sc2 = (Scalar) ((Function) fun).value(((Function) fun).getField().get(z + delta));
				Map<Vector, Scalar> scCoordinates = sc.getCoordinates();
				Map<Vector, Scalar> sc2Coordinates = sc2.getCoordinates();
				Double x1 = this.getValue(scCoordinates.get(((Function) fun).getField().getBaseVec(vec)));
				Double x2 = this.getValue(sc2Coordinates.get(((Function) fun).getField().getBaseVec(vec)));
				StdDraw.line(z, x1, z + delta, x2);
			}
		}
	}

	default void plotCompare(final Plotable fun1, final Plotable fun2, final double left, final double right) {
		LogManager.getLogger(getClass()).debug("plotting {} against {} over [{},{}]", fun1, fun2, left, right);
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
		String name = new Timestamp(System.currentTimeMillis()).toString();
		LogManager.getLogger(this.getClass()).debug(name);
		name = name.replaceAll("\\s", "_").replaceAll("\\:", ".");
		LogManager.getLogger(this.getClass()).debug("plotting {} to file {}.png", this, name);
		StdDraw.save(GlobalSettings.PLOTS + name + ".png");
	}

	default void preparePlot(final Plotable fun, final double left, final double right, final StdDraw stddraw,
			final int count, final double delta) {
		double x = 0;
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
		} else {
			if (h instanceof Quaternion) {
				return ((Real) ((Quaternion) h).getReal()).getRepresentant();
			}
		}
		return null;
	}

}
