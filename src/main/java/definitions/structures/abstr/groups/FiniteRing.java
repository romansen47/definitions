package definitions.structures.abstr.groups;

import definitions.structures.abstr.groups.impl.FiniteGroup;
import definitions.structures.abstr.vectorspaces.Ring;
import definitions.structures.abstr.vectorspaces.RingElement;

public interface FiniteRing extends FiniteGroup, Ring {
	
	@Override
	default boolean divides(final RingElement devisor, final RingElement devident) {
		for (int i = 1; i < this.getOrder(); i++) {
			final RingElement tmp = this.get(i);
			if (this.getMuliplicativeMonoid().operation(devisor, tmp).equals(devident)) {
				return true;
			}
		}
		return false;
	}

	@Override
	RingElement get(Integer index);

	@Override
	default boolean isIrreducible(final RingElement element) {
		if (this.isPrimeElement(element)) {
			return true;
		}
		for (int i = 2; i < this.getOrder(); i++) {
			for (int j = 2; j < this.getOrder(); j++) {
				if (this.getMuliplicativeMonoid().operation(this.get(i), this.get(j)).equals(element)) {
					if (!(this.isUnit(this.get(i)) || this.isUnit(this.get(j)))) {
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	default boolean isPrimeElement(final RingElement element) {
		if (element.equals(this.getIdentityElement()) || this.isUnit(element)) {
			return false;
		}
		for (int i = 2; i < this.getOrder(); i++) {
			for (int j = 2; j < this.getOrder(); j++) {
				if (this.divides(element,
						(RingElement) this.getMuliplicativeMonoid().operation(this.get(i), this.get(j)))) {
					if (!this.divides(element, this.get(i)) && !this.divides(element, this.get(j))) {
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	default boolean isUnit(final RingElement element) {
		if (element.equals(this.get(1))) {
			return true;
		}
		for (int i = 1; i < this.getOrder(); i++) {
			final RingElement other = (RingElement) this.getMuliplicativeMonoid().operation(element, this.get(i));
			if (other.equals(this.get(1))) {
				return true;
			}
		}
		return false;
	}

}
