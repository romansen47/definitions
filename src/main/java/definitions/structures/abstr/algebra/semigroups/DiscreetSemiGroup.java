package definitions.structures.abstr.algebra.semigroups;

import definitions.structures.abstr.algebra.monoids.MonoidElement;

public interface DiscreetSemiGroup extends SemiGroup {
	
	/**
	 * In a discreet monoid we have countably many elements and can define a getter
	 * for integers.
	 * 
	 * @param representant the representant of the element.
	 * @return the corresponding monoid element.
	 */
	DiscreetSemiGroupElement get(Integer representant);
}
