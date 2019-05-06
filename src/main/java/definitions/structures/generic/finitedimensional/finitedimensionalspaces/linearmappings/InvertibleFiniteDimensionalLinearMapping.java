package definitions.structures.generic.finitedimensional.finitedimensionalspaces.linearmappings;

import java.util.Map;

import definitions.structures.generic.finitedimensional.finitedimensionalspaces.FiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.finitedimensionalspaces.IFiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.finitedimensionalspaces.IFiniteVector;

public class InvertibleFiniteDimensionalLinearMapping extends FiniteDimensionalLinearMapping implements Isomorphism {

	public InvertibleFiniteDimensionalLinearMapping(IFiniteDimensionalVectorSpace source,
			Map<IFiniteVector, Map<IFiniteVector, Double>> coordinates) throws Throwable {
		super(source, source, coordinates);
		if (det() == 0) {
			throw new Throwable();
		}
	}

}
