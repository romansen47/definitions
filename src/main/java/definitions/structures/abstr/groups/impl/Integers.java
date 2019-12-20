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

	final GroupElement zero;
	final GroupElement one;

	private static Integers instance;

	public static Integers getInstance() {
		if (instance==null) {
			instance=GroupGenerator.getInstance().getIntegers();
		}
		return instance;
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

//	@Bean(name="int")
//	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public Int integer() {
		return new Int();
	}

	public synchronized RingElement get(Integer int1) {
		Int i = integer();
		i.setValue(int1);
		return i;
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
		return get(-((Int) element).getValue());
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

	public static void setInstance(Integers integers) {
		Integers.instance = integers;
	}

	@Override
	public boolean isIrreducible(RingElement element) {
		return isPrimeElement(element);
	}

	@Override
	public boolean isPrimeElement(RingElement element) {
		int n=((Int) element).getValue();
		if(element.equals(this.getIdentityElement()) || isUnit(element)) {
			return false;
		}
		for(int i=2;i<n;i++) {
			for(int j=2;j<n;j++) {
				if (i*j==n) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean isUnit(RingElement element) {
		return element.equals(get(-1)) || element.equals(get(1));
	}

	@Override
	public boolean divides(RingElement devisor, RingElement devident) {
		return !devisor.equals(getIdentityElement()) && ((Int) devident).getValue() % ((Int) devisor).getValue() == 0;
	}
}
