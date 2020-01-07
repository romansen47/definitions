package definitions.structures.abstr.algebra.rings;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.vectorspaces.Ring; 

public interface FiniteRing extends FiniteGroup, Ring {


	/**
	 * Getter for the identity element
	 * 
	 * @return the neutral element of the semi group
	 */
	@Override
	Element getNeutralElement();
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	Element get(Integer index);

	default Element getMultiplicativeInverseElement(final Element element) {
		final Element tmp = getMultiplicativeInverseElement((Element) element);
		if (tmp != null) {
			return tmp;
		}
		for (int i = 1; i < this.getOrder(); i++) {
			final Element other = (Element) this.getMuliplicativeMonoid().operation(element,
					this.get(i));
			if (other.equals(this.get(1))) {
				return (Element) other;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default boolean isUnit(final Element element) {
		return this.getMultiplicativeInverseElement((Element) element) != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Element operation(final Element first, final Element second) {
		Map<Element, Element> tmpMap = this.getOperationMap().get(first);
		if (tmpMap == null) {
			tmpMap = new HashMap<>();
		}
		Element ans = tmpMap.get(second);
		if (ans != null) {
			return (Element) ans;
		}
		ans = FiniteRing.this.getElements()
				.get((getRepresentant(first) + getRepresentant(second))
						% FiniteRing.this.getOrder());
//		if (((Element) ans).equals((Element) FiniteRing.this.getNeutralElement())) {
//			if (getInverseElement(first) == null) {
//				if (!((Element) second)
//						.equals((Element) FiniteRing.this.getNeutralElement())) { 
//					if (getInverseElement(second) == null) {
//						setInverseElement((Element) second,(Element) first);
//					}
//				}
//			}
//		}
		tmpMap.put(second, ans);
		Map<Element, Element> secondTmpMap = this.getOperationMap().get(second);
		if (secondTmpMap == null) {
			secondTmpMap = new HashMap<>();
		}
		secondTmpMap.put(first, ans);
		this.getOperationMap().put(first, tmpMap);
		this.getOperationMap().put(second, secondTmpMap);
		return (Element) ans;

	}

}
