package definitions.structures.abstr.algebra.rings;

import definitions.structures.abstr.algebra.groups.FiniteAbelianGroup;
import definitions.structures.abstr.algebra.monoids.MonoidElement;

public interface FiniteCommutativeRing extends FiniteRing, FiniteAbelianGroup {

	/**
	 * {@inheritDoc}
	 */
	@Override
	default FiniteRingElement operation(final MonoidElement first, final MonoidElement second) {
		if (((FiniteRingElement) first).getRepresentant() <= ((FiniteRingElement) second).getRepresentant()) {
			return FiniteRing.super.operation(first, second);
		} else {
			return FiniteRing.super.operation(second, first);
		}
	}
}
