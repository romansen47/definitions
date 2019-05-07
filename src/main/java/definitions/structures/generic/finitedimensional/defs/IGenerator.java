package definitions.structures.generic.finitedimensional.defs;

import java.util.Map;

import definitions.structures.generic.finitedimensional.defs.spaces.IFiniteDimensionalVectorSpace;

public interface IGenerator {

	Map<Integer, IFiniteDimensionalVectorSpace> getCachedSpaces();

}
