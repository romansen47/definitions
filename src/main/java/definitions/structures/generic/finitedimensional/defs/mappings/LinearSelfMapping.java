package definitions.structures.generic.finitedimensional.defs.mappings;

import java.util.Map;

import definitions.structures.generic.finitedimensional.defs.spaces.CoordinateSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;

public class LinearSelfMapping extends FiniteDimensionalLinearMapping implements Endomorphism {

	protected LinearSelfMapping(CoordinateSpace source,
			Map<FiniteVector, Map<FiniteVector, Double>> coordinates) throws Throwable {
		super(source, source, coordinates);
	}

}
