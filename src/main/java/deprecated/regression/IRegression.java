package deprecated.regression;

import java.awt.Color;

import deprecated.proprietary.StdDraw;

public interface IRegression {

	deprecated.functions.IPolynomial getPolynomial();

	double getDistance();

	void solveAndDraw(Color col, double[][] values, double precision);

	static void drawInput(double[][] values) {
		deprecated.proprietary.StdDraw.setPenRadius(0.01);
		for (int i = 0; i < values[0].length; i += 1) {
			deprecated.proprietary.StdDraw.point(values[0][i], values[1][i]);
		}
	}

	static void preparePlot(double[][] values, int dimsX, int dimsY) {
		double min = values[1][0];
		double max = values[1][0];

		for (int i = 1; i < values[0].length; i++) {
			if (min > values[1][i]) {
				min = values[1][i];
			}
			if (max < values[1][i]) {
				max = values[1][i];
			}
		}
		final int tolerance = Math.max((int) min, 10);
		final StdDraw stddraw = new StdDraw();
		stddraw.setCanvasSize(dimsX, dimsY);
		StdDraw.setXscale(0, values[0][values[0].length - 1]);
		StdDraw.setYscale(min - tolerance, max + tolerance);
	}

}
