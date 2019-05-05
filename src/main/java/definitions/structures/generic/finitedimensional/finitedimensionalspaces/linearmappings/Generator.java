package definitions.structures.generic.finitedimensional.finitedimensionalspaces.linearmappings;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.generic.finitedimensional.finitedimensionalspaces.IFiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.finitedimensionalspaces.IGenerator;

public class Generator implements IGenerator {

	private static Generator generator = null;
	private static Map<Integer, IFiniteDimensionalVectorSpace> cachedSpaces;

	@Override
	public Map<Integer, IFiniteDimensionalVectorSpace> getCachedSpaces() {
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