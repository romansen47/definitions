package deprecated.regression;

import java.awt.Color;

import deprecated.proprietary.StdDraw;

public abstract class Regression implements IRegression {

	final double[][] approximatedValues;
	private double distance;

	public Regression(final double[][] values) {
		this.approximatedValues = values;
	}

	@Override
	public void solveAndDraw(final Color col, final double[][] values, final double prec) {
		StdDraw.setPenRadius(0.01);
		for (double i = 0; i < values[0][values[0].length - 1]; i += prec) {
			StdDraw.setPenColor(col);
			StdDraw.line(i, this.getPolynomial().eval(i), i + prec, this.getPolynomial().eval(i + prec));
		}
		final int degr = this.getPolynomial().getDegree() - 1;
		System.out.print("Regression " + degr + "-ten Grades geplottet. ");
	}

	@Override
	public double getDistance() {
		return this.distance;
	}

	void setDistance(final double distance) {
		this.distance = distance;
	}

}
