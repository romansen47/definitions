package definitions.structures.abstr.groups.impl;
 
import definitions.structures.abstr.vectorspaces.RingElement;

public class Int implements RingElement {
	private static final long serialVersionUID = -1727262864036395099L;
	private int value;

	Int(int val) {
		this();
		this.value = val;
	}

	Int() {
		this.value = 0;
	}

	public int getValue() {
		return this.value;
	}
	
	public void setValue(int value) {
		this.value=value;
	}

	@Override
	public String toString() {
		return Integer.toString(this.value);
	}

	Int next() {
		return new Int(this.getValue() + 1);
	}

	Int prev() {
		return new Int(this.getValue() - 1);
	}

	public boolean isPrime() {
		if (this.smallestDevisor().getValue() < this.getValue()) {
			return false;
		}
		return true;
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
	public boolean equals(Object o) {
		if (o instanceof Int && ((Int) o).getValue() == this.value) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.value;
	}

	@Override
	public String toXml() {
		return "<integer " + Int.this.getValue() + "/>";
	}
}
