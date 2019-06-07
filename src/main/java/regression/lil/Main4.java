package regression.lil;

import java.awt.Color;
import java.io.IOException;

import functions.Function;
import functions.IFunction;
import math.IVector;
import math.MathOp;
import proprietary.StdDraw;
import regression.DataSet;
import regression.IRegression;
import regression.LinReg;
import regression.Minimizer;

public class Main4 extends regression.Main {

	static double[][] vals;
	static double[][] newvals;

	public static void main(String[] args) throws IOException {
		values = readFile(PATH);
		newvals = values;

		final IFunction fun = new Function() {
			@Override
			public double value(double[] point) {
				return val(point[0], point[1], point[2], point[3], point[4], newvals) * 1.e-10;
			}
		};

		final double a = 22;
		final double b = 0;
		final double[] coeffs = new Minimizer(fun).find(new double[] { a, b, 5.8, 2.4, 0.0027 });

		System.out.println("Wert: " + val(a, b, 5.750578, 2.42977512, 0.002701427, newvals));
		System.out.println("Wert: " + val(coeffs[0], coeffs[1], coeffs[2], coeffs[3], coeffs[4], newvals));
		final double correlation = correlation(values[0], values[1]);
		System.out.println("Korrelationskoeffizient = " + correlation);

		final IFunction reg = new Function() {
			@Override
			public double value(double[] input) {
				return coeffs[0] + coeffs[1] * input[0] + coeffs[2] * Math.sin(coeffs[3] + coeffs[4] * input[0]);
			}
		};

		printDataToXml(coeffs, newvals, reg, correlation);
		System.out.println("Regression: f(x) = " + coeffs[0] + " + ( " + coeffs[1] + " ) * x + " + coeffs[2]
				+ " * sin ( " + coeffs[3] + " + " + coeffs[4] + " * x )");

	}

	private static void printDataToXml(double[] coeffs, double[][] newvals2, IFunction fun, double correlation)
			throws IOException {
		final double length = (int) (newvals2[0][newvals2[0].length - 1]);
		final DataSet[] data = new DataSet[(int) length];
		for (int i = 0; i < newvals2[0].length - 1; i++) {
			data[(int) newvals2[0][i]] = new DataSet((int) newvals2[0][i], newvals2[1][i], fun);
			for (int j = (int) newvals2[0][i] + 1; j < newvals2[0][i + 1]; j++) {
				data[j] = new DataSet(j, null, fun);
			}
		}

		for (int i = 0; i < data.length; i++) {
			System.out.println(data[i].toString());
		}

		IRegression.preparePlot(values, 1000, 500);
		IRegression.drawInput(values);
		final double prec = 10;
		StdDraw.setPenRadius(0.01);
		for (double i = 0; i < values[0][values[0].length - 1]; i += prec) {
			StdDraw.setPenColor(Color.red);
			StdDraw.line(i, fun.value(new double[] { i }), i + prec, fun.value(new double[] { i + prec }));
		}
	}

	private static double correlation(double[] ds, double[] ds2) {
		final IVector mathob = new MathOp(1.e-5);
		return mathob.ScalarProduct(ds, ds2) / (mathob.MagnitudeOfVector(ds) * mathob.MagnitudeOfVector(ds2));
	}

	private static double val(double a, double b, double c, double d, double e, double[][] values) {
		double ans = 0.0;
		for (int i = 0; i < values[0].length; i++) {
			ans += Math.pow(values[1][i] - a - b * values[0][i] - c * Math.sin(d + e * values[0][i]), 2);
		}
		return ans;
	}

	public static double[][] centralize(double[][] vals) {
		final IRegression linreg = new LinReg(vals, 1);
		final double[][] newvalues = new double[2][vals[0].length];
		newvalues[0] = vals[0];
		for (int i = 0; i < vals[0].length; i++) {
			newvalues[1][i] = vals[1][i] - linreg.getPolynomial().eval(vals[0][i]);
		}
		return newvalues;
	}
}
