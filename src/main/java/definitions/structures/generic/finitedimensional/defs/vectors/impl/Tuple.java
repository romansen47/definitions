package definitions.structures.generic.finitedimensional.defs.vectors.impl;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.generic.finitedimensional.defs.Generator;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.FiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;

public class Tuple implements FiniteVector {

	final int dim;

	private Map<Vector, Double> coordinates;

	@Override
	public final int getDim() {
		return this.dim;
	}

	public Tuple(final double[] coordinates) throws Throwable {
		this.dim = coordinates.length;
		this.setCoordinates(new ConcurrentHashMap<>());
		int i = 0;
		for (final Vector vec : Generator.getGenerator().getSpacegenerator().getFiniteDimensionalVectorSpace(this.dim)
				.genericBaseToList()) {
			this.getCoordinates().put(vec, coordinates[i++]);
		}
	}

	public Tuple(final Map<Vector, Double> coordinates) throws Throwable {
		this.setCoordinates(coordinates);
		this.dim = coordinates.keySet().size();
	}

	Tuple(final int dim) {
		this.dim = dim;
		this.coordinates = new ConcurrentHashMap<>();
	}

	@Override
	public boolean elementOf(final VectorSpace space) throws Throwable {
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
	public Set<Vector> getGenericBase() throws Throwable {
		return this.getCoordinates().keySet();
	}

	@Override
	public boolean equals(final Vector vec) throws Throwable {
		return this.equals((Object) vec);
	}

	@Override
	public Map<Vector, Double> getCoordinates(final EuclideanSpace source) throws Throwable {
		return this.getCoordinates();
	}

}
