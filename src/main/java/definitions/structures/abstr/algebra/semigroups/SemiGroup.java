/**
 *
 */
package definitions.structures.abstr.algebra.semigroups;

import java.util.regex.Pattern;

import definitions.settings.XmlPrintable;

/**
 * @author ro
 *
 *         A semi group is a set of things, which can be 'multiplied'.
 * 
 *         In detail, we have a mapping (Element,Element)-Element.
 * 
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
	 * the operation on the semi group.
	 *
	 * @param first  first element
	 * @param second second element
	 * @return operation applied to both of them; for additive semi groups this will
	 *         be the sum, for multiplicative semi group this will be multiplication
	 */
	default Element operation(Element first, Element second) {
		return null;
	}

	/**
	 * Defining own xml-string
	 */
	@Override
	default String toXml() {
		return "<" + getClass().toString().split(Pattern.quote("$"))[0] + "/>\r";
	}

}
