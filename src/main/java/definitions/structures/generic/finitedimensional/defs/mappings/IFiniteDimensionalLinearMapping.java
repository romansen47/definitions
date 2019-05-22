package definitions.structures.generic.finitedimensional.defs.mappings;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.IFiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.IFiniteDimensionalSubSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces.IFiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.IFiniteVector;

public interface IFiniteDimensionalLinearMapping {

	IFiniteVector solve(IFiniteVector image) throws Throwable;

	IFiniteDimensionalVectorSpace getSource();

	VectorSpace getTarget();

	Map<IFiniteVector, Map<IFiniteVector, Double>> getLinearity();

	default Map<IFiniteVector, Double> getLinearity(IFiniteVector vec1) {
		return getLinearity().get(vec1);
	}

	default Vector get(IFiniteVector vec1) throws Throwable {
		if (getSource() instanceof IFiniteDimensionalSubSpace) {
			return getOnSubSpace(vec1);
		}
		final Map<IFiniteVector, Double> coordinates = vec1.getCoordinates();
		Vector ans;
		IFiniteDimensionalVectorSpace target;
		VectorSpace space = getTarget();
		if (space instanceof IFiniteDimensionalFunctionSpace) {
			target = (IFiniteDimensionalFunctionSpace) space;
			ans = ((IFiniteDimensionalFunctionSpace) target).nullFunction();
		} else {
			target = (IFiniteDimensionalVectorSpace) getTarget();
			ans = target.nullVec();
		}
		for (final IFiniteVector src : getSource().genericBaseToList()) {
			Map<IFiniteVector, Double> tmp = new HashMap<>();
			for (IFiniteVector vec : target.genericBaseToList()) {
				tmp.put(vec, getLinearity().get(src).get(vec));
			}
			double coord = coordinates.get(getSource().getBaseVec(src));
			IFiniteVector vec = (IFiniteVector) target.get(tmp);
			Vector summand = target.stretch(vec, coord);
			ans = target.add(ans, summand);
		}
		return ans;
	}

	default IFiniteVector getOnSubSpace(IFiniteVector vec1) throws Throwable {
		IFiniteVector inverseVector = new FiniteVector(
				((IFiniteDimensionalSubSpace) getSource()).getInverseCoordinates(vec1));
		IFiniteDimensionalLinearMapping mapOnSourceSpaces = MappingGenerator.getInstance()
				.getFiniteDimensionalLinearMapping(this.getGenericMatrix());
		IFiniteDimensionalLinearMapping composedMapping;
		if (getTarget() instanceof IFiniteDimensionalSubSpace) {
			composedMapping = MappingGenerator.getInstance()
					.getComposition(((IFiniteDimensionalSubSpace) getTarget()).getParametrization(), mapOnSourceSpaces);
		} else {
			composedMapping = mapOnSourceSpaces;
		}
		return (IFiniteVector) composedMapping.get(inverseVector);
	}

//	default IFiniteVector getColumn(IFiniteVector vec) throws Throwable {
//		// if (vec.getDim() > getSource().dim()) {
//		// throw new Throwable();
//		// }
//		final Map<IFiniteVector, Double> coordinates = new HashMap<>();
//		for (final IFiniteVector vec1 : ((IFiniteDimensionalVectorSpace) getTarget()).genericBaseToList()) {
//			coordinates.put(vec1, getLinearity().get(vec).get(vec1));
//		}
//		return new FiniteVector(coordinates);
//	}

	double[][] getGenericMatrix() throws Throwable;

	default void swap(double[][] mat, int row1, int row2, int col) {
		for (int i = 0; i < col; i++) {
			double temp = mat[row1][i];
			mat[row1][i] = mat[row2][i];
			mat[row2][i] = temp;
		}
	}

	default int getRank() throws Throwable {
		double[][] mat = new double[((IFiniteDimensionalVectorSpace) getTarget()).genericBaseToList()
				.size()][getSource().genericBaseToList().size()];
		int m = 0;
		for (IFiniteVector vec1 : getSource().genericBaseToList()) {
			int n = 0;
			for (IFiniteVector vec2 : ((IFiniteDimensionalVectorSpace) getTarget()).genericBaseToList()) {
				mat[n][m] = getGenericMatrix()[n++][m];
			}
			m++;
		}
		int r = mat.length;
		int c = mat[0].length;
		int rank = c;

		for (int row = 0; row < rank; row++) {
			if (mat[row][row] != 0) {
				for (int col = 0; col < r; col++) {
					if (col != row) {
						double mult = mat[col][row] / mat[row][row];
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
