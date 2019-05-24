package definitions.structures.generic.finitedimensional.defs.spaces.impl;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.VectorSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.CoordinateSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.ISpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.IFiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.impl.FiniteDimensionalFunctionSpace;

public class SpaceGenerator implements ISpaceGenerator {

	private static ISpaceGenerator generator = null;

	private static Map<Integer, CoordinateSpace> cachedCoordinateSpaces;
	
	private static Map<Integer, IFiniteDimensionalFunctionSpace> 
				cachedFunctionSpaces;

	@Override
	public Map<Integer, CoordinateSpace> getCachedCoordinateSpaces() {
		return cachedCoordinateSpaces;
	}
	
	@Override
	public Map<Integer, IFiniteDimensionalFunctionSpace> getCachedFunctionSpaces() {
		return cachedFunctionSpaces;
	}

	public static ISpaceGenerator getInstance() {
		if (generator == null) {
			generator = new SpaceGenerator();
			cachedCoordinateSpaces = new HashMap<>();
			cachedFunctionSpaces = new HashMap<>();
		}
		return generator;
	}

	private SpaceGenerator() {
	}


}