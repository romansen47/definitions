package definitions.structures.abstr.algebra.monoids;

import definitions.structures.abstr.algebra.semigroups.Element;

public interface CyclicMonoid extends FiniteMonoid {

	/**
	 * Method to obtain a generator of the monoid.
	 * 
	 * @return one of the generators of the monoid.
	 */
	Element getGenerator();
}
