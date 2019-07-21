package definitions.structures.abstr;

/**
 * Finite dimensional linear self mapping.
 * 
 * @author ro
 */
public interface Endomorphism extends Homomorphism {

	/**
	 * Method to compute the determinant of the linear self mapping.
	 * 
	 * @return the determinant
	 */
	default double det(final double[][] matrix) {
		double det = 0;
		if (matrix.length == 1) {
			return matrix[0][0];
		}
		for (int i = 0; i < matrix.length; i++) {
			final double[][] adj = adjointMatrix(matrix, i, 0);
			if ((i % 2) == 0) {
				det += det(adj) * matrix[i][0];
			} else {
				det += -det(adj) * matrix[i][0];
			}
		}
		return det;
	}

	default double[][] adjointMatrix(final double[][] matrix, final int a, final int b) {
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

}
