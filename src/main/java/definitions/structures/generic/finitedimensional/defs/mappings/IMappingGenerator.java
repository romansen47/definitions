package definitions.structures.generic.finitedimensional.defs.mappings;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.generic.finitedimensional.defs.spaces.CoordinateSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.SpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces.IFiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;

public interface IMappingGenerator {

	default IFiniteDimensionalLinearMapping getComposition(IFiniteDimensionalLinearMapping a,
			IFiniteDimensionalLinearMapping b) throws Throwable {
		return getFiniteDimensionalLinearMapping(composition(a.getGenericMatrix(), b.getGenericMatrix()));
	}

	default double[][] composition(double[][] matA, double[][] matB) throws Throwable {
		if (matA[0].length != matB.length) {
			throw new Throwable();
		}
		final double[][] matC = new double[matA.length][matB[0].length];
		for (int i = 0; i < matA.length; i++) {
			for (int j = 0; j < matB[0].length; j++) {
				for (int k = 0; k < matA[0].length; k++) {
					matC[i][j] += matA[i][k] * matB[k][j];
				}
			}
		}
		return matC;
	}

	default IFiniteDimensionalLinearMapping getFiniteDimensionalLinearMapping(CoordinateSpace source,
			CoordinateSpace target, Map<FiniteVector, Map<FiniteVector, Double>> coordinates)
			throws Throwable {
		if (source instanceof IFiniteDimensionalFunctionSpace) {
			return new FunctionSpaceOperator((IFiniteDimensionalFunctionSpace)source, 
											 (IFiniteDimensionalFunctionSpace)target, 
											 coordinates);
		}
		final int dimSource = source.dim();
		final int dimTarget = target.dim();
		if (dimSource < dimTarget) {
			IFiniteDimensionalLinearMapping tmp = new FiniteDimensionalLinearMapping(source, target, coordinates);
			if (dimSource == tmp.getRank()) {
				return new FiniteDimensionalInjectiveLinearMapping(tmp);
			}
		} else if (dimSource == dimTarget) {
			final Endomorphism ans = new LinearSelfMapping(source, coordinates);
			if (ans.getRank() == dimSource) {
				return new InvertibleFiniteDimensionalLinearMapping(source, coordinates);
			}
			return ans;
		}
		return new FiniteDimensionalLinearMapping(source, target, coordinates);
	}

	default IFiniteDimensionalLinearMapping getFiniteDimensionalLinearMapping(double[][] genericMatrix)
			throws Throwable {
		final int dimSource = genericMatrix[0].length;
		final int dimTarget = genericMatrix.length;
		final CoordinateSpace source = SpaceGenerator.getInstance()
				.getFiniteDimensionalVectorSpace(dimSource);
		final CoordinateSpace target = SpaceGenerator.getInstance()
				.getFiniteDimensionalVectorSpace(dimTarget);
		final Map<FiniteVector, Map<FiniteVector, Double>> coordinates = new HashMap<>();
		int i = 0;
		for (final FiniteVector vec1 : source.genericBaseToList()) {
			int j = 0;
			final Map<FiniteVector, Double> tmp = new HashMap<>();
			for (final FiniteVector vec2 : target.genericBaseToList()) {
				tmp.put(vec2, genericMatrix[j][i]);
				j++;
			}
			coordinates.put(vec1, tmp);
			i++;
		}
		if (dimSource < dimTarget) {
			IFiniteDimensionalLinearMapping tmp = new FiniteDimensionalLinearMapping(source, target, coordinates);
			if (dimSource == tmp.getRank()) {
				return new FiniteDimensionalInjectiveLinearMapping(tmp);
			}
		} else if (dimSource == dimTarget) {
			final Endomorphism ans = new LinearSelfMapping(source, coordinates);
			if (ans.getRank() == dimSource) {
				return new InvertibleFiniteDimensionalLinearMapping(source, coordinates);
			}
			return ans;
		}
		return new FiniteDimensionalLinearMapping(source, target, coordinates);
	}

	default IFiniteDimensionalLinearMapping getTransposedMapping(IFiniteDimensionalLinearMapping map) throws Throwable {
		Map<FiniteVector, Map<FiniteVector, Double>> transposedMatrix = new HashMap<>();
		for (FiniteVector targetVec : ((CoordinateSpace) map.getTarget()).getGenericBase()) {
			Map<FiniteVector, Double> entry = new HashMap<>();
			for (FiniteVector sourceVec : map.getSource().getGenericBase()) {
				entry.put(sourceVec, map.getLinearity().get(sourceVec).get(targetVec));
			}
			transposedMatrix.put(targetVec, entry);
		}
		return getFiniteDimensionalLinearMapping((CoordinateSpace) map.getTarget(), map.getSource(),
				transposedMatrix);
	}

}
