package definitions.structures.generic.finitedimensional.defs.subspaces;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.mappings.impl.MappingGenerator;
import definitions.structures.generic.finitedimensional.defs.spaces.CoordinateSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.Tuple;

public interface ParameterizedSpace extends CoordinateSpace {

	CoordinateSpace getSuperSpace();

	@Override
	boolean contains(Vector vec) throws Throwable;

	@Override
	default int dim() throws Throwable {
		return getParametrization().getRank();
	}

	IFiniteDimensionalLinearMapping getParametrization();

	@Override
	default Vector add(Vector vec1, Vector vec2) throws Throwable {
		if (vec1 instanceof FiniteVector && vec2 instanceof FiniteVector) {
			final Set<FiniteVector> base = getGenericBase();
			final Map<FiniteVector, Double> coordinates = new HashMap<>();
			for (final FiniteVector vec : base) {
				double summand1 = ((FiniteVector) vec1).getCoordinates().get(getBaseVec(vec));
				double summand2 = ((FiniteVector) vec2).getCoordinates().get(getBaseVec(vec));
				double sumOnCoordinate = summand1 + summand2;
				coordinates.put(vec, sumOnCoordinate);
			}
			return get(coordinates);
		} else {
			return this.add(vec1, vec2);
		}
	}

	default Map<FiniteVector, Double> getInverseCoordinates(FiniteVector vec) throws Throwable {
		FiniteVector ans = getNearestVector(vec);
		if (getSuperSpace().getDistance((FiniteVector) getParametrization().get(ans), vec) < 1.e-5) {
			return ans.getCoordinates();
		} else {
			return null;
		}
	}

	Map<FiniteVector, FiniteVector> getParametrizationBaseVectorMapping();

	default FiniteVector getNearestVector(FiniteVector vec) throws Throwable {
		IFiniteDimensionalLinearMapping transposed = MappingGenerator.getInstance()
				.getTransposedMapping(getParametrization());
		IFiniteDimensionalLinearMapping quadratic = MappingGenerator.getInstance().getComposition(transposed,
				getParametrization());
		FiniteVector transformed = (FiniteVector) transposed.get(vec);
		return new Tuple(quadratic.solve(transformed).getCoordinates());
	}
}
