package definitions.structures.abstr.groups;

import java.io.Serializable;

import definitions.Proceed;
import definitions.settings.XmlPrintable;
import settings.Measurable;

/**
 * 
 * @author ro
 *
 *         A monoid is a set of things, which can be 'multiplied'.
 * 
 *         In detail, we have a method
 *         (MonoidElement,MonoidElement,)->MonoidElement.
 */

public interface Monoid extends SemiGroup{

	/**
	 * Getter for the identity element
	 * 
	 * @return the neutral element of the semi group
	 */
	MonoidElement getNeutralElement();
	
}
