package definitions.structures.abstr.fields.scalars;

import definitions.structures.abstr.vectorspaces.vectors.Vector;

public interface Scalar extends Vector {

	double getValue();

	Scalar getInverse();

}
