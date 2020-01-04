package definitions.structures.abstr.algebra.groups.impl;

import definitions.structures.abstr.vectorspaces.RingElement;

public class Element implements RingElement {

	private static final long serialVersionUID = 1L;
	int representant;

	protected Element(final int r) {
		this.representant = r;
	}

	public int getRepresentant() {
		return this.representant;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toXml() {
		return "<representant>" + this.representant + " </representant>";
	}

}