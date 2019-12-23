package definitions.structures.abstr.groups.impl;

import definitions.structures.abstr.vectorspaces.RingElement;

public interface FiniteRingElement extends RingElement, FiniteGroupElement {
	 
	public void setMultiplicativeInverseElement(RingElement element);
 
	public RingElement getMultiplicativeInverseElement();
	
}
