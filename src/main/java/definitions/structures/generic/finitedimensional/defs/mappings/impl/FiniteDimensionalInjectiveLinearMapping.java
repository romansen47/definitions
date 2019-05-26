package definitions.structures.generic.finitedimensional.defs.mappings.impl;

import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalInjectiveLinearMapping;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;

public class FiniteDimensionalInjectiveLinearMapping extends FiniteDimensionalLinearMapping
		implements IFiniteDimensionalInjectiveLinearMapping {

	protected FiniteDimensionalInjectiveLinearMapping(IFiniteDimensionalLinearMapping mapping) throws Throwable {
		super(mapping.getSource(), (EuclideanSpace) mapping.getTarget(), mapping.getLinearity());
	}

}
