package definitions.structures.abstr.algebra.rings;

import definitions.structures.abstr.algebra.monoids.DiscreetMonoid;

public interface DiscreetSemiRing extends DiscreetMonoid, SemiRing {

	/**
	 * Getter for the multiplicative monoid of the ring.
	 * 
	 * @return the multiplicative monoid of the ring
	 */
	@Override
	DiscreetMonoid getMuliplicativeMonoid();
}
