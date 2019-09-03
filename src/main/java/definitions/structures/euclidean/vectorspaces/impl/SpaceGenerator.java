package definitions.structures.euclidean.vectorspaces.impl;

import java.io.Serializable;

import cache.ICache;
import cache.MyCache;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;
import definitions.structures.euclidean.vectorspaces.SubField;

public class SpaceGenerator implements ISpaceGenerator, Serializable {

	private ICache cache = new MyCache();

	private static final long serialVersionUID = 1L;

	private static ISpaceGenerator generator = null;

	public static ISpaceGenerator getInstance() {
		if (generator == null) {
			generator = new SpaceGenerator();
		}
		return generator;
	}

	private SpaceGenerator() {
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
	public void setCache(ICache ans) {
		this.cache = ans;
	}

}