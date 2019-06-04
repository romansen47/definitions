package definitions.structures.generic.finitedimensional.defs.mappings.impl;

import java.util.Map;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.mappings.Automorphism;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;

public class InvertibleFiniteDimensionalLinearMapping extends FiniteDimensionalLinearMapping implements Automorphism {

	protected InvertibleFiniteDimensionalLinearMapping(EuclideanSpace source,
			Map<Vector, Map<Vector, Double>> coordinates) throws Throwable {
		super(source, source, coordinates);
		if (getRank() < source.dim()) {
			throw new Throwable();
		}
	}

}
