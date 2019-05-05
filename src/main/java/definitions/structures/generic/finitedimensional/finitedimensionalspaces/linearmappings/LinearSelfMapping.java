package definitions.structures.generic.finitedimensional.finitedimensionalspaces.linearmappings;

import java.util.Map;

import definitions.structures.generic.finitedimensional.finitedimensionalspaces.FiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.finitedimensionalspaces.IFiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.finitedimensionalspaces.FiniteVector;

public class LinearSelfMapping extends FiniteDimensionalLinearMapping implements Endomorphism {

	public LinearSelfMapping(IFiniteDimensionalVectorSpace source, Map<FiniteVector, Map<FiniteVector, Double>> linearity)
			throws Throwable {
		super(source, source, linearity);
	}

}
