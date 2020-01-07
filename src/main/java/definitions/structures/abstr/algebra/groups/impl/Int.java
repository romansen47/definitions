package definitions.structures.abstr.algebra.groups.impl;

import org.springframework.stereotype.Component;

import definitions.settings.XmlPrintable;
import definitions.structures.abstr.algebra.semigroups.Element;

@Component(value = "int")
public class Int implements Element,XmlPrintable {

	private static final long serialVersionUID = -1727262864036395099L;

	private int representant;

	public Int() {
		this.representant = 0;
	}

	public Int(final int val) {
		this();
		this.representant = val;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object o) {
		if (o instanceof Int && ((Int) o).getRepresentant() == this.getRepresentant()) {
			return true;
		}
		return false;
	}

	public Integer getRepresentant() {
		return this.representant;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return this.getRepresentant();
	}

	public boolean isPrime() {
		if (this.smallestDevisor().getRepresentant() < this.getRepresentant()) {
			return false;
		}
		return true;
	}

	Int next() {
		return new Int(this.getRepresentant() + 1);
	}

	Int prev() {
		return new Int(this.getRepresentant() - 1);
	}

	public void setValue(final int value) {
		this.representant = value;
	}

	private Int smallestDevisor() {
		int index = 1;
		int smallestDevisor = 2;
		while (index * smallestDevisor <= this.getRepresentant()) {
			if (this.getRepresentant() % smallestDevisor == 0) {
				return new Int(smallestDevisor);
			} else {
				index = smallestDevisor;
				smallestDevisor += 1;
			}
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return Integer.toString(this.getRepresentant());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toXml() {
		return "<integer>" + Int.this.getRepresentant() + "</integer>";
	}
}
