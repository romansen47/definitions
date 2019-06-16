package definitions.structures.generic.finitedimensional.defs;

import java.util.Map;

import definitions.structures.generic.finitedimensional.defs.mappings.IMappingGenerator;
import definitions.structures.generic.finitedimensional.defs.mappings.impl.MappingGenerator;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.ISpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.SpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.vectors.IVectorGenerator;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.VectorGenerator;

@SuppressWarnings("unused")
public class Generator implements IGenerator {

	private final IVectorGenerator vectorGenerator = VectorGenerator.getInstance();
	private final IMappingGenerator mappingGenerator = MappingGenerator.getInstance();
	private final ISpaceGenerator spaceGenerator = SpaceGenerator.getInstance();

	private static Generator generator = null;

	@Override
	public Map<Integer, EuclideanSpace> getCachedSpaces() {
		return getSpacegenerator().getCachedCoordinateSpaces();
	}

	public static IGenerator getGenerator() {
		if (generator == null) {
			generator = new Generator();
		}
		return generator;
	}

	private Generator() {
	}

	@Override
	public IVectorGenerator getVectorgenerator() {
		return generator.vectorGenerator;
	}

	@Override
	public IMappingGenerator getMappinggenerator() {
		return generator.mappingGenerator;
	}

	@Override
	public ISpaceGenerator getSpacegenerator() {
		return generator.spaceGenerator;
	}

}