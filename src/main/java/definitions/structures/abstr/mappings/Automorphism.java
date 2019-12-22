package definitions.structures.abstr.mappings;

import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.mappings.impl.InvertibleSelfMapping;
import definitions.structures.euclidean.mappings.impl.MappingGenerator;

/**
 * Automorphism.
 * 
 * @author ro
 *
 */
public interface Automorphism extends Endomorphism, Isomorphism {
	/**
	 * {@inheritDoc}
	 */
	@Override
	default Isomorphism getInverse() throws Throwable {
		final Scalar[][] matrix = this.getGenericMatrix();
		if ((matrix.length == 1) && (matrix[0].length == 1)) {
			final Scalar in = matrix[0][0];
			if (in.equals(RealLine.getInstance().getZero())) {
				throw new Throwable();
			}
			return (InvertibleSelfMapping) MappingGenerator.getInstance().getFiniteDimensionalLinearMapping(
					new Scalar[][] { { this.getSource().getField().get(1. / in.getValue()) } });
		}
		final int k = matrix.length;
		final Scalar[][] inv = new Scalar[k][k];
		double det;
		try {
			det = 1.0 / this.det(matrix).getValue();
		} catch (final Exception e) {
			System.err.println("Division durch 0!");
			return (InvertibleSelfMapping) MappingGenerator.getInstance()
					.getFiniteDimensionalLinearMapping(new Scalar[0][0]);
		}
		for (int i = 0; i < k; i++) {
			for (int j = 0; j < k; j++) {
				inv[i][j] = this.getSource().getField().get(Math.pow(-1, (double) i + (double) j)
						* this.det(this.adjointMatrix(matrix, j, i)).getValue() * det);
			}
		}
		return (InvertibleSelfMapping) Generator.getInstance().getMappinggenerator()
				.getFiniteDimensionalLinearMapping(inv);
	}
}
