package definitions.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

@Component
public class MyCache implements ICache {

	private static final long serialVersionUID = -4576680725550884235L;
 
	private Map<Integer, EuclideanSpace> coordinateSpaces;

	@Override
	public Map<Integer, EuclideanSpace> getConcreteCache() {
		if (this.coordinateSpaces == null) {
			this.coordinateSpaces = new ConcurrentHashMap<>();
		}
		return this.coordinateSpaces;
	}

	@Override 
	public void setConcreteCache(Map<Integer, EuclideanSpace> cache) {
		this.coordinateSpaces = cache;
	}

}
