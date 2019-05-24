package definitions.structures.generic.finitedimensional.defs.mappings.impl;

import java.util.Map;

import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;

import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.spaces.CoordinateSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.Tuple;

public class FiniteDimensionalLinearMapping implements IFiniteDimensionalLinearMapping {

	private final CoordinateSpace source;
	private final CoordinateSpace target;
	private final Map<Vector, Map<Vector, Double>> linearity;
	private final double[][] genericMatrix;

	public FiniteDimensionalLinearMapping(CoordinateSpace source, CoordinateSpace target,
			Map<Vector, Map<Vector, Double>> coordinates) throws Throwable {
		this.source = source;
		this.target = target;
		this.linearity = coordinates;
		genericMatrix = new double[((CoordinateSpace) getTarget()).dim()][getSource().dim()];
		int i = 0;
		for (final Vector vec1 : getSource().genericBaseToList()) {
			int j = 0;
			for (final Vector vec2 : ((CoordinateSpace) getTarget()).genericBaseToList()) {
				genericMatrix[j][i] = getLinearity(vec1).get(vec2);
				j++;
			}
			i++;
		}
	}

	@Override
	public final CoordinateSpace getSource() {
		return source;
	}

	@Override
	public final VectorSpace getTarget() {
		return target;
	}

	@Override
	public final Map<Vector, Map<Vector, Double>> getLinearity() {
		return linearity;
	}

	@Override
	public FiniteVector solve(Vector image) throws Throwable {
		double[][] matrix = getGenericMatrix();
		double[] imageVector = image.getGenericCoordinates();
		try {
			final org.apache.commons.math3.linear.RealVector apacheVector = new LUDecomposition(
					MatrixUtils.createRealMatrix(matrix)).getSolver().solve(MatrixUtils.createRealVector(imageVector));
			return new Tuple(apacheVector.toArray());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String toString() {
		String str = "";
		double[][] matrix;
		try {
			matrix = getGenericMatrix();
			double x;
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[i].length; j++) {
					x = matrix[i][j];
					str += " " + (x - (x % 0.001)) + " ";
				}
				str += " \r";
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return str;
	}

	@Override
	public final double[][] getGenericMatrix() throws Throwable {
		return genericMatrix;
	}

}
