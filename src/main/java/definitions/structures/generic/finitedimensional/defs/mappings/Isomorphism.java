package definitions.structures.generic.finitedimensional.defs.mappings;

import math.matrix.MatrixOperator;

public interface Isomorphism extends Endomorphism, IFiniteDimensionalInjectiveLinearMapping {

	default Isomorphism getInverse() throws Throwable {
		return (InvertibleFiniteDimensionalLinearMapping) MappingGenerator.getInstance()
				.getFiniteDimensionalLinearMapping(MatrixOperator.getInstance().inverse(getGenericMatrix()));
	}

}
