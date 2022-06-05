package definitions.structures.abstr.algebra.groups;

import java.util.List;
import java.util.Map;

import definitions.structures.abstr.algebra.monoids.FiniteMonoid;
import definitions.structures.abstr.algebra.semigroups.Element;

/**
 * @author ro
 *
 *         A cyclic group is a group is a group (here finite) that is isomorphic
 *         to Z\mZ, where m is the order of the group.
 *
 */
public interface CyclicGroup extends Group, FiniteMonoid {

	List<Element> getElementsAsList();

	@Override
	Map<Double, Element> getElements();

	@Override
	default Element get(Number representant) {
		return getElementsAsList().get((Integer) representant);
	}

	@Override
	default Element getNeutralElement() {
		return getElementsAsList().get(0);
	}

}
