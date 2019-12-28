package definitions.structures.abstr.algebra.semigroups.impl;

import definitions.structures.abstr.algebra.groups.DiscreetMonoidElement;

public class NaturalNumber implements DiscreetMonoidElement {
 
	private static final long serialVersionUID = 1L;
	private final int representant;
	
	public NaturalNumber(int representant) {
		this.representant=representant;
	}

	/**
	 * @return the representant
	 */
	public int getRepresentant() {
		return representant;
	}
}
