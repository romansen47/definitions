package definitions.structures.euclidean.mappings;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.abstr.mappings.VectorSpaceHomomorphism;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.mappings.impl.MappingGenerator;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public interface IMappingGenerator extends Serializable {

	default Scalar[][] composition(final Scalar[][] scalars, final Scalar[][] scalars2) {
		final Scalar[][] matC = new Scalar[scalars.length][scalars2[0].length];
		for (int i = 0; i < scalars.length; i++) {
			for (int j = 0; j < scalars2[0].length; j++) {
				matC[i][j] = RealLine.getInstance().getZero();
				for (int k = 0; k < scalars[0].length; k++) {
					matC[i][j] = RealLine.getInstance().get(((Real) matC[i][j]).getDoubleValue()
							+ (((Real) scalars[i][k]).getDoubleValue() * ((Real) scalars2[k][j]).getDoubleValue()));
				}
			}
		}
		return matC;
	}

	default VectorSpaceHomomorphism getComposition(final VectorSpaceHomomorphism a, final VectorSpaceHomomorphism b) {
		final Scalar[][] genericMatrix = MappingGenerator.getInstance().composition(a.getGenericMatrix(),
				b.getGenericMatrix());
		return this.getFiniteDimensionalLinearMapping(((EuclideanSpace) b.getSource()),
				((EuclideanSpace) a.getSource()), genericMatrix);
	}

	VectorSpaceHomomorphism getFiniteDimensionalLinearMapping(EuclideanSpace source, EuclideanSpace target,
			Map<Vector, Map<Vector, Scalar>> matrix);

	VectorSpaceHomomorphism getFiniteDimensionalLinearMapping(EuclideanSpace source, EuclideanSpace target,
			Scalar[][] genericMatrix);

	VectorSpaceHomomorphism getFiniteDimensionalLinearMapping(Scalar[][] genericMatrix);

	default VectorSpaceHomomorphism getTransposedMapping(final FiniteDimensionalHomomorphism map) {
		final Map<Vector, Map<Vector, Scalar>> transposedMatrix = new ConcurrentHashMap<>();
		for (final Vector targetVec : ((EuclideanSpace) map.getTarget()).genericBaseToList()) {
			final Map<Vector, Scalar> entry = new ConcurrentHashMap<>();
			for (final Vector sourceVec : ((EuclideanSpace) map.getSource()).genericBaseToList()) {
				entry.put(sourceVec, map.getLinearity().get(sourceVec).get(targetVec));
			}
			transposedMatrix.put(targetVec, entry);
		}
		return this.getFiniteDimensionalLinearMapping((EuclideanSpace) map.getTarget(),
				(EuclideanSpace) map.getSource(), transposedMatrix);
	}

}
