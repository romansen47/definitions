package definitions.structures.dynamicsystems;

import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.monoids.OrderedMonoid;

public interface ContinuousDynamicSystem extends DynamicSystem {

	@Override
	default OrderedMonoid getTimeSpace() {
		return (OrderedMonoid) RealLine.getInstance();
	}

}
