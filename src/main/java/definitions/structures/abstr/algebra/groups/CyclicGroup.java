package definitions.structures.abstr.algebra.groups;

import java.util.List;
import java.util.Map;

import definitions.structures.abstr.algebra.monoids.FiniteMonoid;
import definitions.structures.abstr.algebra.semigroups.Element;

public interface CyclicGroup extends Group, FiniteMonoid {

	List<Element> getElementsAsList();

	@Override
	Map<Double, Element> getElements();

	@Override
	default Element get(Number representant) {
		return getElementsAsList().get((Integer)representant);
	}

	@Override
	default Element getNeutralElement() {
		return getElementsAsList().get(0);
	}

}
