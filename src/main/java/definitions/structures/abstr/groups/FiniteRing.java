package definitions.structures.abstr.groups;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.groups.impl.FiniteGroup;
import definitions.structures.abstr.groups.impl.FiniteMonoid;
import definitions.structures.abstr.groups.impl.FiniteRingElement;
import definitions.structures.abstr.vectorspaces.Ring;
import definitions.structures.abstr.vectorspaces.RingElement;

public interface FiniteRing extends FiniteGroup, Ring {

	@Override
	default boolean divides(final RingElement devisor, final RingElement devident) {
		return this.getCoFactor(devisor, devident) != null;
	}

	@Override
	RingElement get(Integer index);

	default RingElement getCoFactor(final RingElement devisor, final RingElement devident) {
		for (final MonoidElement el : ((FiniteMonoid) this.getMuliplicativeMonoid()).getOperationMap().get(devisor)
				.keySet()) {
			if (((FiniteMonoid) this.getMuliplicativeMonoid()).operation(devisor, (GroupElement) el).equals(devident)) {
				return (RingElement) el;
			}
		}
//		for (int i = 1; i < this.getOrder(); i++) {
//			final RingElement tmp = this.get(i);
//			if (this.getMuliplicativeMonoid().operation(devisor, tmp).equals(devident)) {
//				return tmp;
//			}
//		}
		return null;
	}

	default FiniteRingElement getMultiplicativeInverseElement(final FiniteRingElement element) {
		final FiniteRingElement tmp = (FiniteRingElement) element.getMultiplicativeInverseElement();
		if (tmp != null) {
			return tmp;
		}
		if (element.equals(this.get(0))) {
			element.setMultiplicativeInverseElement(null);
			return null;
		}
		if (element.equals(this.get(1))) {
			element.setMultiplicativeInverseElement(element);
			return element;
		}
		for (int i = 1; i < this.getOrder(); i++) {
			final FiniteRingElement other = (FiniteRingElement) this.getMuliplicativeMonoid().operation(element,
					this.get(i));
			if (other.equals(this.get(1))) {
				element.setMultiplicativeInverseElement(other);
				other.setMultiplicativeInverseElement(element);
				return other;
			}
		}
		return null;
	}

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
						(FiniteRingElement) this.getMuliplicativeMonoid().operation(this.get(i), this.get(j)))) {
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
		return this.getMultiplicativeInverseElement((FiniteRingElement) element) != null;
	}

	@Override
	default MonoidElement operation(final MonoidElement first, final MonoidElement second) {
		Map<MonoidElement, MonoidElement> tmpMap = this.getOperationMap().get(first);
		if (tmpMap == null) {
			tmpMap = new HashMap<>();
		}
		MonoidElement ans = tmpMap.get(second);
		if (ans != null) {
			return ans;
		}
		ans = FiniteRing.this.getElements()
				.get((((FiniteRingElement) first).getRepresentant() + ((FiniteRingElement) second).getRepresentant())
						% FiniteRing.this.getOrder());
		if (ans == FiniteRing.this.getIdentityElement()) {
			if (((FiniteRingElement) first).getInverseElement() == null) {
				((FiniteRingElement) first).setInverseElement((RingElement) second);
				if (((FiniteRingElement) second).getInverseElement() == null) {
					((FiniteRingElement) second).setInverseElement((RingElement) first);
				}
			}
		}
		tmpMap.put(second, ans);
		Map<MonoidElement, MonoidElement> secondTmpMap = this.getOperationMap().get(second);
		if (secondTmpMap == null) {
			secondTmpMap = new HashMap<>();
		}
		secondTmpMap.put(first, ans);
		this.getOperationMap().put(first, tmpMap);
		this.getOperationMap().put(second, secondTmpMap);
		return ans;

	}

}
