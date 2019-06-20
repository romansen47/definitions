package definitions.structures.generic.finitedimensional.defs.mappings;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.HilbertSpace;
import definitions.structures.abstr.Homomorphism;
import definitions.structures.abstr.LinearMapping;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.generic.finitedimensional.defs.mappings.impl.FiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;

public interface IMappingGenerator {

	default Homomorphism getComposition(final Homomorphism a, final Homomorphism b) throws Throwable {

		final Homomorphism ans = new LinearMapping(b.getSource(),a.getTarget()) {

			@Override
			public Vector get(final Vector vec2) throws Throwable {
				return b.get(a.get(vec2));
			}


			@Override
			public Map<Vector, Double> getLinearity(final Vector vec1) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Map<Vector, Map<Vector, Double>> getLinearity() throws Throwable {
				if (linearity == null) {
					linearity = new ConcurrentHashMap<>();
					for (final Vector vec : ((EuclideanSpace) b.getSource()).genericBaseToList()) {
						final Map<Vector, Double> coordinates = new ConcurrentHashMap<>();
						for (final Vector vec2 : ((EuclideanSpace) b.getSource()).genericBaseToList()) {
							coordinates.put(vec2, ((HilbertSpace) b.getSource()).product(vec, vec2));
						}
						linearity.put(vec, coordinates);
					}
				}
				return linearity;
			}

			@Override
			public VectorSpace getTarget() {
				return target;
			}

			@Override
			public VectorSpace getSource() {
				return source;
			}

		};
		return ans;
	}

	default double[][] composition(final double[][] matA, final double[][] matB) throws Throwable {
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

	Homomorphism getFiniteDimensionalLinearMapping(EuclideanSpace source, EuclideanSpace target,
			Map<Vector, Map<Vector, Double>> matrix) throws Throwable;

	Homomorphism getFiniteDimensionalLinearMapping(double[][] genericMatrix) throws Throwable;

	default Homomorphism getTransposedMapping(final IFiniteDimensionalLinearMapping map) throws Throwable {
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
