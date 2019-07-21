package definitions.structures.finitedimensional.mappings.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.Endomorphism;
import definitions.structures.abstr.Homomorphism;
import definitions.structures.abstr.Vector;
import definitions.structures.finitedimensional.Generator;
import definitions.structures.finitedimensional.functionspaces.EuclideanFunctionSpace;
import definitions.structures.finitedimensional.mappings.FiniteDimensionalHomomorphism;
import definitions.structures.finitedimensional.mappings.IMappingGenerator;
import definitions.structures.finitedimensional.vectorspaces.EuclideanSpace;

public class MappingGenerator implements IMappingGenerator {

	private static IMappingGenerator generator = null;

	public static IMappingGenerator getInstance() {
		if (generator == null) {
			generator = new MappingGenerator();
		}
		return generator;
	}

	private MappingGenerator() {
	}

	@Override
	public Homomorphism getFiniteDimensionalLinearMapping(final double[][] genericMatrix) {
		final int dimSource = genericMatrix[0].length;
		final int dimTarget = genericMatrix.length;
		final EuclideanSpace source = Generator.getGenerator().getSpacegenerator()
				.getFiniteDimensionalVectorSpace(dimSource);
		final EuclideanSpace target = Generator.getGenerator().getSpacegenerator()
				.getFiniteDimensionalVectorSpace(dimTarget);
		final Map<Vector, Map<Vector, Double>> coordinates = new ConcurrentHashMap<>();
		int i = 0;
		for (final Vector vec1 : source.genericBaseToList()) {
			int j = 0;
			final Map<Vector, Double> tmp = new ConcurrentHashMap<>();
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
			final Map<Vector, Map<Vector, Double>> coordinates) {
		if (source instanceof EuclideanFunctionSpace) {
			return new InjectiveFunctionSpaceOperator((EuclideanFunctionSpace) source, (EuclideanFunctionSpace) target,
					coordinates);
		}
		final int dimSource = source.dim();
		final int dimTarget = target.dim();
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
			double[][] genericMatrix) {
		final Map<Vector, Map<Vector, Double>> map = new HashMap<>();
		int i = 0;
		for (final Vector vec1 : source.genericBaseToList()) {
			int j = 0;
			final Map<Vector, Double> coordinates = new HashMap<>();
			for (final Vector vec2 : target.genericBaseToList()) {
				coordinates.put(vec2, genericMatrix[j][i]);
				j++;
			}
			map.put(vec1, coordinates);
			i++;
		}
		return this.getFiniteDimensionalLinearMapping(source, target, map);
	}

//	@Override
//	public Homomorphism getComposition(Homomorphism map2, Homomorphism map)  {
//		return (Homomorphism) getComposition((IFiniteDimensionalLinearMapping)map2,(IFiniteDimensionalLinearMapping)map);
//	}

}