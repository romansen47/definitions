package definitions.structures.generic;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.IVec;

public interface IFiniteDimensionalLinearMapping {

	IFiniteDimensionalVectorSpace getSource();

	IFiniteDimensionalVectorSpace getTarget();

	Map<RealVec, Map<RealVec, Double>> getLinearity();

	default Map<RealVec, Double> getLinearity(RealVec vec) {
		return getLinearity().get(vec);
	};

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

	default IVec get(IVec vec1) throws Throwable {
		if (vec1 instanceof RealVec) {
			final Map<IVec, Double> coordinates = ((RealVec) vec1).getGenericCoordinates();
			IVec ans = new RealVec(new double[getTarget().dim()]);
			for (final IVec src : getSource().getGenericBase()) {
				ans = getTarget().add(ans, getTarget().stretch(getColumn(src), coordinates.get(src)));
			}
			return ans;
		} else {
			throw new Throwable();
		}
	}

	default IVec getColumn(IVec vec) throws Throwable {
		if (vec.getDim() > getSource().dim()) {
			throw new Throwable();
		}
		final Map<IVec, Double> coordinates = new HashMap<>();
		for (final IVec vec1 : getTarget().getGenericBase()) {
			coordinates.put(vec1, getLinearity().get(vec).get(vec1));
		}
		return new RealVec(coordinates);
	}

	default double[][] getGenericMatrix() {
		final double[][] matrix = new double[getTarget().dim()][getSource().dim()];
		int i = 0;
		for (final RealVec vec1 : getSource().getGenericBase()) {
			int j = 0;
			for (final IVec vec2 : getTarget().getGenericBase()) {
				matrix[j][i] = getLinearity(vec1).get(vec2);
				j++;
			}
			i++;
		}
		return matrix;
	}
}
