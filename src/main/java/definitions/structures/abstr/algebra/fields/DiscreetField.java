package definitions.structures.abstr.algebra.fields;

import definitions.structures.abstr.algebra.groups.DiscreetGroup;
import definitions.structures.abstr.algebra.groups.DiscreetRing;
import definitions.structures.abstr.algebra.semigroups.Element;

public interface DiscreetField extends DiscreetRing, Field {

	@Override
	FieldElement getNeutralElement();

	/**
	 * the operation on the monoid.
	 * 
	 * @param first  first monoid element
	 * @param second second monoid element
	 * @return product of both of them
	 */
	@Override
	FieldElement operation(Element first, Element second);

	@Override
	DiscreetGroup getMuliplicativeMonoid();
}
