package definitions.structures.abstr.groups.impl;

import java.util.Map;

import definitions.structures.abstr.groups.GroupElement;
import definitions.structures.abstr.groups.Monoid;
import definitions.structures.abstr.groups.MonoidElement;

public interface FiniteMonoid extends Monoid {

	Map<MonoidElement, Map<MonoidElement, MonoidElement>> getOperationMap();

	@Override
	default MonoidElement operation(GroupElement first, GroupElement second) {
		if (getOperationMap().get(first)==null) {
			return null;
		}
		return getOperationMap().get(first).get(second);
	}
}
