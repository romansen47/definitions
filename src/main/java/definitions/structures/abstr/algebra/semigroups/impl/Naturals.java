package definitions.structures.abstr.algebra.semigroups.impl;

import java.util.HashMap;
import java.util.Map;

import definitions.settings.XmlPrintable;
import definitions.structures.abstr.algebra.groups.impl.Int;
import definitions.structures.abstr.algebra.monoids.Monoid;
import definitions.structures.abstr.algebra.monoids.MonoidElement;
import definitions.structures.abstr.algebra.monoids.OrderedMonoid;
import definitions.structures.abstr.algebra.monoids.OrderedSemiGroup;
import definitions.structures.abstr.algebra.rings.SemiRing;
import definitions.structures.abstr.algebra.semigroups.DiscreetSemiGroup;
import definitions.structures.abstr.algebra.semigroups.SemiGroupElement;
import definitions.structures.abstr.vectorspaces.RingElement;

public class Naturals implements SemiRing, OrderedSemiGroup, DiscreetSemiGroup, XmlPrintable {

	private static final long serialVersionUID = 1L;
	private static OrderedSemiGroup instance;

	public static OrderedSemiGroup getInstance() {
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
	public Monoid getMuliplicativeMonoid() {
		return new Monoid() {

			private static final long serialVersionUID = 5823541822558827011L;

			@Override
			public NaturalNumber getNeutralElement() {
				return Naturals.this.get(1);
			}

			@Override
			public Integer getOrder() {
				return null;
			}

			@Override
			public NaturalNumber operation(final SemiGroupElement first, final SemiGroupElement second) {
				return new NaturalNumber(((Int) first).getRepresentant() * ((Int) second).getRepresentant());
			}

		};
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
	public NaturalNumber operation(final SemiGroupElement first, final SemiGroupElement second) {
		return this.get(((NaturalNumber) first).getRepresentant() + ((NaturalNumber) second).getRepresentant());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toXml() {
		return "Custom monoid of natural numbers.";
	}

	private Map<NaturalNumber, NaturalNumber> primes = new HashMap<>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isUnit(final RingElement element) {
		return element.equals(this.get(-1)) || element.equals(this.get(1));
	}
}
