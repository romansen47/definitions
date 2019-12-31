package definitions.structures.abstr.algebra.groups;

import definitions.structures.abstr.vectorspaces.RingElement;

public interface FiniteGroupElement extends GroupElement, DiscreetMonoidElement {

	/**
	 * Elements of finite groups carry information about the corresponding inverse
	 * elements
	 * 
	 * @return
	 */
	RingElement getInverseElement();

	/**
	 * setter for inverse element
	 * 
	 * @param element the inverse element
	 */
	void setInverseElement(RingElement element);

}
