package definitions.structures.generic.finitedimensional.defs.vectors.impl;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.FiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.SpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;

public class Tuple implements FiniteVector {

	final int dim;

	private Map<Vector, Double> coordinates;

	@Override
	public final int getDim() {
		return dim;
	}

	public Tuple(double[] coordinates) throws Throwable {
		dim = coordinates.length;
		setCoordinates(new ConcurrentHashMap<>());
		int i = 0;
		for (final Vector vec : SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(dim).genericBaseToList()) {
			getCoordinates().put(vec, coordinates[i++]);
		}
	}

	public Tuple(Map<Vector, Double> coordinates) throws Throwable {
		setCoordinates(coordinates);
		dim = coordinates.keySet().size();
	}

	Tuple(int dim) {
		this.dim = dim;
		coordinates = new ConcurrentHashMap<>();
	}

	@Override
	public boolean elementOf(VectorSpace space) throws Throwable {
		return space instanceof FiniteDimensionalVectorSpace && ((FiniteDimensionalVectorSpace) space).dim() == dim;
	}

	@Override
	public boolean equals(Object vec) {
		if (!(vec instanceof Vector)) {
			return false;
		} else if (vec instanceof Tuple && ((Tuple) vec).dim == dim) {
			return getCoordinates().equals(((Tuple) vec).getCoordinates());
		}
		return false;
	}

	@Override
	public String toString() {
		String str = "";
		try {
			for (final Vector vec : getGenericBase()) {
				str += getCoordinates().get(vec) + "\r";
			}
			return str;
		} catch (final Throwable e) {
			e.printStackTrace();
			return "Problems occured...";
		}
	}

	@Override
	public Map<Vector, Double> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Map<Vector, Double> coordinates) {
		this.coordinates = coordinates;
	}

	@Override
	public Set<Vector> getGenericBase() throws Throwable {
		return getCoordinates().keySet();
	}

	@Override
	public boolean equals(Vector vec) throws Throwable {
		return equals((Object) vec);
	}

	@Override
	public Map<Vector, Double> getCoordinates(EuclideanSpace source) throws Throwable {
		return getCoordinates();
	}

}
