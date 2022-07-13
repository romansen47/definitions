package definitions.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import definitions.settings.XmlPrintable;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

@Component
public class MyCache implements ICache, XmlPrintable {

	private final Logger logger = LogManager.getLogger(this.getClass());

	public Logger getLogger() {
		return this.logger;
	}

	private Map<Integer, EuclideanSpace> coordinateSpaces = new ConcurrentHashMap<>();

	@Override
	public Map<Integer, EuclideanSpace> getConcreteCache() {
		return this.coordinateSpaces;
	}

	@Override
	public void setConcreteCache(final Map<Integer, EuclideanSpace> cache) {
		logger.info("setter triggered: {}", "concrete cache");
		this.coordinateSpaces = cache;
	}

	@Override
	public String toXml() {
		return "custom cache";
	}

}
