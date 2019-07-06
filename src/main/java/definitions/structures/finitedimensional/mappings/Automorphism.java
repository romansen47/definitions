package definitions.structures.finitedimensional.mappings;

import definitions.structures.finitedimensional.Generator;
import definitions.structures.finitedimensional.mappings.impl.InvertibleSelfMapping;
/**
 * Automorphism.
 * 
 * @author ro
 *
 */
public interface Automorphism extends Endomorphism, Isomorphism {
	
	default Isomorphism getInverse() throws Throwable {
		final double[][] matrix=getGenericMatrix();
		if ((matrix.length == 1) && (matrix[0].length == 1)) {
			final double in = matrix[0][0];
			if (in == 0.) {
				throw new Throwable();
			}
			return (InvertibleSelfMapping) Generator.getGenerator().getMappinggenerator()
					.getFiniteDimensionalLinearMapping(new double[][] { { 1. / in } });
		}
		final int k = matrix.length;
		final double[][] inv = new double[k][k];
		double det;
		try {
			det = 1.0 / det(matrix);
		} catch (final Exception e) {
			System.err.println("Division durch 0!");
			return (InvertibleSelfMapping) Generator.getGenerator().getMappinggenerator()
					.getFiniteDimensionalLinearMapping(new double[0][0]);
		}
		for (int i = 0; i < k; i++) {
			for (int j = 0; j < k; j++) {
				inv[i][j] = Math.pow(-1, (double) i + (double) j) * det(adjointMatrix(matrix, j, i)) * det;
			}
		}
		return (InvertibleSelfMapping) Generator.getGenerator().getMappinggenerator()
				.getFiniteDimensionalLinearMapping(inv);
	}
}
