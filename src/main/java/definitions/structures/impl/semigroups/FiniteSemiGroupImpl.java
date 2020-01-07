package definitions.structures.impl.semigroups;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.algebra.semigroups.FiniteSemiGroup;

public abstract class FiniteSemiGroupImpl extends DiscreetSemiGroupImpl implements FiniteSemiGroup{

	Map<Integer, Element> elements=new HashMap<>();
	Map<Element, Map<Element, Element>> operationMap;
	
	@Override
	public Element get(Integer representant) {
		return elements.get(representant);
	}

	@Override
	public Map<Integer, Element> getElements() { 
		return elements;
	}

	@Override
	public Map<Element, Map<Element, Element>> getOperationMap() { 
		return operationMap;
	}

	@Override
	public Element operation(Element first, Element second) { 
		return operationMap.get(first).get(second);
	}

}
