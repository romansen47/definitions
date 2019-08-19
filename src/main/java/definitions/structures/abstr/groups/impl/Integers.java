/**
 * 
 */
package definitions.structures.abstr.groups.impl;

import definitions.structures.abstr.groups.DiscreteGroup;
import definitions.structures.abstr.groups.GroupElement;

/**
 * @author RoManski
 *
 */
public final class Integers implements DiscreteGroup {

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

		int getValue() {
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
	}

	@Override
	public String toString() {
		return "Custom integers group.";
	}
}
