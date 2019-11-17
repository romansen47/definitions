package definitions.structures.euclidean.vectorspaces.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import definitions.cache.MyCache;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;

@Component
public class SpaceGenerator implements ISpaceGenerator, Serializable {

	@Autowired(required=true)
	private MyCache myCache;

	private static final long serialVersionUID = 1L;

	private static SpaceGenerator generator;
	
	public static SpaceGenerator getInstance() {
		if (generator == null) {
			generator = new SpaceGenerator();
		}
		return generator;
	}
	
	public static void setInstance(SpaceGenerator gen) {
		generator=gen;
	}

	@Override
	public String toString() {
		return this.getMyCache().toString();
	}
	
	@Override
	public MyCache getMyCache() {
		return this.myCache;
	}

	@Override 
	public void setMyCache(MyCache ans) {
		this.myCache = ans;
	}

}