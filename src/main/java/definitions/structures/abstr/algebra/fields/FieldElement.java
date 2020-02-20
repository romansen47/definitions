package definitions.structures.abstr.algebra.fields;

import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.euclidean.vectors.FiniteVector;

public interface FieldElement extends Scalar, FiniteVector {

	@Override
	default double getDoubleValue() {
		return getRepresentant();
	}

}
