package definitions.structures.abstr.fields.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.groups.impl.CyclicRingElement;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectors.FiniteVector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public class PrimeFieldElement extends CyclicRingElement implements FiniteVector,Scalar {
	
	private static final long serialVersionUID = 1L;

	private Field field;

	protected PrimeFieldElement(int r, Field f) {
		super(r);
		field = f;
	}

	@Override
	public double getValue() {
		return getRepresentant();
	}

	@Override
	public Scalar getInverse() {
		for (int i = 1; i < field.getCharacteristic(); i++) {
			if (field.getMuliplicativeMonoid().operation(this, field.get(i)).equals(field.getOne())) {
				return field.get(i);
			}
		}
		return null;
	}

	@Override
	public Map<Vector, Scalar> getCoordinates() {
		Map<Vector, Scalar> map = new HashMap<>();
		map.put(field.getOne(), this);
		return map;
	}

	@Override
	public Set<Vector> getGenericBase() {
		Set<Vector> base = new HashSet<>();
		base.add(field.getOne());
		return base;
	}

	private Map<Vector, Scalar> coordinates;
	
	@Override
	public void setCoordinates(Map<Vector, Scalar> coordinates, EuclideanSpace space) {
		this.coordinates=coordinates;
	}
	
	@Override
	public void setCoordinates(Map<Vector, Scalar> coordinates) {
		this.coordinates=coordinates;
	}

	@Override
	public Map<Vector, Scalar> getCoordinates(EuclideanSpace source) {
		if (coordinates == null) {
			coordinates = new HashMap<>();
			coordinates.put(field.getOne(), this);
		}
		return coordinates;
	}
	
	@Override
	public Integer getDim() {
		return 1;
	}
}