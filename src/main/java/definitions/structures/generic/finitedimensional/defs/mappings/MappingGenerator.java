package definitions.structures.generic.finitedimensional.defs.mappings;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.generic.finitedimensional.defs.mappings.Endomorphism;
import definitions.structures.generic.finitedimensional.defs.mappings.FiniteDimensionalInjectiveLinearMapping;
import definitions.structures.generic.finitedimensional.defs.mappings.FiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.mappings.InvertibleFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.mappings.LinearSelfMapping;
import definitions.structures.generic.finitedimensional.defs.spaces.IFiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.SpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.vectors.IFiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.IVectorGenerator;
import definitions.structures.generic.finitedimensional.defs.vectors.VectorGenerator;

public class MappingGenerator implements IMappingGenerator {

	private static final IVectorGenerator vectorGenerator = VectorGenerator.getInstance();
	private static IMappingGenerator generator = null;
	private static Map<Integer, IFiniteDimensionalVectorSpace> cachedSpaces;

	@Override
	public Map<Integer, IFiniteDimensionalVectorSpace> getCachedSpaces() {
		return cachedSpaces;
	}

	public static IMappingGenerator getInstance() {
		if (generator == null) {
			generator = new MappingGenerator();
			cachedSpaces = new HashMap<>();
		}
		return generator;
	}

	private MappingGenerator() {
	}

	@Override
	public IFiniteDimensionalLinearMapping getFiniteDimensionalLinearMapping(double[][] genericMatrix)
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