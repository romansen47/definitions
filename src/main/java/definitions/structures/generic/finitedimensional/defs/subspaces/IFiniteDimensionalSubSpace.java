package definitions.structures.generic.finitedimensional.defs.subspaces;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.IVector;
import definitions.structures.abstr.IVectorSpace;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.mappings.MappingGenerator;
import definitions.structures.generic.finitedimensional.defs.spaces.IFiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.IFiniteVector;

public interface IFiniteDimensionalSubSpace extends IFiniteDimensionalVectorSpace {

	IFiniteDimensionalVectorSpace getSuperSpace();

	@Override
	boolean contains(IVector vec) throws Throwable;

	@Override
	default int dim() throws Throwable {
		return getParametrization().getRank();
	}

	IFiniteDimensionalLinearMapping getParametrization();

	@Override
	default IVector add(IVector vec1, IVector vec2) throws Throwable {
		if (vec1 instanceof IFiniteVector && vec2 instanceof IFiniteVector) {
			final Set<IFiniteVector> base = getGenericBase();
			final Map<IFiniteVector, Double> coordinates = new HashMap<>();
			for (final IFiniteVector vec : base) {
				double summand1 = ((IFiniteVector) vec1).getCoordinates().get(getBaseVec(vec));
				double summand2 = ((IFiniteVector) vec2).getCoordinates().get(getBaseVec(vec));
				double sumOnCoordinate = summand1 + summand2;
				coordinates.put(vec, sumOnCoordinate);
			}
			return get(coordinates);
		} else
			return ((IVectorSpace) this).add(vec1, vec2);
	}

	default Map<IFiniteVector, Double> getInverseCoordinates(IFiniteVector vec) throws Throwable {
		IFiniteVector ans = getNearestVector(vec);
		if (getSuperSpace().getDistance((IFiniteVector) getParametrization().get(ans), vec) < 1.e-5) {
			return ans.getCoordinates();
		} else
			return null;
	}

	Map<IFiniteVector, IFiniteVector> getParametrizationBaseVectorMapping();

	default IFiniteVector getNearestVector(IFiniteVector vec) throws Throwable {
		IFiniteDimensionalLinearMapping transposed = MappingGenerator.getInstance()
				.getTransposedMapping(getParametrization());
		IFiniteDimensionalLinearMapping quadratic = MappingGenerator.getInstance().getComposition(transposed,
				getParametrization());
		IFiniteVector transformed = (IFiniteVector) transposed.get(vec);
		final IFiniteVector ans = new FiniteVector(quadratic.solve(transformed).getCoordinates());
		return ans;
	}
}
