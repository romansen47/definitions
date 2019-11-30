package definitions.structures.euclidean.vectorspaces.impl;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import definitions.cache.MyCache;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;

@Component
public class SpaceGenerator implements ISpaceGenerator, Serializable {

	private final Logger logger = Logger.getLogger(SpaceGenerator.class);
	
	@Autowired(required = true)
	private MyCache myCache;

	private static final long serialVersionUID = 1L;
 
	private static SpaceGenerator instance;

	public static SpaceGenerator getInstance() { 
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