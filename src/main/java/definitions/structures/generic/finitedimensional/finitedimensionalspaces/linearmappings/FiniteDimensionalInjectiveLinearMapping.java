package definitions.structures.generic.finitedimensional.finitedimensionalspaces.linearmappings;

import definitions.structures.generic.finitedimensional.finitedimensionalspaces.FiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.finitedimensionalspaces.IFiniteVector;

public class FiniteDimensionalInjectiveLinearMapping extends FiniteDimensionalLinearMapping
	implements IFiniteDimensionalInjectiveLinearMapping{

	public FiniteDimensionalInjectiveLinearMapping(IFiniteDimensionalLinearMapping mapping)
			throws Throwable {
		super(mapping.getSource(),mapping.getTarget(),mapping.getLinearity());
		// TODO Auto-generated constructor stub
	}

	@Override
	public IFiniteVector solve(IFiniteVector image) throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

}
