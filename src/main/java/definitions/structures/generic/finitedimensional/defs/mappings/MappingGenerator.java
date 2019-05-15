package definitions.structures.generic.finitedimensional.defs.mappings;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.generic.finitedimensional.defs.spaces.IFiniteDimensionalVectorSpace;

public class MappingGenerator implements IMappingGenerator {

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

}