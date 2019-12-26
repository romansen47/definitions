package definitions.structures.abstr.groups.impl;

import java.util.Map;

import definitions.structures.abstr.groups.GroupElement;
import definitions.structures.abstr.groups.Monoid;
import definitions.structures.abstr.groups.MonoidElement;

public interface FiniteMonoid extends DiscreetMonoid {

	Map<MonoidElement, Map<MonoidElement, MonoidElement>> getOperationMap();

	@Override
	default MonoidElement operation(final MonoidElement first, final MonoidElement second) {
		if (this.getOperationMap().get(first) == null) {
			return null;
		}
		return this.getOperationMap().get(first).get(second);
	}
}
