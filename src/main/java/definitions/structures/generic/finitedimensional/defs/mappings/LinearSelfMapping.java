package definitions.structures.generic.finitedimensional.defs.mappings;

import java.util.Map;

import definitions.structures.generic.finitedimensional.defs.spaces.IFiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.IFiniteVector;

public class LinearSelfMapping extends FiniteDimensionalLinearMapping implements Endomorphism {

	protected LinearSelfMapping(IFiniteDimensionalVectorSpace source,
			Map<IFiniteVector, Map<IFiniteVector, Double>> coordinates) throws Throwable {
		super(source, source, coordinates);
	}

}
