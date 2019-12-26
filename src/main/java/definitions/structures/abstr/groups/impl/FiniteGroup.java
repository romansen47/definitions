package definitions.structures.abstr.groups.impl;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.groups.DiscreetGroup;
import definitions.structures.abstr.groups.GroupElement;
import definitions.structures.abstr.groups.MonoidElement;
import definitions.structures.abstr.vectorspaces.RingElement;

public interface FiniteGroup extends DiscreetGroup, FiniteMonoid {

	Map<MonoidElement, Map<MonoidElement, MonoidElement>> operationMap = new HashMap<>();

	@Override
	GroupElement get(Integer representant);

	Map<Integer, RingElement> getElements();

	@Override
	default Map<MonoidElement, Map<MonoidElement, MonoidElement>> getOperationMap() {
		return operationMap;
	}
}
