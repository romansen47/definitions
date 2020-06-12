package definitions.structures.euclidean.vectorspaces.impl;

import java.io.Serializable;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import definitions.cache.MyCache;
import definitions.settings.XmlPrintable;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;

@Service
public class SpaceGenerator implements ISpaceGenerator, Serializable, XmlPrintable {

	private static final long serialVersionUID = 1L;

	private static SpaceGenerator instance;

	public static SpaceGenerator getInstance() {
		if (instance.logger == null) {
			instance.logger = LogManager.getLogger(SpaceGenerator.class);
			BasicConfigurator.configure();
		}
		return instance;
	}

	public static void setInstance(final SpaceGenerator gen) {
		instance = gen;
	}

	private Logger logger;

	@Autowired(required = true)
	private MyCache myCache;

	@Override
	public Logger getLogger() {
		return this.logger;
	}

	@Override
	public MyCache getMyCache() {
		return this.myCache;
	}

	@Override
	public void setMyCache(final MyCache ans) {
		this.myCache = ans;
	}

	@Override
	public String toXml() {
		return "the space generator";
	}

}