package definitions.structures.abstr.algebra.rings;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.algebra.groups.DiscreetRing;
import definitions.structures.abstr.algebra.groups.FiniteGroup;
import definitions.structures.abstr.algebra.semigroups.Element;

public interface FiniteRing extends FiniteGroup, DiscreetRing {

	default Element getMultiplicativeInverseElement(final Element element) {
		final Element tmp = getMultiplicativeInverseElement(element);
		if (tmp != null) {
			return tmp;
		}
		for (double i = 1; i < this.getOrder(); i++) {
			final Element other = getMuliplicativeMonoid().operation(element, this.get(i));
			if (other.equals(this.get(1.))) {
				return other;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default boolean isUnit(final Element element) {
		return getMultiplicativeInverseElement(element) != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Element operation(final Element first, final Element second) {
		Element ans = FiniteGroup.super.operation(first, second);
		if (ans != null) {
			return ans;
		}
		Map<Element, Element> tmpMap = getOperationMap().get(first);
		if (tmpMap == null) {
			tmpMap = new HashMap<>();
		}
		ans = tmpMap.get(second);
		if (ans != null) {
			return ans;
		}
		ans = FiniteRing.this.getElements()
				.get((getRepresentant(first) + getRepresentant(second)) % FiniteRing.this.getOrder());
		tmpMap.put(second, ans);
		Map<Element, Element> secondTmpMap = getOperationMap().get(second);
		if (secondTmpMap == null) {
			secondTmpMap = new HashMap<>();
		}
		secondTmpMap.put(first, ans);
		getOperationMap().put(first, tmpMap);
		getOperationMap().put(second, secondTmpMap);
		return ans;

	}

}
