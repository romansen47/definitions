package definitions.structures.generic.finitedimensional.defs;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.generic.finitedimensional.defs.mappings.IMappingGenerator;
import definitions.structures.generic.finitedimensional.defs.mappings.impl.MappingGenerator;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.ISpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.SpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.vectors.IVectorGenerator;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.VectorGenerator;

public class Generator implements IGenerator {

	@SuppressWarnings("unused")
	private static final IVectorGenerator vectorGenerator = VectorGenerator.getInstance();

	@SuppressWarnings("unused")
	private static final IMappingGenerator mappingGenerator = MappingGenerator.getInstance();

	@SuppressWarnings("unused")
	private static final ISpaceGenerator spaceGenerator = SpaceGenerator.getInstance();

	private static Generator generator = null;

	private static Map<Integer, EuclideanSpace> cachedSpaces;

	@Override
	public Map<Integer, EuclideanSpace> getCachedSpaces() {
		return cachedSpaces;
	}

	public static IGenerator getGenerator() {
		if (generator == null) {
			generator = new Generator();
			cachedSpaces = new HashMap<>();
		}
		return generator;
	}

	private Generator() {
	}

}