/**
 * 
 */
package definitions.structures.abstr.algebra.rings.impl;

import org.springframework.context.annotation.Configuration;

import definitions.structures.abstr.algebra.groups.DiscreetGroup;
import definitions.structures.abstr.algebra.groups.GroupElement;
import definitions.structures.abstr.algebra.groups.impl.GroupGenerator;
import definitions.structures.abstr.algebra.groups.impl.Int;
import definitions.structures.abstr.algebra.monoids.Monoid;
import definitions.structures.abstr.algebra.monoids.MonoidElement;
import definitions.structures.abstr.vectorspaces.Ring;
import definitions.structures.abstr.vectorspaces.RingElement;

/**
 * @author RoManski
 *
 */
@Configuration
public class Integers implements DiscreetGroup, Ring {

	private static Integers instance;
	private static final long serialVersionUID = 321971307361565421L;

	public static Integers getInstance() {
		if (instance == null) {
			instance = GroupGenerator.getInstance().getIntegers();
		}
		return instance;
	}

	public static void setInstance(final Integers integers) {
		Integers.instance = integers;
	}

	final Int zero;

	final Int one;

	public Integers() {
		this.one = (Int) this.get(1);
		this.zero = (Int) this.get(0);
	}

	@Override
	public boolean divides(final RingElement devisor, final RingElement devident) {
		return !devisor.equals(this.getNeutralElement())
				&& ((Int) devident).getValue() % ((Int) devisor).getValue() == 0;
	}

	@Override
	public synchronized Int get(final Integer int1) {
		final Int i = this.integer();
		i.setValue(int1);
		return i;
	}

	@Override
	public Int getNeutralElement() {
		return this.zero;
	}

	@Override
	public Int getInverseElement(final GroupElement element) {
		return this.get(-((Int) element).getValue());
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
			public MonoidElement operation(final MonoidElement first, final MonoidElement second) {
				return new Int(((Int) first).getValue() * ((Int) second).getValue());
			}

			@Override
			public MonoidElement getNeutralElement() { 
				return Integers.this.get(1);
			}
		};
	}

	@Override
	final public Integer getOrder() {
		return null;
	}
 
	public Int integer() {
		return new Int();
	}

	@Override
	public boolean isIrreducible(final RingElement element) {
		return this.isPrimeElement(element);
	}

	@Override
	public boolean isPrimeElement(final RingElement element) {
		final int n = ((Int) element).getValue();
		if (element.equals(this.getNeutralElement()) || this.isUnit(element)) {
			return false;
		}
		for (int i = 2; i < n; i++) {
			for (int j = 2; j < n; j++) {
				if (i * j == n) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean isUnit(final RingElement element) {
		return element.equals(this.get(-1)) || element.equals(this.get(1));
	}

	@Override
	public Int operation(final MonoidElement first, final MonoidElement second) {
		return this.get(((Int) first).getValue() + ((Int) second).getValue());
	}

	@Override
	public String toString() {
		return "Custom integers group.";
	}

	@Override
	public RingElement getOne() {
		return get(1);
	}
}
