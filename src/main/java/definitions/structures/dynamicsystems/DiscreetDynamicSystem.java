package definitions.structures.dynamicsystems;

import definitions.structures.abstr.groups.OrderedMonoid;
import definitions.structures.abstr.groups.impl.Naturals;

public interface DiscreetDynamicSystem extends DynamicSystem {

	@Override
	default OrderedMonoid getTimeSpace() {
		return Naturals.getInstance();
	}
}
