package definitions.structures.abstr.fields.scalars;

import definitions.settings.XmlPrintable;
import definitions.structures.abstr.vectorspaces.RingElement;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import settings.annotations.Proceed;

public interface Scalar extends RingElement,Vector, FiniteVectorMethods, XmlPrintable {

	/**
	 * This has practical reasons. Gives double value, if possible.
	 * 
	 * @return the double value
	 */ 
	double getValue();
 
	Scalar getInverse();

}
