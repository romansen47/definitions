package definitions.structures.abstr.algebra.semigroups.impl;

import definitions.structures.abstr.algebra.groups.impl.Int;
import definitions.structures.abstr.algebra.semigroups.DiscreetSemiGroupElement;

public class NaturalNumber extends Int implements DiscreetSemiGroupElement {

	private static final long serialVersionUID = 1L;
	private final int representant;

	public NaturalNumber(final int representant) {
		this.representant = representant;
	}

	/**
	 * @return the representant
	 */
	@Override
	public int getRepresentant() {
		return this.representant;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toXml() {
		return "<natural>" + String.valueOf(this.getRepresentant()) + "</natural>";
	}
}
