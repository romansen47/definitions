package definitions.structures.generic.finitedimensional.finitedimensionalspaces.linearmappings;

import math.matrix.MatrixOperator;

public interface Isomorphism extends Endomorphism, IFiniteDimensionalInjectiveLinearMapping {

	default Isomorphism getInverse() throws Throwable {

		return (InvertibleFiniteDimensionalLinearMapping) Generator.getGenerator()
				.getFiniteDimensionalLinearMapping(MatrixOperator.getInstance().inverse(getGenericMatrix()));
	}

}
