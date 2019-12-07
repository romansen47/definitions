package definitions.structures.euclidean.vectorspaces.impl;

import java.io.Serializable;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import definitions.cache.MyCache;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;

@Configuration
public class SpaceGenerator implements ISpaceGenerator, Serializable {

	private Logger logger;

	@Autowired(required = true)
	private MyCache myCache;

	private static final long serialVersionUID = 1L;

	private static SpaceGenerator instance;

	public static SpaceGenerator getInstance() {
		if (instance.logger == null) {
			instance.logger = LogManager.getLogger(SpaceGenerator.class);
			BasicConfigurator.configure();
		}
		return instance;
	}

	public static void setInstance(SpaceGenerator gen) {
		instance = gen;
	}

	@Override
	public MyCache getMyCache() {
		return this.myCache;
	}

	@Override
	public void setMyCache(MyCache ans) {
		this.myCache = ans;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

}