package definitions.structures.euclidean.mappings;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.fields.scalars.impl.Real;
import definitions.structures.abstr.mappings.Homomorphism;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.mappings.impl.MappingGenerator;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public interface IMappingGenerator {

	default Homomorphism getComposition(final Homomorphism a, final Homomorphism b) {
		final Map<Vector, Map<Vector, Scalar>> linearity = new ConcurrentHashMap<>();
		final double tmp;
//		for (final Vector vec : ((EuclideanSpace) b.getSource()).genericBaseToList()) {
//			linearity.put(vec,((Function)a.get(b.get(vec))).getCoordinates((EuclideanSpace) a.getTarget()));
//		}
//		return getFiniteDimensionalLinearMapping(((EuclideanSpace) a.getSource()), ((EuclideanSpace) b.getTarget()),
//				linearity);
		final Scalar[][] genericMatrix = MappingGenerator.getInstance().composition(a.getGenericMatrix(),
				b.getGenericMatrix());
		return getFiniteDimensionalLinearMapping(((EuclideanSpace) b.getSource()), ((EuclideanSpace) a.getSource()),
				genericMatrix);
	}

	default Scalar[][] composition(final Scalar[][] scalars, final Scalar[][] scalars2) {
		final Scalar[][] matC = new Scalar[scalars.length][scalars2[0].length];
		for (int i = 0; i < scalars.length; i++) {
			for (int j = 0; j < scalars2[0].length; j++) {
				matC[i][j] = RealLine.getInstance().getZero();
				for (int k = 0; k < scalars[0].length; k++) {
					matC[i][j] = new Real(matC[i][j].getValue() + scalars[i][k].getValue() * scalars2[k][j].getValue());
				}
			}
		}
		return matC;
	}

	Homomorphism getFiniteDimensionalLinearMapping(EuclideanSpace source, EuclideanSpace target,
			Map<Vector, Map<Vector, Scalar>> matrix);

	Homomorphism getFiniteDimensionalLinearMapping(EuclideanSpace source, EuclideanSpace target,
			Scalar[][] genericMatrix);

	Homomorphism getFiniteDimensionalLinearMapping(Scalar[][] genericMatrix);

	default Homomorphism getTransposedMapping(final FiniteDimensionalHomomorphism map) {
		final Map<Vector, Map<Vector, Scalar>> transposedMatrix = new ConcurrentHashMap<>();
		for (final Vector targetVec : ((EuclideanSpace) map.getTarget()).genericBaseToList()) {
			final Map<Vector, Scalar> entry = new ConcurrentHashMap<>();
			for (final Vector sourceVec : ((EuclideanSpace) map.getSource()).genericBaseToList()) {
				entry.put(sourceVec, map.getLinearity().get(sourceVec).get(targetVec));
			}
			transposedMatrix.put(targetVec, entry);
		}
		return getFiniteDimensionalLinearMapping((EuclideanSpace) map.getTarget(), (EuclideanSpace) map.getSource(),
				transposedMatrix);
	}

}
