package definitions.structures.generic.finitedimensional.defs.mappings;

import definitions.structures.generic.finitedimensional.defs.Generator;
import definitions.structures.generic.finitedimensional.defs.mappings.impl.InvertibleFiniteDimensionalLinearMapping;
import deprecated.math.matrix.MatrixOperator;

public interface Isomorphism extends IFiniteDimensionalInjectiveLinearMapping {

	default Isomorphism getInverse() throws Throwable {
		return (InvertibleFiniteDimensionalLinearMapping) Generator.getGenerator().getMappinggenerator()
				.getFiniteDimensionalLinearMapping(MatrixOperator.getInstance().inverse(getGenericMatrix()));
	}

}
