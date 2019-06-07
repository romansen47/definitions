package functions;

public class TestPolynomials {

	public static void main(String[] args) {

		final double[] values = new double[4];
		values[0] = 1;
		values[1] = 2;
		values[2] = 1;
		values[3] = 0;
		final IPolynomial pol = new Polynomial(values);

		System.out.println(pol.eval(0));
		System.out.println(pol.eval(1));
		System.out.println(pol.eval(2));

	}

}
