package definitions.structures.abstr.algebra.rings.impl;

import definitions.structures.abstr.algebra.rings.FiniteRingElement;
import definitions.structures.abstr.vectorspaces.RingElement;

public class FiniteResidueClassElement implements FiniteRingElement {

	private static final long serialVersionUID = 1L;
	int representant;

	private Boolean isPrime;
	private Boolean isUnit;
	private Boolean isIrreducible;
	private RingElement inverseElement;
	private RingElement multiplicativeInverseElement;

	protected FiniteResidueClassElement(final int r) {
		this.representant = r;
	}

	@Override
	public RingElement getInverseElement() {
		return this.inverseElement;
	}

	@Override
	public RingElement getMultiplicativeInverseElement() {
		return this.multiplicativeInverseElement;
	}

	@Override
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

	@Override
	public void setInverseElement(final RingElement element) {
		this.inverseElement = element;
	}

	public void setIrreducible(final Boolean isReducible) {
		this.isIrreducible = isReducible;
	}

	public void setIsUnit(final Boolean isUnit) {
		this.isUnit = isUnit;
	}

	@Override
	public void setMultiplicativeInverseElement(final RingElement element) {
		this.multiplicativeInverseElement = element;
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
