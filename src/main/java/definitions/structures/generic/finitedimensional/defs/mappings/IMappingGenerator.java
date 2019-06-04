package definitions.structures.generic.finitedimensional.defs.mappings;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;

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

	IFiniteDimensionalLinearMapping getFiniteDimensionalLinearMapping(EuclideanSpace source, EuclideanSpace target,
			Map<Vector, Map<Vector, Double>> matrix) throws Throwable;

	IFiniteDimensionalLinearMapping getFiniteDimensionalLinearMapping(double[][] genericMatrix) throws Throwable;

	default IFiniteDimensionalLinearMapping getTransposedMapping(IFiniteDimensionalLinearMapping map) throws Throwable {
		Map<Vector, Map<Vector, Double>> transposedMatrix = new HashMap<>();
		for (Vector targetVec : ((EuclideanSpace) map.getTarget()).getGenericBase()) {
			Map<Vector, Double> entry = new HashMap<>();
			for (Vector sourceVec : map.getSource().getGenericBase()) {
				entry.put(sourceVec, map.getLinearity().get(sourceVec).get(targetVec));
			}
			transposedMatrix.put(targetVec, entry);
		}
		return getFiniteDimensionalLinearMapping((EuclideanSpace) map.getTarget(), map.getSource(), transposedMatrix);
	}

}
