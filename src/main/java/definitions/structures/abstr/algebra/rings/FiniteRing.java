package definitions.structures.abstr.algebra.rings;

import definitions.structures.abstr.algebra.groups.DiscreetRing;
import definitions.structures.abstr.algebra.groups.FiniteGroup;
import definitions.structures.abstr.algebra.semigroups.Element;

/**
 * @author ro
 *
 *         A finite ring is a ring with finite amount of elements
 *
 */
public interface FiniteRing extends FiniteGroup, DiscreetRing {

	/**
	 * method to determine the inverse element wrt multiplication. returns null if
	 * not exists
	 *
	 * @param element the given element
	 * @return the multiplicative inverse or null if not exists
	 */
	default Element getMultiplicativeInverseElement(final Element element) {
		final Element tmp = this.getMultiplicativeInverseElement(element);
		if (tmp != null) {
			return tmp;
		}
		for (double i = 1; i < this.getOrder(); i++) {
			final Element other = this.getMuliplicativeMonoid().operation(element, this.get(i));
			if (other.equals(this.get(1.))) {
				return other;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default boolean isUnit(final Element element) {
		return this.getMultiplicativeInverseElement(element) != null;
	}

}
