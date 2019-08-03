package definitions.structures.finitedimensional.real.vectors.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.FunctionSpace;
import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.finitedimensional.real.vectors.Function;
import definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;

public abstract class GenericFunction implements Function {

	@Override
	public Map<Vector, Scalar> getCoordinates() {
		return null;
	}

	@Override
	public Scalar[] getGenericCoordinates() {
		return null;
	}

	@Override
	public Map<Vector, Scalar> getCoordinates(final EuclideanSpace space) {
		final Map<Vector, Scalar> newCoordinates = new ConcurrentHashMap<>();
		for (final Vector baseVec : space.genericBaseToList()) {
			newCoordinates.put(baseVec, space.innerProduct(this, baseVec));
		}
		return newCoordinates;
	}

	@Override
	public Integer getDim() {
		return -1;
	}

	@Override
	public boolean elementOf(final VectorSpace space) {
		return space instanceof FunctionSpace;
	}

	@Override
	public Boolean equals(final Vector vec) {
		return super.equals(vec);
	}

	@Override
	public void setCoordinates(final Map<Vector, Scalar> coordinates) {
	}

}
