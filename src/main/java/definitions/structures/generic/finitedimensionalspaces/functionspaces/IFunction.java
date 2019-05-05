package definitions.structures.generic.finitedimensionalspaces.functionspaces;

import definitions.structures.generic.finitedimensional.finitedimensionalspaces.IFiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.finitedimensionalspaces.IFiniteVector;

public interface IFunction {

	IFiniteVector get(IFiniteVector vec) throws Throwable;

	IFiniteDimensionalVectorSpace getTarget();

	IFiniteDimensionalVectorSpace getSource();
	
}
