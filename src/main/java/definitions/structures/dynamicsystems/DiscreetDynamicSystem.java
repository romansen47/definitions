package definitions.structures.dynamicsystems;

import definitions.structures.abstr.algebra.monoids.OrderedMonoid;
import definitions.structures.abstr.algebra.semigroups.impl.Naturals;

public interface DiscreetDynamicSystem extends DynamicSystem {

	@Override
	default OrderedMonoid getTimeSpace() {
		return Naturals.getInstance();
	}
}
