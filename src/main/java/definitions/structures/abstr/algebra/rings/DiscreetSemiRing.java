package definitions.structures.abstr.algebra.rings;

import definitions.structures.abstr.algebra.monoids.DiscreetMonoid;
import definitions.structures.abstr.algebra.semigroups.DiscreetSemiGroup;

public interface DiscreetSemiRing extends DiscreetSemiGroup, SemiRing {

	/**
	 * Getter for the multiplicative monoid of the ring.
	 * 
	 * @return the multiplicative monoid of the ring
	 */
	DiscreetMonoid getMuliplicativeMonoid();
}
