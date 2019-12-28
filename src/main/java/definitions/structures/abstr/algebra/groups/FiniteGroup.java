package definitions.structures.abstr.algebra.groups;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.algebra.monoids.FiniteMonoid;
import definitions.structures.abstr.algebra.monoids.MonoidElement;
import definitions.structures.abstr.vectorspaces.RingElement;

public interface FiniteGroup extends DiscreetGroup, FiniteMonoid {

	Map<MonoidElement, Map<MonoidElement, MonoidElement>> operationMap = new HashMap<>();

	@Override
	FiniteGroupElement get(Integer representant);

	Map<Integer, FiniteGroupElement> getElements();

	@Override
	default Map<MonoidElement, Map<MonoidElement, MonoidElement>> getOperationMap() {
		return operationMap;
	}
}
