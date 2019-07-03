package definitions.structures.generic.finitedimensional.defs.vectors.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.FunctionSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.Function;

public abstract class GenericFunction implements Function {

	@Override
	public Map<Vector, Double> getCoordinates() {
		return null;
	}

	@Override
	public double[] getGenericCoordinates() {
		return null;
	}

	@Override
	public Map<Vector, Double> getCoordinates(final EuclideanSpace space) {
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
	public boolean elementOf(final VectorSpace space) {
		return space instanceof FunctionSpace;
	}

	@Override
	public boolean equals(final Vector vec) {
		return super.equals(vec);
	}

	@Override
	public void setCoordinates(final Map<Vector, Double> coordinates) {
	}

}