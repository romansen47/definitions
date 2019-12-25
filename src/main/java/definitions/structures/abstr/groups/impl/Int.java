package definitions.structures.abstr.groups.impl;

import org.springframework.stereotype.Component;

import definitions.structures.abstr.vectorspaces.RingElement;

@Component(value = "int")
public class Int implements RingElement {

	private static final long serialVersionUID = -1727262864036395099L;

	private int value;

	public Int() {
		this.value = 0;
	}

	Int(final int val) {
		this();
		this.value = val;
	}

	@Override
	public boolean equals(final Object o) {
		if (o instanceof Int && ((Int) o).getValue() == this.getValue()) {
			return true;
		}
		return false;
	}

	public int getValue() {
		return this.value;
	}

	@Override
	public int hashCode() {
		return this.getValue();
	}

	public boolean isPrime() {
		if (this.smallestDevisor().getValue() < this.getValue()) {
			return false;
		}
		return true;
	}

	Int next() {
		return new Int(this.getValue() + 1);
	}

	Int prev() {
		return new Int(this.getValue() - 1);
	}

	public void setValue(final int value) {
		this.value = value;
	}

	private Int smallestDevisor() {
		int index = 1;
		int smallestDevisor = 2;
		while (index * smallestDevisor <= this.getValue()) {
			if (this.getValue() % smallestDevisor == 0) {
				return new Int(smallestDevisor);
			} else {
				index = smallestDevisor;
				smallestDevisor += 1;
			}
		}
		return this;
	}

	@Override
	public String toString() {
		return Integer.toString(this.getValue());
	}

	@Override
	public String toXml() {
		return "<integer " + Int.this.getValue() + "/>";
	}
}
