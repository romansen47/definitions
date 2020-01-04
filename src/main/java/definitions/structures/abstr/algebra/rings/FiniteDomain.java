package definitions.structures.abstr.algebra.rings;

import definitions.structures.abstr.algebra.fields.FiniteField;
import definitions.structures.abstr.algebra.monoids.FiniteMonoid;
import definitions.structures.abstr.algebra.monoids.MonoidElement;
import definitions.structures.abstr.vectorspaces.RingElement;

public interface FiniteDomain extends IntegralDomain, FiniteField {
	/**
	 * {@inheritDoc}
	 */
	@Override
	default boolean divides(final RingElement devisor, final RingElement devident) {
		return this.getCoFactor(devisor, devident) != null;
	}

	/**
	 * method to get co factor in a product
	 * 
	 * @param divisor  the divisor
	 * @param divident
	 * @return the co factor
	 */
	default FiniteRingElement getCoFactor(final RingElement divisor, final RingElement divident) {
		for (final MonoidElement el : ((FiniteMonoid) this.getMuliplicativeMonoid()).getOperationMap().get(divisor)
				.keySet()) {
			if (((FiniteMonoid) this.getMuliplicativeMonoid()).operation(divisor, el).equals(divident)) {
				return (FiniteRingElement) el;
			}
		}
		return null;
	}

}
