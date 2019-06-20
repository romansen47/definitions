package definitions.structures.generic.finitedimensional.defs.mappings.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.Homomorphism;
import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.Generator;
import definitions.structures.generic.finitedimensional.defs.mappings.Endomorphism;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.mappings.IMappingGenerator;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.IFiniteDimensionalFunctionSpace;

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
	public Homomorphism getFiniteDimensionalLinearMapping(final double[][] genericMatrix) throws Throwable {
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
			final IFiniteDimensionalLinearMapping tmp = new FiniteDimensionalLinearMapping(source, target, coordinates);
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

	@Override
	public Homomorphism getFiniteDimensionalLinearMapping(final EuclideanSpace source, final EuclideanSpace target,
			final Map<Vector, Map<Vector, Double>> coordinates) throws Throwable {
		if (source instanceof IFiniteDimensionalFunctionSpace) {
			return new InjectiveFunctionSpaceOperator((IFiniteDimensionalFunctionSpace) source,
					(IFiniteDimensionalFunctionSpace) target, coordinates);
		}
		final int dimSource = source.dim();
		final int dimTarget = target.dim();
		if (dimSource < dimTarget) {
			final IFiniteDimensionalLinearMapping tmp = new FiniteDimensionalLinearMapping(source, target, coordinates);
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

//	@Override
//	public Homomorphism getComposition(Homomorphism map2, Homomorphism map) throws Throwable {
//		return (Homomorphism) getComposition((IFiniteDimensionalLinearMapping)map2,(IFiniteDimensionalLinearMapping)map);
//	}

}