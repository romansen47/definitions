/**
 * 
 */
package definitions.structures.abstr.groups.impl;

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
@Configuration
public class Integers implements DiscreteGroup, Ring {

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

	final GroupElement zero;

	final GroupElement one;

	public Integers() {
		this.one = this.get(1);
		this.zero = this.get(0);
	}

	@Override
	public boolean divides(final RingElement devisor, final RingElement devident) {
		return !devisor.equals(this.getIdentityElement())
				&& ((Int) devident).getValue() % ((Int) devisor).getValue() == 0;
	}

	@Override
	public synchronized RingElement get(final Integer int1) {
		final Int i = this.integer();
		i.setValue(int1);
		return i;
	}

	@Override
	public MonoidElement getIdentityElement() {
		return this.zero;
	}

	@Override
	public GroupElement getInverseElement(final GroupElement element) {
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
			public MonoidElement operation(final GroupElement first, final GroupElement second) {
				return new Int(((Int) first).getValue() * ((Int) second).getValue());
			}
		};
	}

	@Override
	final public Integer getOrder() {
		return null;
	}

	// @Bean(name="int")
	// @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
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
		if (element.equals(this.getIdentityElement()) || this.isUnit(element)) {
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
	public RingElement operation(final GroupElement first, final GroupElement second) {
		return this.get(((Int) first).getValue() + ((Int) second).getValue());
	}

	@Override
	public String toString() {
		return "Custom integers group.";
	}
}
