/**
 * 
 */
package definitions.structures.abstr.algebra.rings.impl;

import org.springframework.stereotype.Component;

import definitions.structures.abstr.algebra.groups.DiscreetGroup;
import definitions.structures.abstr.algebra.groups.impl.Int;
import definitions.structures.abstr.algebra.monoids.Monoid;
import definitions.structures.abstr.algebra.rings.Domain;
import definitions.structures.abstr.algebra.semigroups.Element;

/**
 * @author RoManski
 *
 */
@Component
public class Integers implements DiscreetGroup, Domain {

	private static Integers instance;
	private static final long serialVersionUID = 321971307361565421L;

	final Int zero;
	final Int one;

	public static Integers getInstance() {
		if (instance == null) {
			instance = new Integers();
		}
		return instance;
	}

	public Integers() {
		this.one = this.get(1);
		this.zero = this.get(0);
	}

	@Override
	public boolean divides(final Element devisor, final Element devident) {
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
	public Int getInverseElement(final Element element) {
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
			public Int operation(final Element first, final Element second) {
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
	public boolean isIrreducible(final Element element) {
		return this.isPrimeElement(element);
	}

	@Override
	public boolean isPrimeElement(final Element element) {
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
	public boolean isUnit(final Element element) {
		return element.equals(this.get(-1)) || element.equals(this.get(1));
	}

	@Override
	public Int operation(final Element first, final Element second) {
		return this.get(((Int) first).getRepresentant() + ((Int) second).getRepresentant());
	}

	@Override
	public String toString() {
		return "Custom ring of integers.";
	}
}
