package definitions.structures.generic.finitedimensional.finitedimensionalspaces;

import java.util.Map;

import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;

import definitions.structures.generic.finitedimensional.finitedimensionalspaces.linearmappings.IFiniteDimensionalLinearMapping;

public class FiniteDimensionalLinearMapping implements IFiniteDimensionalLinearMapping {

	private final IFiniteDimensionalVectorSpace source;
	private final IFiniteDimensionalVectorSpace target;
	private final Map<IFiniteVector, Map<IFiniteVector, Double>> linearity;

	protected FiniteDimensionalLinearMapping(IFiniteDimensionalVectorSpace source, IFiniteDimensionalVectorSpace target,
			Map<IFiniteVector, Map<IFiniteVector, Double>> coordinates) throws Throwable {
		this.source = source;
		this.target = target;
		this.linearity = coordinates;
	}

	@Override
	final public IFiniteDimensionalVectorSpace getSource() {
		return source;
	}

	@Override
	final public IFiniteDimensionalVectorSpace getTarget() {
		return target;
	}

	@Override
	final public Map<IFiniteVector, Map<IFiniteVector, Double>> getLinearity() {
		return linearity;
	}

	@Override
	public IFiniteVector solve(IFiniteVector image) throws Throwable {
		double[][] matrix = getGenericMatrix();
		double[] imageVector = image.getGenericCoordinates();
		try {
			final org.apache.commons.math3.linear.RealVector apacheVector = new LUDecomposition(
					MatrixUtils.createRealMatrix(matrix)).getSolver().solve(MatrixUtils.createRealVector(imageVector));
			return new FiniteVector(apacheVector.toArray());
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
			for (int i = 0; i<matrix.length;i++) {
				for ( int j = 0;j<matrix[i].length;j++) {
					x=matrix[i][j];
					str += " " + (x-(x%0.001)) + " ";
				}
				str += " \r";
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return str;
	}

}
