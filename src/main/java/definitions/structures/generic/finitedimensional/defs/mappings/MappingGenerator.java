package definitions.structures.generic.finitedimensional.defs.mappings;

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

}