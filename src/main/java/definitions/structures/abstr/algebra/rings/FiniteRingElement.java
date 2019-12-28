package definitions.structures.abstr.algebra.rings;

import definitions.structures.abstr.algebra.groups.FiniteGroupElement;
import definitions.structures.abstr.vectorspaces.RingElement;

public interface FiniteRingElement extends RingElement, FiniteGroupElement {

	RingElement getMultiplicativeInverseElement();

	void setMultiplicativeInverseElement(RingElement element);

}
