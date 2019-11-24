package definitions.structures.euclidean.vectors.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectors.FiniteVector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.FiniteDimensionalVectorSpace;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

public class Tuple implements FiniteVector {

	public Tuple() {
		this.dim = 0;
	}

	private static final long serialVersionUID = -738201540142933649L;

	final int dim;

	private Map<Vector, Scalar> coordinates;

	@Override
	public Integer getDim() {
		return this.dim;
	}

	public Tuple(final Scalar[] coordinates) {
		this.dim = coordinates.length;
		this.setCoordinates(new ConcurrentHashMap<>());
		int i = 0;
		for (final Vector vec : SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(this.dim)
				.genericBaseToList()) {
			this.getCoordinates().put(vec, coordinates[i++]);
		}
	}

	public Tuple(final Map<Vector, Scalar> coordinates) {
		this.setCoordinates(coordinates);
		this.dim = coordinates.keySet().size();
	}

	public Tuple(final int dim) {
		this.dim = dim;
		this.coordinates = new ConcurrentHashMap<>();
	}

	@Override
	public boolean elementOf(final VectorSpace space) {
		if (!(space instanceof FiniteDimensionalVectorSpace)
				&& (((FiniteDimensionalVectorSpace) space).getDim() == this.dim)) {
			return false;
		}
		final List<Vector> base = ((EuclideanSpace) space).genericBaseToList();
		for (final Vector vec : this.coordinates.keySet()) {
			boolean ans = false;
			for (final Vector spaceBaseVec : base) {
				if (vec.equals(spaceBaseVec)) {
					ans = true;
				}
			}
			if (ans == false) {
				return false;
			}
			// if (!base.contains(vec)) {
			// return false;
			// }
		}
		return true;
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

	// @Override
	// public String toString() {
	// String str = "";
	// try {
	// for (int i = 0; i < this.dim; i++) {
	// str += this.getCoordinates()
	// .get(((EuclideanSpace)
	// SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(this.dim))
	// .genericBaseToList().get(i))
	// + "\r";
	// }
	// return str;
	// } catch (final Throwable e) {
	// e.printStackTrace();
	// return "Problems occured...";
	// }
	// }

	@Override
//	@XmlAttribute
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

	// @Override
	// public Boolean equals(final Vector vec) {
	// for (final Vector key : this.getCoordinates().keySet()) {
	// if (!(this.coordinates.get(key).getValue() ==
	// vec.getCoordinates().get(key).getValue())) {
	// return false;
	// }
	// }
	// return true;
	// }

	@Override
//	@XmlAttribute
	public Map<Vector, Scalar> getCoordinates(final EuclideanSpace source) {
		final Map<Vector, Scalar> newCoordinates = new ConcurrentHashMap<>();
		if (this.elementOf(source)) {
			return this.getCoordinates();
		} else {
			for (final Vector baseVec : source.genericBaseToList()) {
				newCoordinates.put(baseVec, source.innerProduct(this, baseVec));
			}
		}
		return newCoordinates;
	}

	@Override
	public void setCoordinates(Map<Vector, Scalar> coordinates, EuclideanSpace space) {
		this.setCoordinates(coordinates);
	}

	@Override
	public String toXml() {
		String ans = "<tuple>";
		for (Vector x : getGenericBase()) {
			ans += "<" + x.getClass() + ">\r"; 
			ans+= getCoordinates().get(x) + "\r</" + x.getClass().toString().split("class ")[1] + ">\r";
		}
		ans = "<tuple>";
		return ans;
	}

}
