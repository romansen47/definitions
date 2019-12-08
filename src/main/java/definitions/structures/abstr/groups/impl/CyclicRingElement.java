package definitions.structures.abstr.groups.impl;

import definitions.structures.abstr.vectorspaces.RingElement;

public class CyclicRingElement implements RingElement {

	private static final long serialVersionUID = 1L;
	int representant;

	protected CyclicRingElement(int r) {
		representant = r;
	}

	@Override
	public String toString() {
		return toXml();
	}
	
	@Override
	public String toXml() {
		return "<representant>" + representant + "</representant>";
	}

	public int getRepresentant() {
		return representant;
	}

}
