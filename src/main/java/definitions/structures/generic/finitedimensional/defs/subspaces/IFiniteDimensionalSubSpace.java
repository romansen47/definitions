package definitions.structures.generic.finitedimensional.defs.subspaces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.IVector;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.mappings.MappingGenerator;
import definitions.structures.generic.finitedimensional.defs.spaces.IFiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.IFiniteVector;

public interface IFiniteDimensionalSubSpace extends IFiniteDimensionalVectorSpace {

	IFiniteDimensionalVectorSpace getSuperSpace();

	@Override
	boolean contains(IVector vec);

	@Override
	default int dim() throws Throwable {
		return getParametrization().getRank();
	}

	IFiniteDimensionalLinearMapping getParametrization();

	default IFiniteVector add(IFiniteVector vec1, IFiniteVector vec2) throws Throwable {
			final Set<IFiniteVector> base = getGenericBase();
			final Map<IFiniteVector, Double> coordinates = new HashMap<>();
			for (final IFiniteVector vec : base) {
				coordinates.put(vec, vec1.getCoordinates().get(getBaseVec(vec)) + 
						vec2.getCoordinates().get(getBaseVec(vec)));
			}
			return new FiniteVector(coordinates);
	}

	default Map<IFiniteVector, Double> getInverseCoordinates(IFiniteVector vec) throws Throwable {

		IFiniteVector ans = getNearestVector(vec);
		if (getSuperSpace().getDistance(getParametrization().get(ans),vec)<1.e-5) {
			return ans.getCoordinates();
		}
		else return null;
	}

	Map<IFiniteVector, IFiniteVector> getParametrizationBaseVectorMapping();
	
	default IFiniteVector getNearestVector(IFiniteVector vec) throws Throwable {
		IFiniteDimensionalLinearMapping transposed = MappingGenerator.getInstance()
				.getTransposedMapping(getParametrization());
		IFiniteDimensionalLinearMapping quadratic = MappingGenerator.getInstance().getComposition(transposed,
				getParametrization());
		IFiniteVector transformed = transposed.get(vec);
		final IFiniteVector ans=new FiniteVector(quadratic.solve(transformed).getCoordinates());
		return ans;
	}
}
