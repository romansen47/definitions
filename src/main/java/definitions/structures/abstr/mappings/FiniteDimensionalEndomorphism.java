package definitions.structures.abstr.mappings;

import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public interface FiniteDimensionalEndomorphism extends VectorSpaceEndomorphism {

	/**
	 * method to get the adjugate matrix
	 *
	 * @param matrix the input matrix
	 * @param a      column index
	 * @param b      row index
	 * @return the adjugate at (i,j)=(a,b)
	 */
	default Scalar[][] adjugateMatrix(final Scalar[][] matrix, final int a, final int b) {
		final int k = matrix.length;
		final Scalar[][] adj = new Scalar[k - 1][k - 1];
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

	/**
	 * Method to compute the determinant of the linear self mapping.
	 *
	 * @param matrix the given matrix
	 * @return the determinant
	 */
	default Scalar det(final Scalar[][] matrix) {
		Scalar det = Generator.getInstance().getFieldGenerator().getRealLine().getZero();
		if (matrix.length == 1) {
			return matrix[0][0];
		}
		for (int i = 0; i < matrix.length; i++) {
			final Scalar[][] adj = this.adjugateMatrix(matrix, i, 0);
			if ((i % 2) == 0) {
				det = ((EuclideanSpace) this.getSource()).getField().get(((Real) det).getDoubleValue()
						+ (((Real) this.det(adj)).getDoubleValue() * ((Real) matrix[i][0]).getDoubleValue()));
			} else {
				det = ((EuclideanSpace) this.getSource()).getField().get(((Real) det).getDoubleValue()
						- (((Real) this.det(adj)).getDoubleValue() * ((Real) matrix[i][0]).getDoubleValue()));
			}
		}
		return det;
	}

}
