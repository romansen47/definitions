package definitions.structures.generic.finitedimensional.defs.mappings;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;

import definitions.structures.abstr.Homomorphism;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.generic.finitedimensional.defs.Generator;
import definitions.structures.generic.finitedimensional.defs.mappings.impl.MappingGenerator;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.ParameterizedSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.IFiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.Tuple;

public interface IFiniteDimensionalLinearMapping extends Homomorphism {

	default FiniteVector solve(final Vector image) throws Throwable {
		final double[][] matrix = this.getGenericMatrix();
		final double[] imageVector = image.getGenericCoordinates();
		try {
			final org.apache.commons.math3.linear.RealVector apacheVector = new LUDecomposition(
					MatrixUtils.createRealMatrix(matrix)).getSolver().solve(MatrixUtils.createRealVector(imageVector));
			return new Tuple(apacheVector.toArray());
		} catch (final Exception e) {
			return null;
		}
	}

	@Override
	default Map<Vector, Double> getLinearity(final Vector vec1) throws Throwable {
		return getLinearity().get(vec1);
	}

	@Override
	default Vector get(final Vector vec2) throws Throwable {
		if (getSource() instanceof ParameterizedSpace) {
			return getOnSubSpace(vec2);
		}
		final Map<Vector, Double> coordinates = ((FiniteVector) vec2).getCoordinates((EuclideanSpace) getSource());
		Vector ans;
		EuclideanSpace target;
		final VectorSpace space = getTarget();
		if (space instanceof IFiniteDimensionalFunctionSpace) {
			target = (IFiniteDimensionalFunctionSpace) space;
			ans = ((IFiniteDimensionalFunctionSpace) target).nullFunction();
		} else {
			target = (EuclideanSpace) getTarget();
			ans = target.nullVec();
		}
		for (final Vector src : ((EuclideanSpace) getSource()).genericBaseToList()) {
			final Map<Vector, Double> tmp = new ConcurrentHashMap<>();
			for (final Vector vec : target.genericBaseToList()) {
				tmp.put(vec, getLinearity().get(src).get(vec));
			}
			final double coord = coordinates.get(((EuclideanSpace) getSource()).getBaseVec(src));
			final Vector vec = target.get(tmp);
			final Vector summand = target.stretch(vec, coord);
			ans = target.add(ans, summand);
		}
		return ans;
	}

	default FiniteVector getOnSubSpace(final Vector vec2) throws Throwable {
		final ParameterizedSpace space = (ParameterizedSpace) getSource();
		final Vector inverseVector = new Tuple(space.getInverseCoordinates(vec2));
		final IFiniteDimensionalLinearMapping mapOnSourceSpaces = (IFiniteDimensionalLinearMapping) Generator
				.getGenerator().getMappinggenerator().getFiniteDimensionalLinearMapping(this.getGenericMatrix());
		IFiniteDimensionalLinearMapping composedMapping;
		if (getTarget() instanceof ParameterizedSpace) {
			composedMapping = (IFiniteDimensionalLinearMapping) MappingGenerator.getInstance()
					.getComposition(((ParameterizedSpace) getTarget()).getParametrization(), mapOnSourceSpaces);
		} else {
			composedMapping = mapOnSourceSpaces;
		}
		return (FiniteVector) composedMapping.get(inverseVector);
	}

	double[][] getGenericMatrix() throws Throwable;

	default void swap(final double[][] mat, final int row1, final int row2, final int col) {
		for (int i = 0; i < col; i++) {
			final double temp = mat[row1][i];
			mat[row1][i] = mat[row2][i];
			mat[row2][i] = temp;
		}
	}

	default int getRank() throws Throwable {
		final double[][] mat = new double[((EuclideanSpace) getTarget()).genericBaseToList()
				.size()][((EuclideanSpace) getSource()).genericBaseToList().size()];
		int m = 0;
		for (final Vector vec1 : ((EuclideanSpace) getSource()).genericBaseToList()) {
			int n = 0;
			for (final Vector vec2 : ((EuclideanSpace) getTarget()).genericBaseToList()) {
				mat[n][m] = getGenericMatrix()[n++][m];
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
