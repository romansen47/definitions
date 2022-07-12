package definitions.structures.impl.semigroups;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.algebra.semigroups.FiniteSemiGroup;

public abstract class FiniteSemiGroupImpl extends DiscreetSemiGroupImpl implements FiniteSemiGroup {

	Map<Double, Element> elements = new ConcurrentHashMap<>();
	Map<Element, Map<Element, Element>> operationMap;

	@Override
	public Element get(Number representant) {
		return this.elements.get(representant);
	}

	@Override
	public Map<Double, Element> getElements() {
		return this.elements;
	}

	@Override
	public Map<Element, Map<Element, Element>> getOperationMap() {
		return this.operationMap;
	}

	@Override
	public Element operation(Element first, Element second) {
		return this.operationMap.get(first).get(second);
	}

}
