package definitions.structures.abstr.mappings;

import definitions.structures.abstr.algebra.monoids.Monoid;
import definitions.structures.abstr.algebra.semigroups.Element;

public interface MonoidHomomorphism extends Element {

	/**
	 * Getter for the source space.
	 * 
	 * @return the source space.
	 */
	Monoid getSource();

	/**
	 * Getter for the target space.
	 * 
	 * @return the target space.
	 */
	Monoid getTarget();
	
}
