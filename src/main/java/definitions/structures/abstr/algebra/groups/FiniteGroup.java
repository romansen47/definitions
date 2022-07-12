package definitions.structures.abstr.algebra.groups;

import java.util.Map;

import definitions.structures.abstr.algebra.monoids.FiniteMonoid;
import definitions.structures.abstr.algebra.semigroups.Element;

/**
 * A finite group is a group with finite amount of elements
 *
 * @author ro
 */
public interface FiniteGroup extends FiniteMonoid, DiscreetGroup {

	@Override
	default Element getInverseElement(Element element) {
		final Map<Element, Element> operations = this.getOperationMap().get(element);
		for (double i = 1; i < this.getOrder(); i++) {
			final Element ans = operations.get(this.get(i));
			if (ans.equals(this.getNeutralElement())) {
				return ans;
			}
		}
		return null;
	}

}
