package definitions.structures.dynamicsystems;

import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.groups.OrderedMonoid;

public interface ContinuousDynamicSystem extends DynamicSystem {
	
	@Override
	default OrderedMonoid getTimeSpace() {
		return (OrderedMonoid) RealLine.getInstance();
	}
	
}
