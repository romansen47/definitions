package definitions.structures.abstr.algebra.groups;

import definitions.structures.abstr.algebra.rings.DiscreetSemiRing;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.vectorspaces.Ring;

public interface DiscreetRing extends DiscreetGroup, DiscreetSemiRing, Ring {

	/**
	 * In a discreet monoid we have countably many elements and can define a getter
	 * for integers.
	 * 
	 * @param representant the representant of the element.
	 * @return the corresponding monoid element.
	 */
	@Override
	Element get(Double representant);
}
