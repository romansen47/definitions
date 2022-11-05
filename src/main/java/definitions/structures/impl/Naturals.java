package definitions.structures.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import definitions.structures.abstr.algebra.fields.RepresentableElement;
import definitions.structures.abstr.algebra.monoids.DiscreetMonoid;
import definitions.structures.abstr.algebra.monoids.OrderedMonoid;
import definitions.structures.abstr.algebra.rings.DiscreetSemiRing;
import definitions.structures.abstr.algebra.rings.SemiDomain;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.impl.semigroups.DiscreetSemiGroupImpl;
import settings.GlobalSettings;

@Service
public class Naturals extends DiscreetSemiGroupImpl
		implements DiscreetSemiRing, DiscreetMonoid, OrderedMonoid, SemiDomain {

	@Override
	public String toString() {
		return "the implementation of the ordered discreet semi ring of natural numbers with 0 ";
	}

	public class NaturalNumber implements RepresentableElement {

		private final int representant;

		public NaturalNumber(Number representant2) {
			this.representant = representant2.intValue();
		}

		@Override
		public Integer getRepresentant() {
			return this.representant;
		}

		@Override
		public boolean equals(Object o) {
			return this == o
					|| (o instanceof NaturalNumber && ((NaturalNumber) o).getRepresentant() == this.getRepresentant());
		}

		@Override
		public int hashCode() {
			return this.representant;
		}
	}

	DiscreetMonoid multiplicativeMonoid;

	@Override
	public Element getNeutralElement() {
		return this.get(0.);
	}

	@Override
	public Element operation(Element first, Element second) {
		return this.get(((NaturalNumber) first).getRepresentant() + ((NaturalNumber) second).getRepresentant());
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
		if (this.multiplicativeMonoid == null) {
			this.multiplicativeMonoid = new DiscreetMonoid() {

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
		return this.multiplicativeMonoid;
	}

	@Override
	public boolean isUnit(Element element) {
		return element.equals(this.getOne());
	}

	@Override
	public Element getOne() {
		return this.getMuliplicativeMonoid().getNeutralElement();
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
		return this.isPrimeElement(element);
	}

	private Map<NaturalNumber, Boolean> primes = new ConcurrentHashMap<>();

	@Override
	public boolean isPrimeElement(Element element) {

		NaturalNumber number = ((NaturalNumber) element);
		if (this.primes.get(element) == null) {
			for (NaturalNumber i = (NaturalNumber) this.get(2); i.getRepresentant() < ((NaturalNumber) element)
					.getRepresentant(); i = (NaturalNumber) this.addition(i, this.getOne())) {
				if (this.divides(i, element) && this.isPrimeElement(i)) {// - correctly implemented this would be
					// faster.
					this.primes.put(i, false);
					return false;
				}
			}
			this.primes.put(number, true);
		}
		return true;

	}

}
