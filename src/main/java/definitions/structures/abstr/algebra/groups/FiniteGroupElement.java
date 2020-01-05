package definitions.structures.abstr.algebra.groups;

import definitions.structures.abstr.algebra.monoids.FiniteMonoidElement;
import definitions.structures.abstr.vectorspaces.RingElement;

public interface FiniteGroupElement extends GroupElement, FiniteMonoidElement {

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
