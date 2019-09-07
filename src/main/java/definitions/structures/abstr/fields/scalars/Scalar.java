package definitions.structures.abstr.fields.scalars;

import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

public interface Scalar extends Vector, FiniteVectorMethods {

	/**
	 * This has practical reasons. Gives double value, if possible.
	 * 
	 * @return the double value
	 */
	double getValue();

	Scalar getInverse();

}
