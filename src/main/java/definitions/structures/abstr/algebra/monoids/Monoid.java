package definitions.structures.abstr.algebra.monoids;

import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.algebra.semigroups.SemiGroup;

/**
 *
 * @author ro
 *
 *         A monoid M is a special semi group with an Element called identity
 *         element. The mappings
 *
 *         alpha: Vector vec - product(Vector vec,identityElement) beta: Vector
 *         vec - product(identityElement,Vector vec)
 *
 *         are identically equal to the identity mapping on M.
 */

public interface Monoid extends SemiGroup {

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Element operation(Element first, Element second) {
		final Element neutralElement = this.getNeutralElement();
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
	 * Getter for the neutral element
	 *
	 * @return the neutral element of the semi group
	 */
	Element getNeutralElement();

}
