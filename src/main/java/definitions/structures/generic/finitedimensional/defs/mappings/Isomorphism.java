package definitions.structures.generic.finitedimensional.defs.mappings;

import definitions.structures.generic.finitedimensional.defs.mappings.impl.InvertibleFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.mappings.impl.MappingGenerator;
import math.matrix.MatrixOperator;

public interface Isomorphism extends IFiniteDimensionalInjectiveLinearMapping {

	default Isomorphism getInverse() throws Throwable {
		return (InvertibleFiniteDimensionalLinearMapping) MappingGenerator.getInstance()
				.getFiniteDimensionalLinearMapping(MatrixOperator.getInstance().inverse(getGenericMatrix()));
	}

}
