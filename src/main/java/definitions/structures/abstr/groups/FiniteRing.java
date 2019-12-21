package definitions.structures.abstr.groups;

import definitions.structures.abstr.groups.impl.FiniteGroup;
import definitions.structures.abstr.vectorspaces.Ring;
import definitions.structures.abstr.vectorspaces.RingElement;

public interface FiniteRing extends FiniteGroup, Ring {

	@Override
	RingElement get(Integer index);

	@Override
	default boolean isIrreducible(RingElement element) {
		if(isPrimeElement(element)) {
			return true;
		}
		for (int i=2;i<getOrder();i++) {
			for (int j=2;j<getOrder();j++) {
				if (getMuliplicativeMonoid().operation(get(i), get(j)).equals(element)){
					if (!(isUnit(get(i)) || isUnit(get(j)))) {
						return false;
					}
				}
			}
		}
		return true;
	};

	default boolean isPrimeElement(RingElement element) {
		if (element.equals(getIdentityElement()) || isUnit(element)) {
			return false;
		}
		for (int i=2;i<getOrder();i++) {
			for (int j=2;j<getOrder();j++) {
				if (divides(element,(RingElement) getMuliplicativeMonoid().operation(get(i), get(j)))){
					if(!divides(element,get(i)) && !divides(element,get(j))) {
						return false;
					}
				}
			}
		}
		return true;
	};

	@Override
	default boolean isUnit(RingElement element) { 
		if (element.equals(get(1))) {
			return true;
		} 
		for (int i = 1; i < getOrder(); i++) {
			RingElement other = (RingElement) getMuliplicativeMonoid().operation(element, get(i));
			if (other.equals(get(1))) {
				return true;
			}
		}
		return false;
	}

	default boolean divides(RingElement devisor, RingElement devident) {
		for (int i = 1; i < getOrder(); i++) {
			RingElement tmp = get(i);
			if (getMuliplicativeMonoid().operation(devisor, get(i)).equals(devident)) {
				return true;
			}
		}
		return false;
	}

}
