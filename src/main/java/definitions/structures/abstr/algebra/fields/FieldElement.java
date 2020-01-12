package definitions.structures.abstr.algebra.fields;

import java.util.Map;

import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public interface FieldElement extends Scalar {
 
	@Override
	default double getDoubleValue() { 
		return getRepresentant();
	}

	
}
