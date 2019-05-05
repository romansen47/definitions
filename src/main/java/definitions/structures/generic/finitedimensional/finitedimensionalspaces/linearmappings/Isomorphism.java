package definitions.structures.generic.finitedimensional.finitedimensionalspaces.linearmappings;

import math.matrix.MatrixOperator;

public interface Isomorphism extends Endomorphism {

	default Isomorphism getInverse() throws Throwable {
		return (Isomorphism) Generator.getGenerator()
				.getFiniteDimensionalLinearMapping(MatrixOperator.getInstance().inverse(getGenericMatrix()));
	}

}
