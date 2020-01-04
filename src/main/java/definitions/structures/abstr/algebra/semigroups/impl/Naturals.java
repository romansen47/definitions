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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NaturalNumber get(final Integer representant) {
		return new NaturalNumber(representant);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NaturalNumber getNeutralElement() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer getOrder() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isHigher(final MonoidElement smallerOne, final MonoidElement biggerOne) {
		return ((NaturalNumber) smallerOne).getRepresentant() < ((NaturalNumber) biggerOne).getRepresentant();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NaturalNumber operation(final MonoidElement first, final MonoidElement second) {
		return this.get(((NaturalNumber) first).getRepresentant() + ((NaturalNumber) second).getRepresentant());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toXml() {
		return "Custom monoid of natural numbers.";
	}
}
