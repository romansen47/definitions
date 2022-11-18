package definitions.structures.abstr.mappings;

import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.mappings.impl.InvertibleSelfMapping;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import exceptions.DevisionByZeroException;

public interface FiniteDimensionalAutomorphism extends FiniteDimensionalEndomorphism, VectorSpaceAutomorphism {

	/**
	 * {@inheritDoc}
	 */
	@Override
	default VectorSpaceIsomorphism getInverse() throws DevisionByZeroException {
		final Scalar[][] matrix = this.getGenericMatrix();
		if ((matrix.length == 1) && (matrix[0].length == 1)) {
			final Scalar in = matrix[0][0];
			if (in.equals(Generator.getInstance().getFieldGenerator().getRealLine().getZero())) {
				Generator.getInstance().getLogger().debug("devision by 0");
				return null;
			}
			return (InvertibleSelfMapping) Generator.getInstance().getMappingGenerator()
					.getFiniteDimensionalLinearMapping(new Scalar[][] { {
							((EuclideanSpace) this.getSource()).getField().get(1. / ((Real) in).getDoubleValue()) } });
		}
		final int k = matrix.length;
		final Scalar[][] inv = new Scalar[k][k];
		double det;
		try {
			det = 1.0 / ((Real) this.det(matrix)).getDoubleValue();
		} catch (final Exception e) {
			throw new DevisionByZeroException();
		}
		for (int i = 0; i < k; i++) {
			for (int j = 0; j < k; j++) {
				inv[i][j] = ((EuclideanSpace) this.getSource()).getField().get(Math.pow(-1, (double) i + (double) j)
						* ((Real) this.det(this.adjugateMatrix(matrix, j, i))).getDoubleValue() * det);
			}
		}
		return (InvertibleSelfMapping) Generator.getInstance().getMappingGenerator()
				.getFiniteDimensionalLinearMapping(inv);
	}
}
