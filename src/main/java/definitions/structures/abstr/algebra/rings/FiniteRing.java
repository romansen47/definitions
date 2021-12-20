package definitions.structures.abstr.algebra.rings;

import definitions.structures.abstr.algebra.groups.DiscreetRing;
import definitions.structures.abstr.algebra.groups.FiniteGroup;
import definitions.structures.abstr.algebra.semigroups.Element;

public interface FiniteRing extends FiniteGroup, DiscreetRing {

	default Element getMultiplicativeInverseElement(final Element element) {
		final Element tmp = getMultiplicativeInverseElement(element);
		if (tmp != null) {
			return tmp;
		}
		for (double i = 1; i < this.getOrder(); i++) {
			final Element other = getMuliplicativeMonoid().operation(element, this.get(i));
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
		return getMultiplicativeInverseElement(element) != null;
	}

}
