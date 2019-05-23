package definitions.structures.generic.finitedimensional.defs.spaces;

import java.util.HashMap;
import java.util.Map;

public class SpaceGenerator implements ISpaceGenerator {

	private static ISpaceGenerator generator = null;
	private static Map<Integer, CoordinateSpace> cachedSpaces;

	@Override
	public Map<Integer, CoordinateSpace> getCachedSpaces() {
		return cachedSpaces;
	}

	public static ISpaceGenerator getInstance() {
		if (generator == null) {
			generator = new SpaceGenerator();
			cachedSpaces = new HashMap<>();
		}
		return generator;
	}

	private SpaceGenerator() {
	}

}