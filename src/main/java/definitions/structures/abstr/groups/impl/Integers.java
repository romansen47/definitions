/**
 * 
 */
package definitions.structures.abstr.groups.impl;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import definitions.structures.abstr.groups.DiscreteGroup;
import definitions.structures.abstr.groups.GroupElement;
import definitions.structures.abstr.groups.Monoid;
import definitions.structures.abstr.groups.MonoidElement;
import definitions.structures.abstr.vectorspaces.Ring;
import definitions.structures.abstr.vectorspaces.RingElement;

/**
 * @author RoManski
 *
 */

@Configurable
@Configuration
@ComponentScan
public class Integers implements DiscreteGroup, Ring {

	final GroupElement zero;
	final GroupElement one;

	private static Integers instance;

	public static Integers getInstance() {
		if (instance == null) {
			instance = integers();
		}
		return instance;
	}

	@Bean
	public static Integers integers() {
		return new Integers();
	}

	public Integers() {
		this.one = this.get(1);
		this.zero = this.get(0);
	}

	private static final long serialVersionUID = 321971307361565421L;

	@Override
	final public Integer getOrder() {
		return null;
	}

	@Override
	public RingElement operation(GroupElement first, GroupElement second) {
		return (RingElement) this.get(((Int) first).getValue() + ((Int) second).getValue());
	}

	@Bean
	public Int integer() {
		return new Int();
	}

	public GroupElement get(int int1) {
		Int i= integer();
		i.setValue(int1);
		return i;
	}

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
