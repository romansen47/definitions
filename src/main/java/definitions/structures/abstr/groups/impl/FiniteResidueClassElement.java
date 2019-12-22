package definitions.structures.abstr.groups.impl;

import definitions.structures.abstr.vectorspaces.RingElement;

public class FiniteResidueClassElement implements RingElement {

	private static final long serialVersionUID = 1L;
	int representant;

	private Boolean isPrime;
	private Boolean isUnit;
	private Boolean isIrreducible;

	protected FiniteResidueClassElement(final int r) {
		this.representant = r;
	}

	public int getRepresentant() {
		return this.representant;
	}

	public Boolean isIrreducible() {
		return this.isIrreducible;
	}

	public Boolean isPrime() {
		return this.isPrime;
	}

	public Boolean isUnit() {
		return this.isUnit;
	}

	public void setIrreducible(final Boolean isReducible) {
		this.isIrreducible = isReducible;
	}

	public void setIsUnit(final Boolean isUnit) {
		this.isUnit = isUnit;
	}

	public void setPrime(final boolean isPrime) {
		this.isPrime = isPrime;
	}

	@Override
	public String toString() {
		return this.toXml();
	}

	@Override
	public String toXml() {
		return "<representant>" + this.representant + "</representant>";
	}

}
