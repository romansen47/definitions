package deprecated.functions;

public class Derivative {

	final double feinheit;
	final IFunction fun;

	public Derivative(final double fein, final IFunction fun) {
		this.feinheit = fein;
		this.fun = fun;
	}

	public double value(final double[] point, final double[] direction) {
		final double[] delta = new double[point.length];
		for (int i = 0; i < point.length; i++) {
			delta[i] = point[i] + (this.feinheit * direction[i]);
		}
		return (1.0 / this.feinheit) * (this.fun.value(delta) - this.fun.value(point));
	}

	public double[] Gradient(final double[] point) {
		final double[] Grad = new double[point.length];
		for (int i = 0; i < point.length; i++) {
			final double[] vec = new double[point.length];
			for (int j = 0; j < point.length; j++) {
				vec[j] = 0;
			}
			vec[i] = 1;
			Grad[i] = this.value(point, vec);
		}
		return Grad;
	}

	public double[][] HesseMatrix(final double[] point) {
		final double[][] Hesse = new double[point.length][point.length];
		for (int i = 0; i < point.length; i++) {
			final int j = i;
			final IFunction fun = new Function() {
				@Override
				public double value(final double[] point) {
					return Derivative.this.Gradient(point)[j];
				}
			};
			final Derivative Deriv = new Derivative(this.feinheit, fun);
			Hesse[i] = Deriv.Gradient(point);
		}
		return Hesse;
	}

}
