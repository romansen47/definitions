package definitions.structures.generic.finitedimensional.defs.mappings;

import java.util.Map;

import definitions.structures.generic.finitedimensional.defs.spaces.IFiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.IFiniteVector;

public class InvertibleFiniteDimensionalLinearMapping extends FiniteDimensionalLinearMapping implements Automorphism {

	protected InvertibleFiniteDimensionalLinearMapping(IFiniteDimensionalVectorSpace source,
			Map<IFiniteVector, Map<IFiniteVector, Double>> coordinates) throws Throwable {
		super(source, source, coordinates);
		if (getRank() < source.dim()) {
			throw new Throwable();
		}
	}

}
