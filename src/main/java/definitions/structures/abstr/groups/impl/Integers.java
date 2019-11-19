/**
 * 
 */
package definitions.structures.abstr.groups.impl;

import org.springframework.stereotype.Component;

import definitions.structures.abstr.groups.DiscreteGroup;
import definitions.structures.abstr.groups.GroupElement;
import definitions.structures.abstr.groups.Monoid;
import definitions.structures.abstr.groups.MonoidElement;
import definitions.structures.abstr.vectorspaces.Ring;

/**
 * @author RoManski
 *
 */
@Component
public final class Integers implements DiscreteGroup, Ring {

	final GroupElement zero;
	final GroupElement one;

	private static Integers instance = null;

	public static Integers getInstance() {
		if (instance == null) {
			instance = new Integers();
		}
		return instance;
	}

	private Integers() {
		this.one = this.get(1);
		this.zero = this.get(0);
	}

	private static final long serialVersionUID = 321971307361565421L;

	final

	@Override public Integer getOrder() {
		return null;
	}

	@Override
	public GroupElement operation(GroupElement first, GroupElement second) {
		return this.get(((Int) first).getValue() + ((Int) second).getValue());
	}

	public GroupElement get(int int1) {
		return new Int(int1);
	}

	public class Int implements GroupElement {
		private static final long serialVersionUID = -1727262864036395099L;
		final int value;

		Int(int val) {
			this.value = val;
		}

		public int getValue() {
			return this.value;
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
			return "<integer "+Int.this.getValue()+"/>";
		}
	}

	@Override
	public String toString() {
		return "Custom integers group.";
	}

	@Override
	public MonoidElement getIdentityElement() {
		return this.zero;
	}

	@Override
	public GroupElement getInverseElement(GroupElement element) {
		return new Int(-((Int) element).getValue());
	}

	@Override
	public Monoid getMuliplicativeMonoid() {
		return new Monoid() {
			private static final long serialVersionUID = 3746187360827667761L;

			@Override
			public Integer getOrder() {
				return null;
			}

			@Override
			public MonoidElement operation(GroupElement first, GroupElement second) {
				return new Int(((Int) first).getValue() * ((Int) second).getValue());
			}
		};
	}
}
