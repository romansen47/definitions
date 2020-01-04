package definitions.structures.abstr.algebra.rings;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.algebra.groups.FiniteGroup;
import definitions.structures.abstr.algebra.monoids.MonoidElement;
import definitions.structures.abstr.vectorspaces.Ring;
import definitions.structures.abstr.vectorspaces.RingElement;

public interface FiniteRing extends FiniteGroup, Ring {

	/**
	 * {@inheritDoc}
	 */
	@Override
	FiniteRingElement get(Integer index);

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

	/**
	 * {@inheritDoc}
	 */
	@Override
	default boolean isUnit(final RingElement element) {
		return this.getMultiplicativeInverseElement((FiniteRingElement) element) != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default FiniteRingElement operation(final MonoidElement first, final MonoidElement second) {
		Map<MonoidElement, MonoidElement> tmpMap = this.getOperationMap().get(first);
		if (tmpMap == null) {
			tmpMap = new HashMap<>();
		}
		MonoidElement ans = tmpMap.get(second);
		if (ans != null) {
			return (FiniteRingElement) ans;
		}
		ans = FiniteRing.this.getElements()
				.get((((FiniteRingElement) first).getRepresentant() + ((FiniteRingElement) second).getRepresentant())
						% FiniteRing.this.getOrder());
		if (ans == FiniteRing.this.getNeutralElement()) {
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
		return (FiniteRingElement) ans;

	}

}
