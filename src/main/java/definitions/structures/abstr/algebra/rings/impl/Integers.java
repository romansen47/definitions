/**
 * 
 */
package definitions.structures.abstr.algebra.rings.impl;

import org.springframework.stereotype.Component;

import definitions.structures.abstr.algebra.groups.DiscreetGroup;
import definitions.structures.abstr.algebra.groups.GroupElement;
import definitions.structures.abstr.algebra.groups.impl.GroupGenerator;
import definitions.structures.abstr.algebra.groups.impl.Int;
import definitions.structures.abstr.algebra.monoids.Monoid;
import definitions.structures.abstr.algebra.rings.Domain;
import definitions.structures.abstr.algebra.semigroups.SemiGroupElement;
import definitions.structures.abstr.vectorspaces.RingElement;

/**
 * @author RoManski
 *
 */
@Component
public class Integers implements DiscreetGroup, Domain {

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
		this.one = this.get(1);
		this.zero = this.get(0);
	}

	@Override
	public boolean divides(final RingElement devisor, final RingElement devident) {
		return !devisor.equals(this.getNeutralElement())
				&& ((Int) devident).getRepresentant() % ((Int) devisor).getRepresentant() == 0;
	}

	@Override
	public synchronized Int get(final Integer int1) {
		final Int i = this.integer();
		i.setValue(int1);
		return i;
	}

	@Override
	public Int getInverseElement(final GroupElement element) {
		return this.get(-((Int) element).getRepresentant());
	}

	@Override
	public Monoid getMuliplicativeMonoid() {
		return new Monoid() {
			private static final long serialVersionUID = 3746187360827667761L;

			@Override
			public Int getNeutralElement() {
				return Integers.this.get(1);
			}

			@Override
			public Integer getOrder() {
				return null;
			}

			@Override
			public Int operation(final SemiGroupElement first, final SemiGroupElement second) {
				return new Int(((Int) first).getRepresentant() * ((Int) second).getRepresentant());
			}

		};
	}

	@Override
	public Int getNeutralElement() {
		return this.zero;
	}

	@Override
	public Int getOne() {
		return this.get(1);
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
		final int n = ((Int) element).getRepresentant();
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
	public Int operation(final SemiGroupElement first, final SemiGroupElement second) {
		return this.get(((Int) first).getRepresentant() + ((Int) second).getRepresentant());
	}

	@Override
	public String toString() {
		return "Custom ring of integers.";
	}
}
