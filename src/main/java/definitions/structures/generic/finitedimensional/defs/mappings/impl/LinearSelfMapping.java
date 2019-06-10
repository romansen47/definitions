package definitions.structures.generic.finitedimensional.defs.mappings.impl;

import java.util.Map;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.mappings.Endomorphism;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;

public class LinearSelfMapping extends FiniteDimensionalLinearMapping implements Endomorphism {

	protected LinearSelfMapping(EuclideanSpace source, Map<Vector, Map<Vector, Double>> coordinates) throws Throwable {
		super(source, source, coordinates);
	}

}
