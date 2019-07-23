package definitions.structures.finitedimensional.real.mappings;

import definitions.structures.abstr.Endomorphism;
import definitions.structures.abstr.Scalar;
import definitions.structures.finitedimensional.real.Generator;
import definitions.structures.finitedimensional.real.mappings.impl.InvertibleSelfMapping;
import definitions.structures.finitedimensional.real.vectors.Real;

/**
 * Automorphism.
 * 
 * @author ro
 *
 */
public interface Automorphism extends Endomorphism, Isomorphism {

	@Override
	default Isomorphism getInverse() throws Throwable {
		final Scalar[][] matrix = getGenericMatrix();
		if ((matrix.length == 1) && (matrix[0].length == 1)) {
			final Scalar in = matrix[0][0];
			if (in.equals(new Real(0.))) {
				throw new Throwable();
			}
			return (InvertibleSelfMapping) Generator.getGenerator().getMappinggenerator()
					.getFiniteDimensionalLinearMapping(new Scalar[][] { { new Real(1. / in.getValue() )} });
		}
		final int k = matrix.length;
		final Scalar[][] inv = new Scalar[k][k];
		double det;
		try {
			det = 1.0 / det(matrix).getValue();
		} catch (final Exception e) {
			System.err.println("Division durch 0!");
			return (InvertibleSelfMapping) Generator.getGenerator().getMappinggenerator()
					.getFiniteDimensionalLinearMapping(new Scalar[0][0]);
		}
		for (int i = 0; i < k; i++) {
			for (int j = 0; j < k; j++) {
				inv[i][j] = new Real( 	Math.pow(-1, (double) i + (double) j) * 
										det(adjointMatrix(matrix, j, i)).getValue() * det
						);
			}
		}
		return (InvertibleSelfMapping) Generator.getGenerator().getMappinggenerator()
				.getFiniteDimensionalLinearMapping(inv);
	}
}
