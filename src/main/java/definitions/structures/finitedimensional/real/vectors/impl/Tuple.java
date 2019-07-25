package definitions.structures.finitedimensional.real.vectors.impl;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.finitedimensional.real.Generator;
import definitions.structures.finitedimensional.real.vectors.FiniteVector;
import definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;
import definitions.structures.finitedimensional.real.vectorspaces.impl.FiniteDimensionalVectorSpace;

public class Tuple implements FiniteVector {

	final int dim;

	private Map<Vector, Scalar> coordinates;

	@Override
	public final int getDim() {
		return this.dim;
	}

	public Tuple(final Scalar[] coordinates) {
		this.dim = coordinates.length;
		this.setCoordinates(new ConcurrentHashMap<>());
		int i = 0;
		for (final Vector vec : ((EuclideanSpace) Generator.getGenerator().getSpacegenerator().getFiniteDimensionalVectorSpace(this.dim))
				.genericBaseToList()) {
			this.getCoordinates().put(vec, coordinates[i++]);
		}
	}

	public Tuple(final Map<Vector, Scalar> coordinates) {
		this.setCoordinates(coordinates);
		this.dim = coordinates.keySet().size();
	}

	Tuple(final int dim) {
		this.dim = dim;
		this.coordinates = new ConcurrentHashMap<>();
	}

	@Override
	public boolean elementOf(final VectorSpace space) {
		return (space instanceof FiniteDimensionalVectorSpace)
				&& (((FiniteDimensionalVectorSpace) space).dim() == this.dim);
	}

	@Override
	public boolean equals(final Object vec) {
		if (!(vec instanceof Vector)) {
			return false;
		} else if ((vec instanceof Tuple) && (((Tuple) vec).dim == this.dim)) {
			return this.getCoordinates().equals(((Tuple) vec).getCoordinates());
		}
		return false;
	}

	@Override
	public String toString() {
		String str = "";
		try {
			for (final Vector vec : this.getGenericBase()) {
				str += this.getCoordinates().get(vec) + "\r";
			}
			return str;
		} catch (final Throwable e) {
			e.printStackTrace();
			return "Problems occured...";
		}
	}

	@Override
	public Map<Vector, Scalar> getCoordinates() {
		return this.coordinates;
	}

	@Override
	public void setCoordinates(final Map<Vector, Scalar> coordinates) {
		this.coordinates = coordinates;
	}

	@Override
	public Set<Vector> getGenericBase() {
		return this.getCoordinates().keySet();
	}

	@Override
	public Boolean equals(final Vector vec) {
		for (int i=0;i<vec.getGenericCoordinates().length;i++) {
			if (!vec.getGenericCoordinates()[i].equals(getGenericCoordinates()[i])) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Map<Vector, Scalar> getCoordinates(final EuclideanSpace source) {
		return this.getCoordinates();
	}

}
