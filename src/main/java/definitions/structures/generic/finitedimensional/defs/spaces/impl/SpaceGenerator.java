package definitions.structures.generic.finitedimensional.defs.spaces.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.ISpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.IFiniteDimensionalFunctionSpace;

public class SpaceGenerator implements ISpaceGenerator {

	private static ISpaceGenerator generator = null;

	private static Map<Integer, EuclideanSpace> cachedCoordinateSpaces;
	
	private static Map<Integer, IFiniteDimensionalFunctionSpace> 
				cachedFunctionSpaces;

	@Override
	public Map<Integer, EuclideanSpace> getCachedCoordinateSpaces() {
		return cachedCoordinateSpaces;
	}
	
	@Override
	public Map<Integer, IFiniteDimensionalFunctionSpace> getCachedFunctionSpaces() {
		return cachedFunctionSpaces;
	}

	public static ISpaceGenerator getInstance() {
		if (generator == null) {
			generator = new SpaceGenerator();
		}
		return generator;
	}

	private SpaceGenerator() {
		cachedCoordinateSpaces = new ConcurrentHashMap<>();
		cachedFunctionSpaces = new ConcurrentHashMap<>();
	}

	@Override
	public String toString(){
		String ans="";
		for (int i:cachedCoordinateSpaces.keySet()) {
			ans+=cachedCoordinateSpaces.get(i).toString()+"\r";
		}
		return ans;
	}

}