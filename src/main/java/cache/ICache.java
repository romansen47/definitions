package cache;

import java.io.Serializable;
import java.util.Map;

import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public interface ICache extends Serializable {

	Map<Integer, EuclideanSpace> getConcreteCache();

	void setConcreteCache(Map<Integer, EuclideanSpace> cache);

	Map<Integer, EuclideanSpace> getTrigonometricSpaceswithGowth();

	void setTrigonometricSpaceswithGowth(Map<Integer, EuclideanSpace> trigonometricSpaceswithGowth);

}
