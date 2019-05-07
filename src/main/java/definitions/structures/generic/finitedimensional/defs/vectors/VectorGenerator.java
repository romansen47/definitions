package definitions.structures.generic.finitedimensional.defs.vectors;

public class VectorGenerator implements IVectorGenerator {

	private static VectorGenerator generator = null;

	public static IVectorGenerator getInstance() {
		if (generator == null) {
			generator = new VectorGenerator();
		}
		return generator;
	}

	private VectorGenerator() {
	}

}