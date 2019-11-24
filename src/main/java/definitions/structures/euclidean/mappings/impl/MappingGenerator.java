package definitions.structures.euclidean.mappings.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.mappings.Endomorphism;
import definitions.structures.abstr.mappings.Homomorphism;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.mappings.FiniteDimensionalHomomorphism;
import definitions.structures.euclidean.mappings.IMappingGenerator;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

@Component
public class MappingGenerator implements IMappingGenerator {

	private static final long serialVersionUID = 1L;
	private static IMappingGenerator instance;

	public static IMappingGenerator getInstance() {
		if (instance == null) {
			instance = new MappingGenerator();
		}
		return instance;
	}

	public MappingGenerator() {
	}

	@Override
	public Homomorphism getFiniteDimensionalLinearMapping(final Scalar[][] genericMatrix) {
		final int dimSource = genericMatrix[0].length;
		final int dimTarget = genericMatrix.length;
		final EuclideanSpace source = SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(dimSource);
		final EuclideanSpace target = Generator.getInstance().getSpacegenerator()
				.getFiniteDimensionalVectorSpace(dimTarget);
		final Map<Vector, Map<Vector, Scalar>> coordinates = new ConcurrentHashMap<>();
		int i = 0;
		for (final Vector vec1 : source.genericBaseToList()) {
			int j = 0;
			final Map<Vector, Scalar> tmp = new ConcurrentHashMap<>();
			for (final Vector vec2 : target.genericBaseToList()) {
				tmp.put(vec2, genericMatrix[j][i]);
				j++;
			}
			coordinates.put(vec1, tmp);
			i++;
		}
		if (dimSource < dimTarget) {
			final FiniteDimensionalHomomorphism tmp = new FiniteDimensionalLinearMapping(source, target, coordinates);
			if (dimSource == tmp.getRank()) {
				return new InjectiveLinearMapping(tmp);
			}
		} else if (dimSource == dimTarget) {
			final Endomorphism ans = new LinearSelfMapping(source, coordinates);
			if (((FiniteDimensionalHomomorphism) ans).getRank() == dimSource) {
				return new InvertibleSelfMapping(source, coordinates);
			}
			return ans;
		}
		return new FiniteDimensionalLinearMapping(source, target, coordinates);
	}

	@Override
	public Homomorphism getFiniteDimensionalLinearMapping(final EuclideanSpace source, final EuclideanSpace target,
			final Map<Vector, Map<Vector, Scalar>> coordinates) {
		if (source instanceof EuclideanFunctionSpace) {
			return new InjectiveFunctionSpaceOperator((EuclideanFunctionSpace) source, (EuclideanFunctionSpace) target,
					coordinates);
		}
		final int dimSource = source.getDim();
		final int dimTarget = target.getDim();
		if (dimSource < dimTarget) {
			final FiniteDimensionalHomomorphism tmp = new FiniteDimensionalLinearMapping(source, target, coordinates);
			if (dimSource == tmp.getRank()) {
				return new InjectiveLinearMapping(tmp);
			}
		} else if (dimSource == dimTarget) {
			final Endomorphism ans = new LinearSelfMapping(source, coordinates);
			if (((FiniteDimensionalHomomorphism) ans).getRank() == dimSource) {
				return new InvertibleSelfMapping(source, coordinates);
			}
			return ans;
		}
		return new FiniteDimensionalLinearMapping(source, target, coordinates);
	}

	@Override
	public Homomorphism getFiniteDimensionalLinearMapping(EuclideanSpace source, EuclideanSpace target,
			Scalar[][] genericMatrix) {
		final Map<Vector, Map<Vector, Scalar>> map = new HashMap<>();
		int i = 0;
		for (final Vector vec1 : source.genericBaseToList()) {
			int j = 0;
			final Map<Vector, Scalar> coordinates = new HashMap<>();
			for (final Vector vec2 : target.genericBaseToList()) {
				coordinates.put(vec2, genericMatrix[j][i]);
				j++;
			}
			map.put(vec1, coordinates);
			i++;
		}
		return this.getFiniteDimensionalLinearMapping(source, target, map);
	}

	public static void setInstance(MappingGenerator mappingGenerator) {
		MappingGenerator.instance = mappingGenerator;
	}

	// @Override
	// public Homomorphism getComposition(Homomorphism map2, Homomorphism map) {
	// return (Homomorphism)
	// getComposition((IFiniteDimensionalLinearMapping)map2,(IFiniteDimensionalLinearMapping)map);
	// }

}