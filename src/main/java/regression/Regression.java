package regression;

import java.awt.Color;

import proprietary.StdDraw;

public abstract class Regression implements IRegression {

	final double[][] approximatedValues;
	private double distance;

	public Regression(double[][] values) {
		approximatedValues = values;
	}

	@Override
	public void solveAndDraw(Color col, double[][] values, double prec) {
		StdDraw.setPenRadius(0.01);
		for (double i = 0; i < values[0][values[0].length - 1]; i += prec) {
			StdDraw.setPenColor(col);
			StdDraw.line(i, getPolynomial().eval(i), i + prec, getPolynomial().eval(i + prec));
		}
		final int degr = getPolynomial().getDegree() - 1;
		System.out.print("Regression " + degr + "-ten Grades geplottet. ");
	}

	@Override
	public double getDistance() {
		return distance;
	}

	void setDistance(double distance) {
		this.distance = distance;
	}

}
