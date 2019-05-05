package definitions.structures.generic.finitedimensional.finitedimensionalspaces.linearmappings;

import java.util.Map;

import definitions.structures.generic.finitedimensional.finitedimensionalspaces.FiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.finitedimensionalspaces.IFiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.finitedimensionalspaces.FiniteVector;

public class InvertibleFiniteDimensionalLinearMapping extends FiniteDimensionalLinearMapping implements Isomorphism {

	public InvertibleFiniteDimensionalLinearMapping(IFiniteDimensionalVectorSpace source,
			Map<FiniteVector, Map<FiniteVector, Double>> linearity) throws Throwable {
		super(source, source, linearity);
		if (det() == 0) {
			throw new Throwable();
		}
	}

}
