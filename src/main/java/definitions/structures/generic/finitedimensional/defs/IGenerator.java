package definitions.structures.generic.finitedimensional.defs;

import java.util.Map;

import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;

public interface IGenerator {

	Map<Integer, EuclideanSpace> getCachedSpaces();

}
