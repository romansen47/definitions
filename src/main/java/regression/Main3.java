package regression;

import java.io.IOException;

import functions.Function;
import functions.IFunction;
import math.IVector;
import math.MathOp;

public class Main3 extends regression.Main {

	static double[][] vals;
	static double[][] newvals;

	public static void main(String[] args) throws IOException {
		values = readFile(PATH);
		newvals = centralize(values);
		double min = 200000.0;
		double a0 = 0;
		double b0 = 0;
		double c0 = 0;
		for (double a = 5.7; a < 5.8; a += 0.01) {
			for (double b = 2.2; b < 2.5; b += 0.01) {
				for (double c = 0.0027; c < 0.0035; c += 0.0001) {
					final double val = val(a, b, c, newvals);
					if (val < min) {
						a0 = a;
						b0 = b;
						c0 = c;
						min = val;
					}
					// System.out.println(a+", "+b+", "+c+" -value="+val);
				}
			}
		}
		final IRegression reg = new LinReg(values, 1);
		final double[] alpha = reg.getPolynomial().getCoefficients();
		System.out.println(alpha[0] + "+(" + alpha[1] + ")*x" + " + " + a0 + "*sin(" + b0 + "+" + c0 + "*x)");

		final double[][] valsToPlot = new double[2][2 * values[0].length];
		final double[][] altInput = new double[2][2 * values[0].length];
		for (int i = 0; i < values[0].length; i++) {
			altInput[0][i] = values[0][i];
			altInput[1][i] = values[1][i];
			valsToPlot[0][i] = values[0][i];
			valsToPlot[1][i] = reg.getPolynomial().eval(values[0][i]) + a0 * Math.sin(b0 + c0 * values[0][i]);
		}
		for (int i = values[0].length; i < 2 * values[0].length; i++) {
			altInput[0][i] = values[0][values[0].length - 1] - values[0].length + i;
			altInput[1][i] = reg.getPolynomial().eval(altInput[0][i]);
			valsToPlot[0][i] = values[0][values[0].length - 1] - values[0].length + i;
			valsToPlot[1][i] = reg.getPolynomial().eval(valsToPlot[0][i]) + a0 * Math.sin(b0 + c0 * valsToPlot[0][i]);
		}
//		IRegression.preparePlot(altInput,2*DIMX,DIMY);
//		IRegression.drawInput(altInput);
//		StdDraw.setPenRadius(0.01);
//        for (int i = 0; i < valsToPlot[0].length-1; i+=1) {
//    		StdDraw.setPenColor(Color.RED);
//        	StdDraw.line(valsToPlot[0][i],valsToPlot[1][i],valsToPlot[0][i+1],valsToPlot[1][i+1]);
//        }

		final IFunction fun = new Function() {
			@Override
			public double value(double[] point) {
				return val(point[0], point[1], point[2], newvals) / 100000.0;
			}
		};

		final double[] coeffs = new Minimizer(fun).find(new double[] { 5.750578, 2.42977512, 0.002701427 });
		System.out.println(coeffs[0] + "," + coeffs[1] + "," + coeffs[2]);

		System.out.println("Korellationskoeffizient = " + correlation(values[0], values[1]));

	}

	private static double correlation(double[] ds, double[] ds2) {
		final IVector mathob = new MathOp(1.e-7);
		return mathob.ScalarProduct(ds, ds2) / (mathob.MagnitudeOfVector(ds) * mathob.MagnitudeOfVector(ds2));
	}

	private static double val(double a, double b, double c, double[][] values) {
		double ans = 0.0;
		for (int i = 0; i < values[0].length; i++) {
			ans += Math.pow(values[1][i] - a * Math.sin(b + c * values[0][i]), 2);
		}
		return ans;
	}

	@SuppressWarnings("unused")
	private static double[][] getAsinVals(double[][] newvals) {
		final double max = getMax(newvals);
		final double[][] normvals = normalizeValues(max, newvals);
		final double[][] asinvals = new double[2][normvals[0].length];
		asinvals[0] = newvals[0];
		for (int i = 0; i < newvals[0].length; i++) {
			asinvals[1][i] = Math.asin(normvals[1][i]);
			System.out.println(newvals[0][i] + ", " + asinvals[1][i]);
		}
		return asinvals;
	}

	private static double[][] normalizeValues(double max, double[][] newvals) {
		final double[][] normvals = new double[2][newvals[0].length];
		normvals[0] = newvals[0];
		for (int i = 0; i < newvals[0].length; i++) {
			normvals[1][i] = newvals[1][i] / max;
		}
		return normvals;
	}

	@SuppressWarnings("unused")
	private static double getMin(double[][] newvals) {
		double min = Math.abs(newvals[1][0]);
		for (int i = 0; i < newvals[0].length; i++) {
			min = Math.min(min, Math.abs(newvals[1][i]));
		}
		return min;
	}

	private static double getMax(double[][] newvals) {
		double max = Math.abs(newvals[1][0]);
		for (int i = 0; i < newvals[0].length; i++) {
			max = Math.max(max, Math.abs(newvals[1][i]));
		}
		return max;
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
