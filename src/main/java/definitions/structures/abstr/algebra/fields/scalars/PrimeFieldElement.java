package definitions.structures.abstr.algebra.fields.scalars;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.rings.impl.FiniteResidueClassElement;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectors.FiniteVector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public class PrimeFieldElement extends FiniteResidueClassElement implements FiniteVector, FiniteFieldElement {

	private static final long serialVersionUID = 1L;

	private final Field field;

	private Map<Vector, Scalar> coordinates;

	public PrimeFieldElement(final int r, final Field f) {
		super(r);
		this.field = f;
	}

	@Override
	public Map<Vector, Scalar> getCoordinates() {
		final Map<Vector, Scalar> map = new HashMap<>();
		map.put(this.field.getOne(), this);
		return map;
	}

	@Override
	public Map<Vector, Scalar> getCoordinates(final EuclideanSpace source) {
		if (this.coordinates == null) {
			this.coordinates = new HashMap<>();
			this.coordinates.put(this.field.getOne(), this);
		}
		return this.coordinates;
	}

	@Override
	public Integer getDim() {
		return 1;
	}

	@Override
	public Set<Vector> getGenericBase() {
		final Set<Vector> base = new HashSet<>();
		base.add(this.field.getOne());
		return base;
	}

	@Override
	public Scalar getInverse() {
		for (int i = 1; i < this.field.getCharacteristic(); i++) {
			if (this.field.getMuliplicativeMonoid().operation(this, this.field.get(i)).equals(this.field.getOne())) {
				return this.field.get(i);
			}
		}
		return null;
	}

	@Override
	public double getValue() {
		return this.getRepresentant();
	}

	@Override
	public void setCoordinates(final Map<Vector, Scalar> coordinates) {
		this.coordinates = coordinates;
	}

	@Override
	public void setCoordinates(final Map<Vector, Scalar> coordinates, final EuclideanSpace space) {
		this.coordinates = coordinates;
	}
}