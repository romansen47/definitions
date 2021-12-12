package definitions.structures.impl;

import org.springframework.stereotype.Component;

import definitions.structures.abstr.algebra.monoids.DiscreetMonoid;
import definitions.structures.abstr.algebra.monoids.OrderedMonoid;
import definitions.structures.abstr.algebra.rings.DiscreetSemiRing;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.impl.semigroups.DiscreetSemiGroupImpl;
import settings.GlobalSettings;

@Component
public class Naturals extends DiscreetSemiGroupImpl implements DiscreetSemiRing, DiscreetMonoid, OrderedMonoid {

	@Override
	public String toString() {
		return "the implementation of the ordered discreet semi ring of natural numbers with 0 ";
	}

	class NaturalNumber implements Element {

		private final double representant;

		public NaturalNumber(Double representant2) {
			representant = representant2;
		}

		public Double getRepresentant() {
			return representant;
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
	public Element get(Double representant) {
		return new NaturalNumber(representant) {
			@Override
			public Double getRepresentant() {
				if (representant < 0.0) {
					System.out.println("natural number must not be smaller 0!");
				}
				return representant;
			}

			@Override
			public boolean equals(Object other) {
				if (other instanceof NaturalNumber) {
					return Math.abs(representant
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
				public Element get(Double representant) {
					return Naturals.this.get(representant);
				}

			};
		}
		return multiplicativeMonoid;
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

}
