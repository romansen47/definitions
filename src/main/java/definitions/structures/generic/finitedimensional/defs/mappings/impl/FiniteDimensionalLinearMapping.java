package definitions.structures.generic.finitedimensional.defs.mappings.impl;

import java.util.Map;

import definitions.structures.abstr.LinearMapping;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;

public class FiniteDimensionalLinearMapping extends LinearMapping implements IFiniteDimensionalLinearMapping {

	public FiniteDimensionalLinearMapping(final EuclideanSpace source, final EuclideanSpace target,
			final Map<Vector, Map<Vector, Double>> coordinates) {
		super(source, target);
		this.linearity = coordinates;
		this.genericMatrix = new double[((EuclideanSpace) this.getTarget()).dim()][((EuclideanSpace) this.getSource())
				.dim()];
		int i = 0;
		for (final Vector vec1 : source.genericBaseToList()) {
			int j = 0;
			for (final Vector vec2 : target.genericBaseToList()) {
				this.genericMatrix[j][i] = this.getLinearity(vec1).get(vec2);
				j++;
			}
			i++;
		}
	}

	@Override
	public final VectorSpace getSource() {
		return this.source;
	}

	@Override
	public final VectorSpace getTarget() {
		return this.target;
	}

	@Override
	public final Map<Vector, Map<Vector, Double>> getLinearity() {
		return this.linearity;
	}

	@Override
	public String toString() {
		String str = "";
		double[][] matrix;
		try {
			matrix = this.getGenericMatrix();
			double x;
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[i].length; j++) {
					x = matrix[i][j];
					str += " " + (x - (x % 0.001)) + " ";
				}
				str += " \r";
			}
		} catch (final Throwable e) {
			e.printStackTrace();
		}

		return str;
	}

	@Override
	public final double[][] getGenericMatrix() {
		return this.genericMatrix;
	}

}
