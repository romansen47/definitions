package definitions.structures.abstr.algebra.groups;

import definitions.structures.abstr.algebra.semigroups.Element;

public interface DiscreetRing extends DiscreetGroup {

	/**
	 * In a discreet monoid we have countably many elements and can define a getter
	 * for integers.
	 * 
	 * @param representant the representant of the element.
	 * @return the corresponding monoid element.
	 */
	@Override
	Element get(Integer representant);
}
