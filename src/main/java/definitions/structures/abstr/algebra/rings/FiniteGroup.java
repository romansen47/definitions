package definitions.structures.abstr.algebra.rings;

import java.util.Map;

import definitions.structures.abstr.algebra.groups.Group;
import definitions.structures.abstr.algebra.monoids.FiniteMonoid;
import definitions.structures.abstr.algebra.semigroups.Element;

public interface FiniteGroup extends FiniteMonoid, Group {

	default Element getInverseElement(Element element) {
		Map<Element,Element> operations=getOperationMap().get(element);
		for(double i=1;i<getOrder();i++) {
			Element ans=operations.get(get(i));
			if (ans.equals(getNeutralElement())) {
				return ans;
			}
		}
		return null;
	}
	
}
