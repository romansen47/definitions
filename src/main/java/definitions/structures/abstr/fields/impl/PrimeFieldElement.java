package definitions.structures.abstr.fields.impl;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.groups.impl.CyclicRingElement;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

public class PrimeFieldElement extends CyclicRingElement implements Scalar, Vector { 
	private static final long serialVersionUID = 1L;

	private Field field;
	
	protected PrimeFieldElement(int r,Field f) {
		super(r);
		field=f;
	}

	@Override
	public double getValue() {
		return getRepresentant();
	}

	@Override
	public Scalar getInverse() {
		for (int i=1;i<field.getCharacteristic();i++) {
			if (field.getMuliplicativeMonoid().operation(this, field.get(i)).equals(field.getOne())) {
				return field.get(i);
			};
		}
		return null;
	}
}