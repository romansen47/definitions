package definitions.structures.finitedimensional.real.mappings;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;

import definitions.structures.abstr.Homomorphism;
import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.field.scalar.impl.Real;
import definitions.structures.finitedimensional.real.Generator;
import definitions.structures.finitedimensional.real.functionspaces.EuclideanFunctionSpace;
import definitions.structures.finitedimensional.real.mappings.impl.MappingGenerator;
import definitions.structures.finitedimensional.real.vectors.FiniteVector;
import definitions.structures.finitedimensional.real.vectors.Function;
import definitions.structures.finitedimensional.real.vectors.impl.Tuple;
import definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;
import definitions.structures.finitedimensional.real.vectorspaces.ParameterizedSpace;

/**
 * Finite dimensional homomorphism.
 * 
 * @author ro
 *
 */
public interface FiniteDimensionalHomomorphism extends Homomorphism {

	/**
	 * Method to solve the equation y=Ax.
	 * 
	 * @param image the vector y.
	 * @return x, the source image of y with respect to the mapping.
	 */
	default FiniteVector solve(final FiniteVector image) {
		final Scalar[][] matrix = this.getGenericMatrix();
		final Scalar[] imageVector = null;//image.getCoordinates();

		final double[][] matrixAsDoubles = new double[matrix.length][matrix[0].length];
		final double[] imageVectorAsDoubles = new double[imageVector.length];

		try {
			final org.apache.commons.math3.linear.RealVector apacheVector = new LUDecomposition(
					MatrixUtils.createRealMatrix(matrixAsDoubles)).getSolver()
							.solve(MatrixUtils.createRealVector(imageVectorAsDoubles));
			double[] ans = apacheVector.toArray();
			Scalar[] ansAsScalars = new Scalar[ans.length];
			for (int i = 0; i < ans.length; i++) {
				ansAsScalars[i] = new Real(ans[i]);
			}
			return new Tuple(ansAsScalars);
		} catch (final Exception e) {
			return null;
		}
	}

	@Override
	default Vector get(final Vector vec2) {
		if (getSource() instanceof ParameterizedSpace) {
			return getOnSubSpace(vec2);
		}
		Map<Vector, Scalar> coordinates;
		if (vec2 instanceof Function) {
			coordinates = ((Function) vec2).getCoordinates((EuclideanSpace) getSource());
		} else {
			coordinates = ((FiniteVector) vec2).getCoordinates((EuclideanSpace) getSource());
		}
		Vector ans;
		EuclideanSpace target;
		final VectorSpace space = getTarget();
		if (space instanceof EuclideanFunctionSpace) {
			target = (EuclideanFunctionSpace) space;
			ans = ((EuclideanFunctionSpace) target).nullFunction();
		} else {
			target = (EuclideanSpace) getTarget();
			ans = target.nullVec();
		}
		for (final Vector src : ((EuclideanSpace) getSource()).genericBaseToList()) {
			final Map<Vector, Scalar> tmp = new ConcurrentHashMap<>();
			for (final Vector vec : target.genericBaseToList()) {
				tmp.put(vec, getLinearity().get(src).get(vec));
			}
			final double coord = coordinates.get(((EuclideanSpace) getSource()).getBaseVec(src)).getValue();
			final Vector vec = target.get(tmp);
			final Vector summand = target.stretch(vec, new Real(coord));
			ans = target.add(ans, summand);
		}
		return ans;
	}

	default FiniteVector getOnSubSpace(final Vector vec2) {
		final ParameterizedSpace space = (ParameterizedSpace) getSource();
		final Vector inverseVector = new Tuple(space.getInverseCoordinates(vec2));
		final FiniteDimensionalHomomorphism mapOnSourceSpaces = (FiniteDimensionalHomomorphism) Generator.getGenerator()
				.getMappinggenerator().getFiniteDimensionalLinearMapping(this.getGenericMatrix());
		FiniteDimensionalHomomorphism composedMapping;
		if (getTarget() instanceof ParameterizedSpace) {
			composedMapping = (FiniteDimensionalHomomorphism) MappingGenerator.getInstance()
					.getComposition(((ParameterizedSpace) getTarget()).getParametrization(), mapOnSourceSpaces);
		} else {
			composedMapping = mapOnSourceSpaces;
		}
		return (FiniteVector) composedMapping.get(inverseVector);
	}

	/**
	 * method to swap columns and rows.
	 */
	default void swap(final double[][] mat, final int row1, final int row2, final int col) {
		for (int i = 0; i < col; i++) {
			final double temp = mat[row1][i];
			mat[row1][i] = mat[row2][i];
			mat[row2][i] = temp;
		}
	}

	/**
	 * Method to get the rank.
	 * 
	 * @return the rank.
	 */
	default int getRank() {
		final double[][] mat = new double[((EuclideanSpace) getTarget()).genericBaseToList()
				.size()][((EuclideanSpace) getSource()).genericBaseToList().size()];
		int m = 0;
		for (final Vector vec1 : ((EuclideanSpace) getSource()).genericBaseToList()) {
			int n = 0;
			for (final Vector vec2 : ((EuclideanSpace) getTarget()).genericBaseToList()) {
				mat[n][m] = getGenericMatrix()[n++][m].getValue();
			}
			m++;
		}
		final int r = mat.length;
		final int c = mat[0].length;
		int rank = c;

		for (int row = 0; row < rank; row++) {
			if (mat[row][row] != 0) {
				for (int col = 0; col < r; col++) {
					if (col != row) {
						final double mult = mat[col][row] / mat[row][row];
						for (int i = 0; i < rank; i++) {
							mat[col][i] -= mult * mat[row][i];
						}
					}
				}
			} else {
				boolean reduce = true;
				for (int i = row + 1; i < r; i++) {
					if (mat[i][row] != 0) {
						swap(mat, row, i, rank);
						reduce = false;
						break;
					}
				}
				if (reduce) {
					rank--;
					for (int i = 0; i < r; i++) {
						mat[i][row] = mat[i][rank];
					}
				}
				row--;
			}
		}
		return rank;
	}

}
