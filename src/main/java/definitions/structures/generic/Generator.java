package definitions.structures.generic;

import java.util.HashMap;
import java.util.Map;

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