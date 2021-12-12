package definitions.structures.abstr.algebra.rings;

import definitions.structures.abstr.algebra.fields.FieldElement;
import definitions.structures.abstr.algebra.fields.FiniteField;
import definitions.structures.abstr.algebra.groups.FiniteGroup;
import definitions.structures.abstr.algebra.monoids.FiniteMonoid;
import definitions.structures.abstr.algebra.semigroups.Element;

public interface FiniteDomain extends IntegralDomain, FiniteField {
	/**
	 * {@inheritDoc}
	 */
	@Override
	default boolean divides(final Element devisor, final Element devident) {
		return this.getCoFactor(devisor, devident) != null;
	}

	/**
	 * method to get co factor in a product
	 *
	 * @param divisor  the divisor
	 * @param divident
	 * @return the co factor
	 */
	default Element getCoFactor(final Element divisor, final Element divident) {
		for (final Element el : ((FiniteMonoid) this.getMuliplicativeMonoid()).getOperationMap().get(divisor)
				.keySet()) {
			if (((FiniteMonoid) this.getMuliplicativeMonoid()).operation(divisor, el).equals(divident)) {
				return el;
			}
		}
		return null;
	}

	@Override
	default FieldElement getInverseElement(Element element) {
		return FiniteField.super.getInverseElement(element);
	}

	@Override
	FiniteGroup getMuliplicativeMonoid();

}
