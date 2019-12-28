package definitions.structures.abstr.algebra.fields.scalars;

import definitions.settings.XmlPrintable;
import definitions.structures.abstr.algebra.monoids.MonoidElement;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

public interface Scalar extends MonoidElement, Vector, FiniteVectorMethods, XmlPrintable {

	Scalar getInverse();

	/**
	 * This has practical reasons. Gives double value, if possible.
	 * 
	 * @return the double value
	 */
	double getValue();

}
