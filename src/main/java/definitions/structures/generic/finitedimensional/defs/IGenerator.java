package definitions.structures.generic.finitedimensional.defs;

import java.util.Map;

import definitions.structures.generic.finitedimensional.defs.spaces.CoordinateSpace;

public interface IGenerator {

	Map<Integer, CoordinateSpace> getCachedSpaces();

}
