package definitions.structures.generic.finitedimensional.finitedimensionalspaces.linearmappings;

import math.matrix.MatrixOperator;

public interface Endomorphism extends IFiniteDimensionalLinearMapping {

	default double det() throws Throwable {
		return MatrixOperator.getInstance().det(getGenericMatrix());
	}

}
