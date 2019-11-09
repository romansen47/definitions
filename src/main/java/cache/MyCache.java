package cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

@Configuration
public class MyCache implements ICache {

	private static final long serialVersionUID = -4576680725550884235L;

	@Autowired
	private Map<Integer, EuclideanSpace> coordinateSpaces;

	@Override
	public Map<Integer, EuclideanSpace> getConcreteCache() {
		if (this.coordinateSpaces == null) {
			this.coordinateSpaces = new ConcurrentHashMap<>();
		}
		return this.coordinateSpaces;
	}

	@Override
	@Bean
	@Autowired
	public void setConcreteCache(Map<Integer, EuclideanSpace> cache) {
		this.coordinateSpaces = cache;
	}

}
