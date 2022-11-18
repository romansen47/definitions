package definitions.structures.euclidean.vectorspaces.impl;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import definitions.cache.MyCache;
import definitions.settings.XmlPrintable;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;

@Component
public class SpaceGenerator implements ISpaceGenerator, XmlPrintable {

	public static final Logger logger = org.apache.logging.log4j.LogManager.getLogger(SpaceGenerator.class);

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