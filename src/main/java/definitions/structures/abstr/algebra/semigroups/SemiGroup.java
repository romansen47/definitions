/**
 * 
 */
package definitions.structures.abstr.algebra.semigroups;

import java.util.regex.Pattern;

import definitions.settings.XmlPrintable; 

/**
 * @author RoManski
 *
 *         A semi group G is a special monoid with an Element called identity
 *         element. The mappings
 *
 *         alpha: Vector vec - product(Vector vec,identityElement) beta: Vector
 *         vec - product(identityElement,Vector vec)
 *
 *         are identically equal to the identity mapping on G.
 */
public interface SemiGroup extends XmlPrintable {
	
	/**
	 * Getter for the order of the monoid - the amount of elements.
	 * 
	 * @return null, if infinitely many, order otherwise.
	 */
	default Integer getOrder() {
		return null;
	}

	/**
	 * the operation on the monoid.
	 * 
	 * @param first  first monoid element
	 * @param second second monoid element
	 * @return product of both of them
	 */
	default	Element operation(Element first, Element second) {
		return null;
	}
	
	@Override
	default String toXml() {
		return "<"+this.getClass().toString().split(Pattern.quote("$"))[0]+ "/>\r";
	}

}
