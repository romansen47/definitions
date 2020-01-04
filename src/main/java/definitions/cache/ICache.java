package definitions.cache;

import java.io.Serializable;
import java.util.Map;

import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public interface ICache {

	Map<Integer, EuclideanSpace> getConcreteCache();

	void setConcreteCache(Map<Integer, EuclideanSpace> cache);

}
