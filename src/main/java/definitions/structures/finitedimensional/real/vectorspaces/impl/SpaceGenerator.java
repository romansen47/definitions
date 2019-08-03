package definitions.structures.finitedimensional.real.vectorspaces.impl;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.finitedimensional.real.functionspaces.EuclideanFunctionSpace;
import definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;
import definitions.structures.finitedimensional.real.vectorspaces.ISpaceGenerator;

public class SpaceGenerator implements ISpaceGenerator, Serializable {

	private static final long serialVersionUID = 1L;

	private static ISpaceGenerator generator = null;

	private static Map<Integer, EuclideanSpace> cachedCoordinateSpaces;

	private static Map<Integer, EuclideanFunctionSpace> cachedFunctionSpaces;

	@Override
	public Map<Integer, EuclideanSpace> getCachedCoordinateSpaces() {
		return cachedCoordinateSpaces;
	}

	@Override
	public Map<Integer, EuclideanFunctionSpace> getCachedFunctionSpaces() {
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
	public String toString() {
		String ans = "";
		for (final int i : cachedCoordinateSpaces.keySet()) {
			ans += cachedCoordinateSpaces.get(i).toString() + "\r";
		}
		return ans;
	}

	@Override
	public void setCachedCoordinateSpaces(ISpaceGenerator gen) {
		cachedCoordinateSpaces = gen.getCachedCoordinateSpaces();
	}

	@Override
	public void setCachedFunctionSpaces(ISpaceGenerator gen) {
		cachedFunctionSpaces = gen.getCachedFunctionSpaces();
	}

	@Override
	public EuclideanFunctionSpace getPolynomialFunctionSpace(int n, double right,boolean ortho) {
		return new PolynomialFunctionSpace(n, right,ortho);
	}

}