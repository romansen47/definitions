package definitions.structures.abstr.algebra.monoids;

import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.algebra.semigroups.FiniteSemiGroup;

/**
 *
 * @author ro
 *
 *         A finite abelian semi group is a semi group that is finite and
 *         abelian
 */
public interface FiniteAbelianSemiGroup extends AbelianSemiGroup, FiniteSemiGroup {

	/**
	 * {@inheritDoc}
	 */
	@Override
	Element operation(final Element first, final Element second);

}
