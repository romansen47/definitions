package definitions.structures.finitedimensional.vectors.impl;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.finitedimensional.Generator;
import definitions.structures.finitedimensional.vectors.FiniteVector;
import definitions.structures.finitedimensional.vectorspaces.EuclideanSpace;
import definitions.structures.finitedimensional.vectorspaces.impl.FiniteDimensionalVectorSpace;

public class Tuple implements FiniteVector {

	final int dim;

	private Map<Vector, Double> coordinates;

	@Override
	public final int getDim() {
		return this.dim;
	}

	public Tuple(final double[] coordinates) {
		this.dim = coordinates.length;
		this.setCoordinates(new ConcurrentHashMap<>());
		int i = 0;
		for (final Vector vec : Generator.getGenerator().getSpacegenerator().getFiniteDimensionalVectorSpace(this.dim)
				.genericBaseToList()) {
			this.getCoordinates().put(vec, coordinates[i++]);
		}
	}

	public Tuple(final Map<Vector, Double> coordinates) {
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
	public Map<Vector, Double> getCoordinates() {
		return this.coordinates;
	}

	@Override
	public void setCoordinates(final Map<Vector, Double> coordinates) {
		this.coordinates = coordinates;
	}

	@Override
	public Set<Vector> getGenericBase() {
		return this.getCoordinates().keySet();
	}

	@Override
	public Boolean equals(final Vector vec) {
		return this.equals((Object) vec);
	}

	@Override
	public Map<Vector, Double> getCoordinates(final EuclideanSpace source) {
		return this.getCoordinates();
	}

}
