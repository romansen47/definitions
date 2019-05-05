package definitions.structures.generic.finitedimensional.finitedimensionalspaces.linearmappings;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.IVector;
import definitions.structures.generic.finitedimensional.finitedimensionalspaces.IFiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.finitedimensionalspaces.FiniteVector;

public interface IFiniteDimensionalLinearMapping {

	IFiniteDimensionalVectorSpace getSource();

	IFiniteDimensionalVectorSpace getTarget();

	Map<FiniteVector, Map<FiniteVector, Double>> getLinearity();

	default Map<FiniteVector, Double> getLinearity(FiniteVector vec) {
		return getLinearity().get(vec);
	};
default IVector get(IVector vec1) throws Throwable {
		if (vec1 instanceof FiniteVector) {
			final Map<IVector, Double> coordinates = ((FiniteVector) vec1).getGenericCoordinates();
			IVector ans = new FiniteVector(new double[getTarget().dim()]);
			for (final IVector src : getSource().getGenericBase()) {
				ans = getTarget().add(ans, getTarget().stretch(getColumn(src), coordinates.get(src)));
			}
			return ans;
		} else {
			throw new Throwable();
		}
	}

	default IVector getColumn(IVector vec) throws Throwable {
		if (vec.getDim() > getSource().dim()) {
			throw new Throwable();
		}
		final Map<IVector, Double> coordinates = new HashMap<>();
		for (final IVector vec1 : getTarget().getGenericBase()) {
			coordinates.put(vec1, getLinearity().get(vec).get(vec1));
		}
		return new FiniteVector(coordinates);
	}

	default double[][] getGenericMatrix() {
		final double[][] matrix = new double[getTarget().dim()][getSource().dim()];
		int i = 0;
		for (final FiniteVector vec1 : getSource().getGenericBase()) {
			int j = 0;
			for (final IVector vec2 : getTarget().getGenericBase()) {
				matrix[j][i] = getLinearity(vec1).get(vec2);
				j++;
			}
			i++;
		}
		return matrix;
	}
	

	default void swap(double mat[][], int row1, int row2, int col) {
		for (int i = 0; i < col; i++) {
			double temp = mat[row1][i];
			mat[row1][i] = mat[row2][i];
			mat[row2][i] = temp;
		}
	}

	default int getRank() {
		double mat[][] = getGenericMatrix();
		int R = mat.length;
		int C = mat[0].length;
		int rank = C;

		for (int row = 0; row < rank; row++) {
			if (mat[row][row] != 0) {
				for (int col = 0; col < R; col++) {
					if (col != row) {
						double mult = mat[col][row] / mat[row][row];
						for (int i = 0; i < rank; i++){
							mat[col][i] -= mult * mat[row][i];
						}
					}
				}
			} else {
				boolean reduce = true;
				for (int i = row + 1; i < R; i++) {
					if (mat[i][row] != 0) {
						swap(mat, row, i, rank);
						reduce = false;
						break;
					}
				}
				if (reduce) {
					rank--;
					for (int i = 0; i < R; i++)
						mat[i][row] = mat[i][rank];
				}
				row--;
			}
		}
		return rank;
	}

}
