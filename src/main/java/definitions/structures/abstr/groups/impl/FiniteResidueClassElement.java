package definitions.structures.abstr.groups.impl;

import definitions.structures.abstr.vectorspaces.RingElement;

public class FiniteResidueClassElement implements RingElement {

	private static final long serialVersionUID = 1L;
	int representant;
	
	private Boolean isPrime;
	private Boolean isUnit;
	private Boolean isIrreducible;

	public Boolean isPrime() {
		return isPrime;
	}

	public void setPrime(boolean isPrime) {
		this.isPrime = isPrime;
	}

	protected FiniteResidueClassElement(int r) {
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

	public Boolean isUnit() {
		return isUnit;
	}

	public void setIsUnit(Boolean isUnit) {
		this.isUnit = isUnit;
	}

	public Boolean isIrreducible() {
		return isIrreducible;
	}

	public void setIrreducible(Boolean isReducible) {
		this.isIrreducible = isReducible;
	}

}
