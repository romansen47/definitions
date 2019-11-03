package definitions.structures.euclidean.vectorspaces.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cache.ICache;
import cache.MyCache;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;
import definitions.structures.euclidean.vectorspaces.SubField;

@Configuration
public class SpaceGenerator implements ISpaceGenerator, Serializable {

	@Autowired
	private ICache cache = new MyCache();

	private static final long serialVersionUID = 1L;

	@Autowired
	private static ISpaceGenerator generator;

	@Bean
	public static ISpaceGenerator getInstance() {
//		if (generator == null) {
//			generator = new SpaceGenerator();
//		}
		return generator;
	}
	
	@Bean
	public static void setInstance(SpaceGenerator gen) {
		generator=gen;
	}


	@Override
	public String toString() {
		return this.getCache().toString();
	}

	@Override
	// TODO!
	public EuclideanSpace convert(EuclideanSpace complexSpace, SubField subField) {
		final int ratio = complexSpace.getDim() / subField.getDim();
		return null;
	}

	@Override
	public ICache getCache() {
		return this.cache;
	}

	@Override
	@Bean
	public void setCache(ICache ans) {
		this.cache = ans;
	}

}