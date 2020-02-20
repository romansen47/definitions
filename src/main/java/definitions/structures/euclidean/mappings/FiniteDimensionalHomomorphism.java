package definitions.structures.euclidean.mappings;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;

import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.mappings.VectorSpaceHomomorphism;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.VectorSpaceMethods;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.mappings.impl.MappingGenerator;
import definitions.structures.euclidean.vectors.FiniteVector;
import definitions.structures.euclidean.vectors.impl.Tuple;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.ParameterizedSpace;

/**
 * Finite dimensional homomorphism.
 * 
 * @author ro
 *
 */
public interface FiniteDimensionalHomomorphism extends VectorSpaceHomomorphism {

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Vector get(final Element vec2) {
		if (this.getSource() instanceof ParameterizedSpace) {
			return this.getOnSubSpace((Vector) vec2);
		}
		Map<Vector, Scalar> coordinates;
		if (((FiniteVectorMethods) vec2).getCoordinates() == null) {
			coordinates = ((Function) vec2).getCoordinates((EuclideanSpace) this.getSource());
			((Function)vec2).setCoordinates(coordinates, (EuclideanSpace) this.getSource());
		} else {
			if (vec2 instanceof FiniteVector) {
				coordinates = ((FiniteVector) vec2).getCoordinates();
			} else {
				final EuclideanSpace source = (EuclideanSpace) this.getSource();
				coordinates = ((Function) vec2).getCoordinates(source);
			}
		}
		Vector ans;
		EuclideanSpace target;
		final VectorSpace space = (VectorSpace) this.getTarget();
		if (space instanceof EuclideanFunctionSpace) {
			target = (EuclideanFunctionSpace) space;
			ans = ((EuclideanFunctionSpace) target).nullFunction();
		} else {
			target = (EuclideanSpace) this.getTarget();
			ans = target.nullVec();
		}
		for (final Vector src : ((EuclideanSpace) this.getSource()).genericBaseToList()) {
			final Map<Vector, Scalar> tmp = new ConcurrentHashMap<>();
			for (final Vector vec : target.genericBaseToList()) {
				tmp.put(vec, this.getLinearity().get(src).get(vec));
			}
			final Scalar coord = coordinates.get(((EuclideanSpace) this.getSource()).getBaseVec(src));
			final Vector vec = target.get(tmp);
			final Vector summand = target.stretch(vec, coord);
			ans = target.addition(ans, summand);
		}
		return ans;
	}

	default FiniteVector getOnSubSpace(final Vector vec2) {
		final ParameterizedSpace space = (ParameterizedSpace) this.getSource();
		/*
		 * Direct usage of constructor instead of get method in order to avoid cycles.
		 * Don't touch this
		 */
		final Vector inverseVector = new Tuple(space.getInverseCoordinates(vec2));
		final FiniteDimensionalHomomorphism mapOnSourceSpaces = (FiniteDimensionalHomomorphism) MappingGenerator
				.getInstance().getFiniteDimensionalLinearMapping(this.getGenericMatrix());
		FiniteDimensionalHomomorphism composedMapping;
		if (this.getTarget() instanceof ParameterizedSpace) {
			composedMapping = (FiniteDimensionalHomomorphism) MappingGenerator.getInstance()
					.getComposition(((ParameterizedSpace) this.getTarget()).getParametrization(), mapOnSourceSpaces);
		} else {
			composedMapping = mapOnSourceSpaces;
		}
		return (FiniteVector) composedMapping.get(inverseVector);
	}

	/**
	 * Method to get the rank.
	 * 
	 * @return the rank.
	 */
	default int getRank() {
		final double[][] mat = new double[((EuclideanSpace) this.getTarget()).genericBaseToList()
				.size()][((EuclideanSpace) this.getSource()).genericBaseToList().size()];
		int m = 0;
		for (final Vector vec1 : ((EuclideanSpace) this.getSource()).genericBaseToList()) {
			int n = 0;
			for (final Vector vec2 : ((EuclideanSpace) this.getTarget()).genericBaseToList()) {
				mat[n][m] = this.getGenericMatrix()[n++][m].getDoubleValue();
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
						this.swap(mat, row, i, rank);
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

	/**
	 * Method to solve the equation y=Ax.
	 * 
	 * @param image the vector y.
	 * @return x, the source image of y with respect to the mapping.
	 */
	default FiniteVector solve(final FiniteVector image) {

		final Scalar[][] matrix = this.getGenericMatrix();
		final Scalar[] imageVector = new Scalar[image.getDim()];
		for (int i = 0; i < ((VectorSpaceMethods) this.getTarget()).getDim(); i++) {
			imageVector[i] = image.getCoordinates().get(((EuclideanSpace) this.getTarget()).genericBaseToList().get(i));
		}
		final double[][] matrixAsDoubles = new double[matrix.length][matrix[0].length];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrixAsDoubles[i][j] = matrix[i][j].getDoubleValue();
			}
		}
		final double[] imageVectorAsDoubles = new double[imageVector.length];
		for (int i = 0; i < imageVector.length; i++) {
			imageVectorAsDoubles[i] = imageVector[i].getDoubleValue();
		}
		try {
			final org.apache.commons.math3.linear.RealVector apacheVector = new LUDecomposition(
					MatrixUtils.createRealMatrix(matrixAsDoubles)).getSolver()
							.solve(MatrixUtils.createRealVector(imageVectorAsDoubles));
			final double[] ans = apacheVector.toArray();
			final Scalar[] ansAsScalars = new Scalar[ans.length];
			final Map<Vector, Scalar> ansAsCoordinates = new HashMap<>();
			for (int i = 0; i < ans.length; i++) {
				ansAsScalars[i] = ((EuclideanSpace) this.getSource()).getField().get(ans[i]);
				ansAsCoordinates.put(((EuclideanSpace) this.getTarget()).genericBaseToList().get(i),
						((EuclideanSpace) this.getSource()).getField().get(ans[i]));
			}
			return (FiniteVector) ((EuclideanSpace) this.getTarget()).get(ansAsCoordinates);
		} catch (final Exception e) {
			return null;
		}
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

}
