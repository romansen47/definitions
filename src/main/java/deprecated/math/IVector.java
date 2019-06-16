package deprecated.math;

public interface IVector {

	default double[] AdditionOfVectors(final double[] VEC1, final double[] VEC2) {
		final double[] addarrays = new double[2];
		addarrays[0] = VEC1[0] + VEC2[0];
		addarrays[1] = VEC1[1] + VEC2[1];
		return addarrays;
	}

	default double MagnitudeOfVector(final double[] VEC) {
		if (VEC.length < 2) {
			return Math.abs(VEC[0]);
		}
		return SQRT(ScalarProduct(VEC, VEC));
	} /*
		 * Usage of native Math.sqrt-function weirdly lacks precision
		 */

	default double[] Projection(final double[] X, final double[] Y) {
		return ScalarMultiplication(ScalarProduct(X, Y), ScalarMultiplication(1 / MagnitudeOfVector(Y), Y));
	}

	default double[] ProjectionComplement(final double[] X, final double[] Y) {
		return AdditionOfVectors(X, ReversalOfVector(Projection(X, Y)));
	}

	default double[] ReversalOfVector(final double[] VEC) {
		return ScalarMultiplication(-1, VEC);
	}

	default double[] ScalarMultiplication(final double skalar, final double[] VECTOR) {
		final double[] skalarmult = new double[2];
		skalarmult[0] = skalar * VECTOR[0];
		skalarmult[1] = skalar * VECTOR[1];
		return skalarmult;
	}

	default double ScalarProduct(final double[] VEC1, final double[] VEC2) {
		final double skalprod = (VEC1[0] * VEC2[0]) + (VEC1[1] * VEC2[1]);
		return skalprod;
	}

	default double SQRT(final double Square) {
		return (Math.sqrt(Square));
		/*
		 * double tmp=Square/2; while (Abs(tmp*tmp-Square)>Config.
		 * SquareRootCorrectness){ tmp=0.5*(tmp+Square/tmp); } return tmp;
		 */
	}

	default double[] UnitVector(final double[] VEC) {
		return ScalarMultiplication(1 / MagnitudeOfVector(VEC), VEC);
	}

}
