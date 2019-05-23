package definitions.structures.generic.finitedimensional.defs.vectors.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.FiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.SpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;

public class Tuple implements FiniteVector {

	final int dim;

	@Override
	public final int getDim() {
		return dim;
	}

	private Map<FiniteVector, Double> coordinates;

	public Tuple(double[] coordinates) throws Throwable {
		dim = coordinates.length;
		setCoordinates(new HashMap<>());
		int i = 0;
		for (final FiniteVector vec : SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(dim)
				.genericBaseToList()) {
			getCoordinates().put(vec, coordinates[i++]);
		}
	}

	public Tuple(Map<FiniteVector, Double> coordinates) throws Throwable {
		setCoordinates(coordinates);
		dim = coordinates.keySet().size();
	}

	Tuple(int dim) {
		this.dim = dim;
		coordinates = new HashMap<>();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Problems occured...";
		}
	}

	@Override
	public Map<FiniteVector, Double> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Map<FiniteVector, Double> coordinates) {
		this.coordinates = coordinates;
	}

	@Override
	public Set<FiniteVector> getGenericBase() throws Throwable {
		return new HashSet<>(getCoordinates().keySet());
	}

	@Override
	public boolean equals(Vector vec) throws Throwable {
		return equals((Object) vec);
	}

}
