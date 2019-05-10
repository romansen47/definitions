package definitions.structures.generic.finitedimensional.defs.mappings;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.spaces.IFiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.SpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.vectors.IFiniteVector;

public interface IMappingGenerator {

	Map<Integer, IFiniteDimensionalVectorSpace> getCachedSpaces();

	default IFiniteDimensionalLinearMapping getComposition(IFiniteDimensionalLinearMapping A,
			IFiniteDimensionalLinearMapping B) throws Throwable {
		return getFiniteDimensionalLinearMapping(composition(A.getGenericMatrix(), B.getGenericMatrix()));
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
	
	default IFiniteDimensionalLinearMapping getFiniteDimensionalLinearMapping(
			IFiniteDimensionalVectorSpace source,
			IFiniteDimensionalVectorSpace target,
			Map<IFiniteVector,Map<IFiniteVector,Double>> map) throws Throwable {
		return new FiniteDimensionalLinearMapping(source,target,map);
	}

	default IFiniteDimensionalLinearMapping getFiniteDimensionalLinearMapping(double[][] genericMatrix)
			throws Throwable {
		final int dimSource = genericMatrix[0].length;
		final int dimTarget = genericMatrix.length;
		final IFiniteDimensionalVectorSpace source = SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(dimSource);
		final IFiniteDimensionalVectorSpace target = SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(dimTarget);
		final Map<IFiniteVector, Map<IFiniteVector, Double>> coordinates = new HashMap<>();
		int i = 0;
		for (final IFiniteVector vec1 : source.genericBaseToList()) {
			int j = 0;
			final Map<IFiniteVector, Double> tmp = new HashMap<>();
			for (final IFiniteVector vec2 : target.genericBaseToList()) {
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
		} 
		else if (dimSource==dimTarget){
			final Endomorphism ans = new LinearSelfMapping(source, coordinates);
			if (ans.getRank()==dimSource) {
				return new InvertibleFiniteDimensionalLinearMapping(source, coordinates);
			}
			return ans;
		}
		return new FiniteDimensionalLinearMapping(source,target,coordinates);
	}
	
}
