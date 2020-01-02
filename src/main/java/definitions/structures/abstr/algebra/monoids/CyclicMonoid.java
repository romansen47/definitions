package definitions.structures.abstr.algebra.monoids;

import definitions.structures.abstr.algebra.groups.GroupElement;

public interface CyclicMonoid extends Monoid {

	/**
	 * Method to obtain a generator of the monoid.
	 * 
	 * @return one of the generators of the monoid.
	 */
	GroupElement getGenerator();
}
