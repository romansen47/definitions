package definitions.structures.generic.finitedimensional.defs.mappings;

import definitions.structures.generic.finitedimensional.defs.spaces.CoordinateSpace;

public class FiniteDimensionalInjectiveLinearMapping extends FiniteDimensionalLinearMapping
		implements IFiniteDimensionalInjectiveLinearMapping {

	protected FiniteDimensionalInjectiveLinearMapping(IFiniteDimensionalLinearMapping mapping) throws Throwable {
		super(mapping.getSource(), (CoordinateSpace) mapping.getTarget(), mapping.getLinearity());
	}

}
