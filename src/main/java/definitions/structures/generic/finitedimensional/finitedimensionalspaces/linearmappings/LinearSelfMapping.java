package definitions.structures.generic.finitedimensional.finitedimensionalspaces.linearmappings;

import java.util.Map;

import definitions.structures.generic.finitedimensional.finitedimensionalspaces.FiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.finitedimensionalspaces.IFiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.finitedimensionalspaces.IFiniteVector;

public class LinearSelfMapping extends FiniteDimensionalLinearMapping implements Endomorphism {

	public LinearSelfMapping(IFiniteDimensionalVectorSpace source,
			Map<IFiniteVector, Map<IFiniteVector, Double>> coordinates) throws Throwable {
		super(source, source, coordinates);
	}

}
