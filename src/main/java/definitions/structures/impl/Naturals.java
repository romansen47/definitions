package definitions.structures.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import definitions.structures.abstr.algebra.monoids.DiscreetMonoid;
import definitions.structures.abstr.algebra.monoids.OrderedMonoid;
import definitions.structures.abstr.algebra.rings.DiscreetSemiRing;
import definitions.structures.abstr.algebra.rings.SemiDomain;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.impl.semigroups.DiscreetSemiGroupImpl;
import settings.GlobalSettings;

@Component
public class Naturals extends DiscreetSemiGroupImpl
		implements DiscreetSemiRing, DiscreetMonoid, OrderedMonoid, SemiDomain {

	@Override
	public String toString() {
		return "the implementation of the ordered discreet semi ring of natural numbers with 0 ";
	}

	public class NaturalNumber implements Element {

		private final int representant;

		public NaturalNumber(Number representant2) {
			representant = representant2.intValue();
		}

		public Integer getRepresentant() {
			return representant;
		}

		@Override
		public boolean equals(Object o) {
			return this == o
					|| (o instanceof NaturalNumber && ((NaturalNumber) o).getRepresentant() == this.getRepresentant());
		}

		@Override
		public int hashCode() {
			return representant;
		}
	}

	DiscreetMonoid multiplicativeMonoid;

	@Override
	public Element getNeutralElement() {
		return get(0.);
	}

	@Override
	public Element operation(Element first, Element second) {
		return get(((NaturalNumber) first).getRepresentant() + ((NaturalNumber) second).getRepresentant());
	}

	@Override
	public Element get(Number representant) {
		return new NaturalNumber(representant) {

			@Override
			public Integer getRepresentant() {
				return representant.intValue();
			}

			@Override
			public boolean equals(Object other) {
				if (other instanceof NaturalNumber) {
					return Math.abs(representant.intValue()
							- ((NaturalNumber) other).getRepresentant()) < GlobalSettings.REAL_EQUALITY_FEINHEIT;
				}
				return false;
			}

			@Override
			public String toString() {
				return String.valueOf(representant);
			}
		};
	}

	@Override
	public DiscreetMonoid getMuliplicativeMonoid() {
		if (multiplicativeMonoid == null) {
			multiplicativeMonoid = new DiscreetMonoid() {

				@Override
				public Element getNeutralElement() {
					return Naturals.this.get(1.);
				}

				@Override
				public Element operation(Element first, Element second) {
					return Naturals.this.get(
							((NaturalNumber) first).getRepresentant() * ((NaturalNumber) second).getRepresentant());
				}

				@Override
				public Element get(Number representant) {
					return Naturals.this.get(representant);
				}

			};
		}
		return multiplicativeMonoid;
	}

	@Override
	public boolean isUnit(Element element) {
		return element.equals(getOne());
	}

	@Override
	public Element getOne() {
		return getMuliplicativeMonoid().getNeutralElement();
	}

	@Override
	public boolean isSmallerThan(Element smallerOne, Element biggerOne) {
		return ((NaturalNumber) biggerOne).getRepresentant() > ((NaturalNumber) smallerOne).getRepresentant();
	}

	@Override
	public boolean divides(Element divisor, Element divident) {
		return ((NaturalNumber) divident).representant % ((NaturalNumber) divisor).getRepresentant() == 0;
	}

	@Override
	public boolean isIrreducible(Element element) {
		return isPrimeElement(element);
	}

	private Map<NaturalNumber, Boolean> primes = new ConcurrentHashMap<>();

	@Override
	public boolean isPrimeElement(Element element) {

		NaturalNumber number = ((NaturalNumber) element);
		if (primes.get(element) == null) {
			for (NaturalNumber i = (NaturalNumber) this.get(2); i.getRepresentant() < ((NaturalNumber) element)
					.getRepresentant(); i = (NaturalNumber) this.addition(i, this.getOne())) {
				if (this.divides(i, element) && this.isPrimeElement(i)) {// - correctly implemented this would be
					// faster.
					primes.put(i, false);
					return false;
				}
			}
			primes.put(number, true);
		}
		return true;

	}

}
