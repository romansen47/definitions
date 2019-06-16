package definitions.structures.generic.finitedimensional.defs.vectors.impl;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.Function;

public abstract class GenericFunction implements Function {

	@Override
	public Map<Vector, Double> getCoordinates() {
		return null;
	}

	@Override
	public Set<Vector> getGenericBase() throws Throwable {
		return null;
	}

//	@Override
//	public Map<Vector, Double> getCoordinates(EuclideanSpace source) throws Throwable {
//		Map<Vector, Double> coordinates = new ConcurrentHashMap<>();
//		for (Vector baseVec : source.genericBaseToList()) {
//			coordinates.put(baseVec, source.product(this,baseVec));
//		}
//		return coordinates;
//	}

	@Override
	public Map<Vector, Double> getCoordinates(final EuclideanSpace space) throws Throwable {
		final Map<Vector, Double> newCoordinates = new ConcurrentHashMap<>();
		for (final Vector baseVec : space.genericBaseToList()) {
			newCoordinates.put(baseVec, space.product(this, baseVec));
		}
		return newCoordinates;
	}

	@Override
	public int getDim() {
		return 0;
	}

	@Override
	public boolean elementOf(final VectorSpace space) throws Throwable {
		return true;
	}

	@Override
	public boolean equals(final Vector vec) throws Throwable {
		return super.equals(vec);
	}

	@Override
	public void setCoordinates(final Map<Vector, Double> coordinates) {
	}

}
