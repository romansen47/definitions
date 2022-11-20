/**
 *
 */
package definitions.structures.abstr.algebra.groups;

import definitions.structures.abstr.algebra.monoids.Monoid;
import definitions.structures.abstr.algebra.semigroups.Element;
import exceptions.DevisionByZeroException;

/**
 * A Group G is a monoid, such that for every element there exists a so called
 * inverse element
 *
 * b = getInverseElement(a),
 *
 * such that
 *
 * operation(a,b) = operation(b,a) = getIdentityElement().
 *
 * @author ro
 */
public interface Group extends Monoid {

	/**
	 * Getter for the (by definition existing) inverse of an element within the
	 * group.
	 *
	 * @param element the input
	 * @return the inverse element of the input within the group.
	 * @throws DevisionByZeroException
	 */
	Element getInverseElement(Element element);

}
