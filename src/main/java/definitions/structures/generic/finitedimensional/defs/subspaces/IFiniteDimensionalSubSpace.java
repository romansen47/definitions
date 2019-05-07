package definitions.structures.generic.finitedimensional.defs.subspaces;

import definitions.structures.abstr.IVector;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.spaces.IFiniteDimensionalVectorSpace;

public interface IFiniteDimensionalSubSpace extends IFiniteDimensionalVectorSpace {

	IFiniteDimensionalVectorSpace getSuperSpace();

	@Override
	boolean contains(IVector vec);

	@Override
	default int dim() throws Throwable {
		return getParametrization().getSource().dim() - getParametrization().getRank();
	}

	IFiniteDimensionalLinearMapping getParametrization();
}
