package cache;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public class MyCache implements ICache {

	private static final long serialVersionUID = -4576680725550884235L;

	private Map<Integer, EuclideanSpace> trigonometricSpaceswithGowth;
	private Map<Integer, EuclideanSpace> coordinateSpaces;

	@Override
	public Map<Integer, EuclideanSpace> getConcreteCache() {
		if (this.coordinateSpaces == null) {
			this.coordinateSpaces = new HashMap<>();
		}
		return this.coordinateSpaces;
	}

	@Override
	public void setConcreteCache(Map<Integer, EuclideanSpace> cache) {
		this.coordinateSpaces = cache;
	}

	@Override
	public Map<Integer, EuclideanSpace> getTrigonometricSpaceswithGowth() {
		if (this.trigonometricSpaceswithGowth == null) {
			this.trigonometricSpaceswithGowth = new HashMap<>();
		}
		return this.trigonometricSpaceswithGowth;
	}

	@Override
	public void setTrigonometricSpaceswithGowth(Map<Integer, EuclideanSpace> trigonometricSpaceswithGowth) {
		this.trigonometricSpaceswithGowth = trigonometricSpaceswithGowth;
	}

}
