package definitions.structures.abstr.groups.impl;

import definitions.structures.abstr.groups.GroupElement;
import definitions.structures.abstr.vectorspaces.RingElement;

public interface FiniteGroupElement extends GroupElement {

	/**
	 * Elements of finite groups carry information about the corresponding inverse
	 * elements
	 * 
	 * @return
	 */
	RingElement getInverseElement();

	/**
	 * Elements of finite groups can be indexed by integers
	 * 
	 * @return the integer representant
	 */
	int getRepresentant();

	/**
	 * setter for inverse element
	 * 
	 * @param element the inverse element
	 */
	void setInverseElement(RingElement element);

}
