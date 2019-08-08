package definitions.structures.finitedimensional.real.vectors.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.FunctionSpace;
import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.finitedimensional.real.vectors.Function;
import definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;

public abstract class GenericFunction implements Function {

	Map<EuclideanSpace, Map<Vector, Scalar>> coordinatesMap = new HashMap<>();
	private Map<Vector, Scalar> coordinates;

	@Override
	public Map<Vector, Scalar> getCoordinates() {
		return coordinates;
	}

	@Override
	public Map<Vector, Scalar> getCoordinates(final EuclideanSpace space) {
		if (coordinatesMap!=null) {
			if (coordinatesMap.get(space)!=null) {
				return coordinatesMap.get(space);
			}
			Map<Vector, Scalar> map=new ConcurrentHashMap<>();
			coordinatesMap.put(space,map);
		}
		final Map<Vector, Scalar> newCoordinates = new ConcurrentHashMap<>();
		for (final Vector baseVec : space.genericBaseToList()) {
			newCoordinates.put(baseVec, space.innerProduct(this, baseVec));
		}
		coordinatesMap.put(space,newCoordinates);
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
	public void setCoordinates(final Map<Vector, Scalar> coordinates, final EuclideanSpace space) {
		this.coordinatesMap.put(space, coordinates);
		this.setCoordinates(coordinates);
	}

	@Override
	public void setCoordinates(final Map<Vector, Scalar> coordinates) {
		this.coordinates=coordinates;
	}

	@Override
	public Map<EuclideanSpace, Map<Vector, Scalar>> getCoordinatesMap() {
		return this.coordinatesMap;
	}

}
