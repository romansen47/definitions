package definitions.cache;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import definitions.settings.XmlPrintable;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

@Component
public class MyCache implements ICache, XmlPrintable, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Map<Integer, EuclideanSpace> coordinateSpaces = new ConcurrentHashMap<>();

	@Override
	public Map<Integer, EuclideanSpace> getConcreteCache() {
		return coordinateSpaces;
	}

	@Override
	public void setConcreteCache(final Map<Integer, EuclideanSpace> cache) {
		coordinateSpaces = cache;
	}

	@Override
	public String toXml() {
		return "custom cache";
	}

}
