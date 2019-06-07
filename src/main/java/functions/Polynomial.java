package functions;

public class Polynomial implements IPolynomial {

	private final double[] coefficients;
	private final int degree;

	public Polynomial(double[] coeffs) {
		int n = coeffs.length;
		while (n > 0 && coeffs[n - 1] == 0) {
			n = n - 1;
		}
		degree = n;
		coefficients = new double[getDegree()];
		for (int i = 0; i < getDegree(); i++) {
			getCoefficients()[i] = coeffs[i];
		}
	}

	@Override
	public double eval(double x) {
		double y = 0;
		for (int i = 0; i < getDegree(); i++) {
			y += getCoefficients()[i] * Math.pow(x, i);
		}
		return y;
	}

	/**
	 * @return the degree
	 */
	@Override
	public int getDegree() {
		return degree;
	}

	/**
	 * @return the coefficients
	 */
	@Override
	public double[] getCoefficients() {
		return coefficients;
	}

	@Override
	public String toString() {
		String ans = "";
		for (int i = 0; i < getDegree() - 1; i++) {
			ans += getCoefficients()[i] + ", ";
		}
		ans += getCoefficients()[getDegree() - 1];
		return ans;
	}

}
