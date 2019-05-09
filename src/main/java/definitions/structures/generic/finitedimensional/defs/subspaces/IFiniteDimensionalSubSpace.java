package definitions.structures.generic.finitedimensional.defs.subspaces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import definitions.structures.abstr.IVector;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.spaces.IFiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.IFiniteVector;

public interface IFiniteDimensionalSubSpace extends IFiniteDimensionalVectorSpace {

	IFiniteDimensionalVectorSpace getSuperSpace();

	@Override
	boolean contains(IVector vec);

	@Override
	default int dim() throws Throwable {
		return getParametrization().getSource().dim() - getParametrization().getRank();
	}

	IFiniteDimensionalLinearMapping getParametrization();

	default IVector add(IFiniteVector vec1, IFiniteVector vec2) throws Throwable {
		if (vec1.getDim() == vec2.getDim() && getSuperSpace().dim() == dim()) {
			final List<IFiniteVector> base = genericBaseToList();
			final Map<IFiniteVector, Double> coordinates = new HashMap<>();
			for (final IFiniteVector vec : base) {
				coordinates.put(vec, vec1.getCoordinates().get(vec) + vec2.getCoordinates().get(vec));
			}
			return new FiniteVector(coordinates);
		} else {
			throw new Exception();
		}
	}
}
