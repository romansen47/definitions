package deprecated.functions;

public class Polynomial implements IPolynomial {

	private final double[] coefficients;
	private final int degree;

	public Polynomial(final double[] coeffs) {
		int n = coeffs.length;
		while ((n > 0) && (coeffs[n - 1] == 0)) {
			n = n - 1;
		}
		this.degree = n;
		this.coefficients = new double[this.getDegree()];
		for (int i = 0; i < this.getDegree(); i++) {
			this.getCoefficients()[i] = coeffs[i];
		}
	}

	@Override
	public double eval(final double x) {
		double y = 0;
		for (int i = 0; i < this.getDegree(); i++) {
			y += this.getCoefficients()[i] * Math.pow(x, i);
		}
		return y;
	}

	/**
	 * @return the degree
	 */
	@Override
	public int getDegree() {
		return this.degree;
	}

	/**
	 * @return the coefficients
	 */
	@Override
	public double[] getCoefficients() {
		return this.coefficients;
	}

	@Override
	public String toString() {
		String ans = "";
		for (int i = 0; i < (this.getDegree() - 1); i++) {
			ans += this.getCoefficients()[i] + ", ";
		}
		ans += this.getCoefficients()[this.getDegree() - 1];
		return ans;
	}

}
