package definitions.structures.abstr.algebra.fields;

import definitions.structures.abstr.algebra.groups.DiscreetGroupElement;
import definitions.structures.abstr.algebra.groups.DiscreetRing;
import definitions.structures.abstr.algebra.semigroups.SemiGroupElement;

public interface DiscreetField extends DiscreetRing, Field {

	@Override
	DiscreetFieldElement getNeutralElement();

	/**
	 * the operation on the monoid.
	 * 
	 * @param first  first monoid element
	 * @param second second monoid element
	 * @return product of both of them
	 */
	@Override
	DiscreetFieldElement operation(SemiGroupElement first, SemiGroupElement second);
}
