package definitions.structures.finitedimensional.mappings;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.Homomorphism;
import definitions.structures.abstr.InnerProductSpace;
import definitions.structures.abstr.Vector;
import definitions.structures.finitedimensional.vectorspaces.EuclideanSpace;

public interface IMappingGenerator {

	default Homomorphism getComposition(final Homomorphism a, final Homomorphism b) {
		Map<Vector, Map<Vector, Double>> linearity = new ConcurrentHashMap<>();
		for (final Vector vec : ((EuclideanSpace) b.getSource()).genericBaseToList()) {
			final Map<Vector, Double> coordinates = new ConcurrentHashMap<>();
			for (final Vector vec2 : ((EuclideanSpace) b.getSource()).genericBaseToList()) {
				coordinates.put(vec2, ((InnerProductSpace) b.getSource()).product(vec, vec2));
			}
			linearity.put(vec, coordinates);
		}
		return getFiniteDimensionalLinearMapping(((EuclideanSpace) a.getSource()), ((EuclideanSpace) b.getTarget()),
				linearity);
	}

	default double[][] composition(final double[][] matA, final double[][] matB) {
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

	Homomorphism getFiniteDimensionalLinearMapping(EuclideanSpace source, EuclideanSpace target,
			Map<Vector, Map<Vector, Double>> matrix);

	Homomorphism getFiniteDimensionalLinearMapping(double[][] genericMatrix);

	default Homomorphism getTransposedMapping(final FiniteDimensionalHomomorphism map) {
		final Map<Vector, Map<Vector, Double>> transposedMatrix = new ConcurrentHashMap<>();
		for (final Vector targetVec : ((EuclideanSpace) map.getTarget()).genericBaseToList()) {
			final Map<Vector, Double> entry = new ConcurrentHashMap<>();
			for (final Vector sourceVec : ((EuclideanSpace) map.getSource()).genericBaseToList()) {
				entry.put(sourceVec, map.getLinearity().get(sourceVec).get(targetVec));
			}
			transposedMatrix.put(targetVec, entry);
		}
		return getFiniteDimensionalLinearMapping((EuclideanSpace) map.getTarget(), (EuclideanSpace) map.getSource(),
				transposedMatrix);
	}

}
