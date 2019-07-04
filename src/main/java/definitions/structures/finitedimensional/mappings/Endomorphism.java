package definitions.structures.finitedimensional.mappings;

import deprecated.math.matrix.MatrixOperator;

/**
 * Finite dimensional linear self mapping.
 * 
 * @author ro
 */
public interface Endomorphism extends FiniteDimensionalHomomorphism {

	/**
	 * Method to compute the determinant of the linear self mapping.
	 * 
	 * @return the determinant
	 */
	default double det() {
		return MatrixOperator.getInstance().det(getGenericMatrix());
	}

}
