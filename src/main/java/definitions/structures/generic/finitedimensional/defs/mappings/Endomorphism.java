package definitions.structures.generic.finitedimensional.defs.mappings;

import deprecated.math.matrix.MatrixOperator;

public interface Endomorphism extends IFiniteDimensionalLinearMapping {

	default double det() throws Throwable {
		return MatrixOperator.getInstance().det(getGenericMatrix());
	}

}
