package definitions.structures.abstr.algebra.monoids;

import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.algebra.semigroups.FiniteSemiGroup;

public interface FiniteAbelianSemiGroup extends AbelianSemiGroup, FiniteSemiGroup {

	/**
	 * {@inheritDoc}
	 */
	@Override
	Element operation(final Element first, final Element second);

}
