package definitions.structures.euclidean.vectorspaces.impl;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import definitions.cache.MyCache;
import definitions.settings.XmlPrintable;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;

@Service
public class SpaceGenerator implements ISpaceGenerator, XmlPrintable {

	private static SpaceGenerator instance;

	public static SpaceGenerator getInstance() {
		return SpaceGenerator.instance;
	}

	public static void setInstance(final SpaceGenerator gen) {
		SpaceGenerator.instance = gen;
	}

	public static final org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager
			.getLogger(SpaceGenerator.class);

	@Autowired(required = true)
	private MyCache myCache;

	@Override
	public Logger getLogger() {
		return SpaceGenerator.logger;
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