package definitions.structures.euclidean.vectorspaces.impl;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

import definitions.structures.abstr.fields.Field;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;
import definitions.structures.euclidean.vectorspaces.SubField;

public class SpaceGenerator implements ISpaceGenerator, Serializable {

	private CacheManager cacheManager;

	private static final long serialVersionUID = 1L;

	private static ISpaceGenerator generator = null;

	private static Map<Integer, EuclideanSpace> cachedCoordinateSpaces;

	private static Map<Integer, EuclideanFunctionSpace> cachedFunctionSpaces;

	@Override
	public Map<Integer, EuclideanSpace> getCachedCoordinateSpaces() {
		return cachedCoordinateSpaces;
	}

	@Override
	public Map<Integer, EuclideanFunctionSpace> getCachedFunctionSpaces() {
		return cachedFunctionSpaces;
	}

	public static ISpaceGenerator getInstance() {
		if (generator == null) {
			generator = new SpaceGenerator();
		}
		return generator;
	}

	private Cache<Long, EuclideanSpace> myCache;

	private SpaceGenerator() {
		cachedCoordinateSpaces = new ConcurrentHashMap<>();
		cachedFunctionSpaces = new ConcurrentHashMap<>();
		this.setCacheManager(CacheManagerBuilder.newCacheManagerBuilder().withCache("myCache", CacheConfigurationBuilder
				.newCacheConfigurationBuilder(Long.class, EuclideanSpace.class, ResourcePoolsBuilder.heap(100)).build())
				.build(true));
		this.setMyCache(this.cacheManager.getCache("myCache", Long.class, EuclideanSpace.class));

	}

	@Override
	public String toString() {
		String ans = "";
		for (final int i : cachedCoordinateSpaces.keySet()) {
			ans += cachedCoordinateSpaces.get(i).toString() + "\r";
		}
		return ans;
	}

	@Override
	public void setCachedCoordinateSpaces(ISpaceGenerator gen) {
		cachedCoordinateSpaces = gen.getCachedCoordinateSpaces();
	}

	@Override
	public void setCachedFunctionSpaces(ISpaceGenerator gen) {
		cachedFunctionSpaces = gen.getCachedFunctionSpaces();
	}

	@Override
	public EuclideanFunctionSpace getPolynomialFunctionSpace(Field field, int n, double right, boolean ortho) {
		return new PolynomialFunctionSpace(field, n, right, ortho);
	}

	@Override
	public EuclideanSpace convert(EuclideanSpace complexSpace, SubField subField) {
		final int ratio = complexSpace.getDim() / subField.getDim();

		return null;
	}

	/**
	 * @return the cacheManager
	 */
	@Override
	public CacheManager getCacheManager() {
		return this.cacheManager;
	}

	/**
	 * @param cacheManager the cacheManager to set
	 */
	@Override
	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	/**
	 * @return the myCache
	 */
	@Override
	public Cache<Long, EuclideanSpace> getMyCache() {
		return this.myCache;
	}

	/**
	 * @param myCache the myCache to set
	 */
	@Override
	public void setMyCache(Cache<Long, EuclideanSpace> myCache) {
		this.myCache = myCache;
	}

}