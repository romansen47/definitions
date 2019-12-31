package definitions.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import definitions.settings.XmlPrintable;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

@Component
public class MyCache implements ICache, XmlPrintable{

	private static final long serialVersionUID = -4576680725550884235L;

	private Map<Integer, EuclideanSpace> coordinateSpaces = new ConcurrentHashMap<>();

	@Override
	public Map<Integer, EuclideanSpace> getConcreteCache() {
		return this.coordinateSpaces;
	}

	@Override
	public void setConcreteCache(final Map<Integer, EuclideanSpace> cache) {
		this.coordinateSpaces = cache;
	}
	
	@Override
	public String toXml() {
		return "custom cache";
	}

}
