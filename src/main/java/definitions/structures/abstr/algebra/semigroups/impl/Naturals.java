package definitions.structures.abstr.algebra.semigroups.impl;

import definitions.settings.XmlPrintable;
import definitions.structures.abstr.algebra.monoids.DiscreetMonoid;
import definitions.structures.abstr.algebra.monoids.MonoidElement;
import definitions.structures.abstr.algebra.monoids.OrderedMonoid;
import definitions.structures.abstr.algebra.semigroups.SemiGroup;

public class Naturals implements SemiGroup, OrderedMonoid, DiscreetMonoid, XmlPrintable {

	private static final long serialVersionUID = 1L;
	private static OrderedMonoid instance;

	public static OrderedMonoid getInstance() {
		if (instance == null) {
			instance = new Naturals();
		}
		return instance;
	}

	@Override
	public NaturalNumber get(final Integer representant) {
		return new NaturalNumber(representant);
	}

	@Override
	public NaturalNumber getNeutralElement() {
		return null;
	}

	@Override
	public Integer getOrder() {
		return null;
	}

	@Override
	public boolean isHigher(final MonoidElement smallerOne, final MonoidElement biggerOne) {
		return ((NaturalNumber) smallerOne).getRepresentant() < ((NaturalNumber) biggerOne).getRepresentant();
	}

	@Override
	public NaturalNumber operation(final MonoidElement first, final MonoidElement second) {
		return this.get(((NaturalNumber) first).getRepresentant() + ((NaturalNumber) second).getRepresentant());
	}

	@Override
	public String toXml() {
		return "Custom monoid of natural numbers.";
	}
}
