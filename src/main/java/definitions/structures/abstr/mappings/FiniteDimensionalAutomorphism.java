package definitions.structures.abstr.mappings;

import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.mappings.impl.InvertibleSelfMapping;
import definitions.structures.euclidean.mappings.impl.MappingGenerator;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public interface FiniteDimensionalAutomorphism extends FiniteDimensionalEndomorphism, VectorSpaceAutomorphism {

	/**
	 * {@inheritDoc}
	 */
	@Override
	default VectorSpaceIsomorphism getInverse() {
		final Scalar[][] matrix = getGenericMatrix();
		if ((matrix.length == 1) && (matrix[0].length == 1)) {
			final Scalar in = matrix[0][0];
			if (in.equals(RealLine.getInstance().getZero())) {
				Generator.getInstance().getLogger().info("devision by 0");
				return null;
			}
			return (InvertibleSelfMapping) MappingGenerator.getInstance()
					.getFiniteDimensionalLinearMapping(new Scalar[][] {
						{ ((EuclideanSpace) getSource()).getField().get(1. / ((Real) in).getDoubleValue()) } });
		}
		final int k = matrix.length;
		final Scalar[][] inv = new Scalar[k][k];
		double det;
		try {
			det = 1.0 / ((Real) det(matrix)).getDoubleValue();
		} catch (final Exception e) {
			System.err.println("Division durch 0!");
			return (InvertibleSelfMapping) MappingGenerator.getInstance()
					.getFiniteDimensionalLinearMapping(new Scalar[0][0]);
		}
		for (int i = 0; i < k; i++) {
			for (int j = 0; j < k; j++) {
				inv[i][j] = ((EuclideanSpace) getSource()).getField().get(Math.pow(-1, (double) i + (double) j)
						* ((Real) det(adjugateMatrix(matrix, j, i))).getDoubleValue() * det);
			}
		}
		return (InvertibleSelfMapping) Generator.getInstance().getMappingGenerator()
				.getFiniteDimensionalLinearMapping(inv);
	}
}
