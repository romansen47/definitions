package functions;

public interface IPolynomial {

	double eval(double x);

	@Override
	String toString();

	int getDegree();

	double[] getCoefficients();
}
