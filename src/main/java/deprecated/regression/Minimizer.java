package deprecated.regression;

import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealVector;

import deprecated.functions.Derivative;
import deprecated.functions.IFunction;
import deprecated.math.MathOp;

public class Minimizer {

	final IFunction fun;
	final Derivative der;

	final double eps = 1.e-7;

	public Minimizer(final IFunction fun) {
		this.fun = fun;
		this.der = new Derivative(this.eps, fun);
	}

	public double[] find(final double[] start) {
		double[] tmp = start;
		while (this.norm(this.der.Gradient(tmp)) > this.eps) {
			tmp = this.NewtonStep(tmp);
		}
		return tmp;
	}

	private double[] NewtonStep(final double[] tmp) {
		final double[] ans = tmp.clone();
		final double[] minGrad = tmp.clone();
		for (int i = 0; i < tmp.length; i++) {
			minGrad[i] = -minGrad[i];
		}
		final RealVector tmpdelta = new LUDecomposition(MatrixUtils.createRealMatrix(this.der.HesseMatrix(tmp)))
				.getSolver().solve(MatrixUtils.createRealVector((this.der).Gradient(tmp)));
		for (int i = 0; i < tmp.length; i++) {
			ans[i] -= tmpdelta.getEntry(i);
		}
		String str = "";
		for (final double val : ans) {
			str += val + " ; ";
		}

		System.out.println(str);
		return ans;
	}

	private double norm(final double[] start) {
		return new MathOp(1.e-5).MagnitudeOfVector(start);
	}

}
