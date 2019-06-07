package math.matrix;

public interface IMatrixTrix {

	final static math.IMathOp mathOperator = math.MathOp.getInstance();

	default double[] matrixMult(double[][] matrix, double[] vector) {
		final double[] y = new double[matrix.length];
		for (int i = 0; i < matrix.length; i++) {
			double tmp = 0;
			for (int j = 0; j < matrix[0].length; j++) {
				tmp += matrix[i][j] * vector[j];
			}
			y[i] = tmp;
		}
		return y;
	}

	// Only for quadratic matrices!!!
	default double[][] adjointMatrix(double[][] matrix, int a, int b) {
		final int k = matrix.length;
		final double[][] adj = new double[k - 1][k - 1];
		for (int i = 0; i < a; i++) {
			for (int j = 0; j < b; j++) {
				adj[i][j] = matrix[i][j];
			}
			for (int j = b + 1; j < k; j++) {
				adj[i][j - 1] = matrix[i][j];
			}
		}
		for (int i = a + 1; i < k; i++) {
			for (int j = 0; j < b; j++) {
				adj[i - 1][j] = matrix[i][j];
			}
			for (int j = b + 1; j < k; j++) {
				adj[i - 1][j - 1] = matrix[i][j];
			}
		}
		return adj;
	}

	default double det(double[][] matrix) {
		double det = 0;
		if (matrix.length == 1) {
			return matrix[0][0];
		}
		for (int i = 0; i < matrix.length; i++) {
			final double[][] adj = adjointMatrix(matrix, i, 0);
			if (i % 2 == 0) {
				det += det(adj) * matrix[i][0];
			} else {
				det += -det(adj) * matrix[i][0];
			}
		}
		return det;
	}

	default double[][] inverse(double[][] matrix) throws Throwable {
		if (matrix.length == 1 && matrix[0].length == 1) {
			final double in = matrix[0][0];
			if (in == 0.) {
				throw new Throwable();
			}
			return new double[][] { { 1. / in } };
		}
		final int k = matrix.length;
		final double[][] inv = new double[k][k];
		double det;
		try {
			det = 1.0 / det(matrix);
		} catch (final Exception e) {
			System.err.println("Division durch 0!");
			return new double[0][0];
		}
		for (int i = 0; i < k; i++) {
			for (int j = 0; j < k; j++) {
				inv[i][j] = Math.pow(-1, (double) i + (double) j) * det(adjointMatrix(matrix, j, i)) * det;
			}
		}
		return inv;
	}

}
