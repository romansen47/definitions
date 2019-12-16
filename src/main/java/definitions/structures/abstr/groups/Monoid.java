package definitions.structures.abstr.groups;

import java.io.Serializable;

import definitions.Proceed;

/**
 * 
 * @author ro
 *
 *         A monoid is a set of things, which can be 'multiplied'.
 * 
 *         In detail, we have a method
 *         (MonoidElement,MonoidElement,)->MonoidElement.
 */

public interface Monoid extends Serializable {

	/**
	 * Getter for the order of the monoid - the amount of elements.
	 * 
	 * @return null, if infinitely many, order otherwise.
	 */ 
	Integer getOrder();

	/**
	 * the operation on the monoid.
	 * 
	 * @param first  first monoid element
	 * @param second second monoid element
	 * @return product of both of them
	 */ 
	@Proceed
	MonoidElement operation(GroupElement first, GroupElement second);

}
