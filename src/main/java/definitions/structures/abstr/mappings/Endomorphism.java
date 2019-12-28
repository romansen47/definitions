package definitions.structures.abstr.mappings;

import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 * Finite dimensional linear self mapping.
 * 
 * @author ro
 */
public interface Endomorphism extends VectorSpaceHomomorphism {

	default Scalar[][] adjointMatrix(final Scalar[][] matrix, final int a, final int b) {
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
	 * @return the determinant
	 */
	default Scalar det(final Scalar[][] matrix) {
		Scalar det = RealLine.getInstance().getZero();
		if (matrix.length == 1) {
			return matrix[0][0];
		}
		for (int i = 0; i < matrix.length; i++) {
			final Scalar[][] adj = this.adjointMatrix(matrix, i, 0);
			if ((i % 2) == 0) {
				det = ((EuclideanSpace)this.getSource()).getField()
						.get(det.getValue() + this.det(adj).getValue() * matrix[i][0].getValue());
			} else {
				det = ((EuclideanSpace)this.getSource()).getField()
						.get(det.getValue() - this.det(adj).getValue() * matrix[i][0].getValue());
			}
		}
		return det;
	}

}
