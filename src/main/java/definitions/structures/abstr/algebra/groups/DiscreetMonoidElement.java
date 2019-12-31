package definitions.structures.abstr.algebra.groups;

import definitions.structures.abstr.algebra.monoids.MonoidElement;

public interface DiscreetMonoidElement extends MonoidElement {

	/**
	 * Elements of finite groups can be indexed by integers
	 * 
	 * @return the integer representant
	 */
	int getRepresentant();

}
