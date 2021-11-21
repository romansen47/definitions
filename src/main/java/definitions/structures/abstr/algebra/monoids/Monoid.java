package definitions.structures.abstr.algebra.monoids;

import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.algebra.semigroups.SemiGroup;

/**
 *
 * @author ro
 *
 *         A monoid is a set of things, which can be 'multiplied'.
 *
 *         In detail, we have a method
 *         (MonoidElement,MonoidElement)-MonoidElement.
 */

public interface Monoid extends SemiGroup {

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Element operation(Element first, Element second) {
		final Element neutralElement = getNeutralElement();
		Element ans;
		if (first.equals(neutralElement)) {
			ans = second;
		} else if (second.equals(neutralElement)) {
			ans = first;
		} else {
			ans = SemiGroup.super.operation(first, second);
		}
		return ans;
	}

	/**
	 * Getter for the identity element
	 *
	 * @return the neutral element of the semi group
	 */
	Element getNeutralElement();

}
