package definitions.structures.generic.finitedimensional.defs.mappings.impl;

import definitions.structures.generic.finitedimensional.defs.mappings.FiniteDimensionalEmbedding;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;

public class FiniteDimensionalInjectiveLinearMapping extends FiniteDimensionalLinearMapping
		implements FiniteDimensionalEmbedding {

	protected FiniteDimensionalInjectiveLinearMapping(final IFiniteDimensionalLinearMapping mapping) {
		super((EuclideanSpace) mapping.getSource(), (EuclideanSpace) mapping.getTarget(), mapping.getLinearity());
	}

}
