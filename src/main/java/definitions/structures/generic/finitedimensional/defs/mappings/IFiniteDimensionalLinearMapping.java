package definitions.structures.generic.finitedimensional.defs.mappings;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.CoordinateSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.ParameterizedSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces.IFiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.Tuple;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;

public interface IFiniteDimensionalLinearMapping {

	FiniteVector solve(FiniteVector image) throws Throwable;

	CoordinateSpace getSource();

	VectorSpace getTarget();

	Map<FiniteVector, Map<FiniteVector, Double>> getLinearity();

	default Map<FiniteVector, Double> getLinearity(FiniteVector vec1) {
		return getLinearity().get(vec1);
	}

	default Vector get(FiniteVector vec1) throws Throwable {
		if (getSource() instanceof ParameterizedSpace) {
			return getOnSubSpace(vec1);
		}
		final Map<FiniteVector, Double> coordinates = vec1.getCoordinates();
		Vector ans;
		CoordinateSpace target;
		VectorSpace space = getTarget();
		if (space instanceof IFiniteDimensionalFunctionSpace) {
			target = (IFiniteDimensionalFunctionSpace) space;
			ans = ((IFiniteDimensionalFunctionSpace) target).nullFunction();
		} else {
			target = (CoordinateSpace) getTarget();
			ans = target.nullVec();
		}
		for (final FiniteVector src : getSource().genericBaseToList()) {
			Map<FiniteVector, Double> tmp = new HashMap<>();
			for (FiniteVector vec : target.genericBaseToList()) {
				tmp.put(vec, getLinearity().get(src).get(vec));
			}
			double coord = coordinates.get(getSource().getBaseVec(src));
			FiniteVector vec = (FiniteVector) target.get(tmp);
			Vector summand = target.stretch(vec, coord);
			ans = target.add(ans, summand);
		}
		return ans;
	}

	default FiniteVector getOnSubSpace(FiniteVector vec1) throws Throwable {
		FiniteVector inverseVector = new Tuple(
				((ParameterizedSpace) getSource()).getInverseCoordinates(vec1));
		IFiniteDimensionalLinearMapping mapOnSourceSpaces = MappingGenerator.getInstance()
				.getFiniteDimensionalLinearMapping(this.getGenericMatrix());
		IFiniteDimensionalLinearMapping composedMapping;
		if (getTarget() instanceof ParameterizedSpace) {
			composedMapping = MappingGenerator.getInstance()
					.getComposition(((ParameterizedSpace) getTarget()).getParametrization(), mapOnSourceSpaces);
		} else {
			composedMapping = mapOnSourceSpaces;
		}
		return (FiniteVector) composedMapping.get(inverseVector);
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
		double[][] mat = new double[((CoordinateSpace) getTarget()).genericBaseToList()
				.size()][getSource().genericBaseToList().size()];
		int m = 0;
		for (FiniteVector vec1 : getSource().genericBaseToList()) {
			int n = 0;
			for (FiniteVector vec2 : ((CoordinateSpace) getTarget()).genericBaseToList()) {
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
