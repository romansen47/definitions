package definitions.structures.abstr.groups.impl;

import definitions.structures.abstr.vectorspaces.RingElement;

public interface FiniteRingElement extends RingElement, FiniteGroupElement {

	RingElement getMultiplicativeInverseElement();

	void setMultiplicativeInverseElement(RingElement element);

}
