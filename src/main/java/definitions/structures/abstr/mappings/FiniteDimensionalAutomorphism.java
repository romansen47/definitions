package definitions.structures.abstr.mappings;

import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.mappings.impl.InvertibleSelfMapping;
import definitions.structures.euclidean.mappings.impl.MappingGenerator;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public interface FiniteDimensionalAutomorphism extends FiniteDimensionalEndomorphism, Automorphism {

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Isomorphism getInverse() {
		final Scalar[][] matrix = this.getGenericMatrix();
		if ((matrix.length == 1) && (matrix[0].length == 1)) {
			final Scalar in = matrix[0][0];
			if (in.equals(RealLine.getInstance().getZero())) {
				Generator.getInstance().getLogger().info("devision by 0");
				return null;
			}
			return (InvertibleSelfMapping) MappingGenerator.getInstance().getFiniteDimensionalLinearMapping(
					new Scalar[][] { { ((EuclideanSpace) this.getSource()).getField().get(1. / in.getDoubleValue()) } });
		}
		final int k = matrix.length;
		final Scalar[][] inv = new Scalar[k][k];
		double det;
		try {
			det = 1.0 / this.det(matrix).getDoubleValue();
		} catch (final Exception e) {
			System.err.println("Division durch 0!");
			return (InvertibleSelfMapping) MappingGenerator.getInstance()
					.getFiniteDimensionalLinearMapping(new Scalar[0][0]);
		}
		for (int i = 0; i < k; i++) {
			for (int j = 0; j < k; j++) {
				inv[i][j] = ((EuclideanSpace) this.getSource()).getField().get(Math.pow(-1, (double) i + (double) j)
						* this.det(this.adjointMatrix(matrix, j, i)).getDoubleValue() * det);
			}
		}
		return (InvertibleSelfMapping) Generator.getInstance().getMappingGenerator()
				.getFiniteDimensionalLinearMapping(inv);
	}
}
