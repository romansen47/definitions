package definitions.structures.abstr.algebra.monoids;

import definitions.structures.abstr.algebra.rings.FiniteRingElement;
import definitions.structures.abstr.algebra.semigroups.FiniteSemiGroup;
import definitions.structures.abstr.algebra.semigroups.FiniteSemiGroupElement;
import definitions.structures.abstr.algebra.semigroups.SemiGroupElement;

public interface FiniteAbelianSemiGroup extends AbelianSemiGroup, FiniteSemiGroup {

	/**
	 * {@inheritDoc}
	 */ 
	default FiniteSemiGroupElement operation(final SemiGroupElement first, final SemiGroupElement second) {
		if (((FiniteRingElement) first).getRepresentant() <= ((FiniteRingElement) second).getRepresentant()) {
			return (FiniteSemiGroupElement) FiniteSemiGroup.super.operation(first, second);
		} else {
			return (FiniteSemiGroupElement) FiniteSemiGroup.super.operation(second, first);
		}
	}

}
