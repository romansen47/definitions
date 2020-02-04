package definitions.structures.abstr.algebra.fields;

import definitions.structures.abstr.algebra.fields.scalars.Scalar;

public interface FieldElement extends Scalar {
 
	@Override
	default double getDoubleValue() { 
		return getRepresentant();
	}
	
}
