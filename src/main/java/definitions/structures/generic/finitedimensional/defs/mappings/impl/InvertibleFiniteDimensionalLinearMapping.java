package definitions.structures.generic.finitedimensional.defs.mappings.impl;

import java.util.Map;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.mappings.Automorphism;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;

public class InvertibleFiniteDimensionalLinearMapping extends FiniteDimensionalLinearMapping implements Automorphism {

	protected InvertibleFiniteDimensionalLinearMapping(final EuclideanSpace source,
			final Map<Vector, Map<Vector, Double>> coordinates) {
		super(source, source, coordinates);
//		if (this.getRank() < source.dim()) {
//			throw new Throwable();
//		}
	}

}
