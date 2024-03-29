package definitions.structures.abstr.algebra.fields;

import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.groups.DiscreetGroup;
import definitions.structures.abstr.algebra.groups.DiscreetRing;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import exceptions.DevisionByZeroException;

/**
 * a field that also is a discreet ring
 *
 * @author ro
 */
public interface DiscreetField extends DiscreetRing, Field {

	/**
	 * the operation on the monoid.
	 *
	 * @param first  first monoid element
	 * @param second second monoid element
	 * @return product of both of them
	 */
	@Override
	default FieldElement operation(Element first, Element second) {
		return Field.super.operation(first, second);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default DiscreetGroup getMuliplicativeMonoid() {

		final Vector newOne = this.getOne();
		final Integer newOrder = this.getOrder();

		return new DiscreetGroup() {

			@Override
			public Element getInverseElement(final Element element) {
				try {
					return DiscreetField.this.getMultiplicativeInverseElement((Scalar) element);
				} catch (DevisionByZeroException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			@Override
			public Element getNeutralElement() {
				return newOne;
			}

			@Override
			public Integer getOrder() {
				if (newOrder == null) {
					return newOrder;
				}
				return newOrder - 1;
			}

			@Override
			public Element operation(final Element first, final Element second) {
				return DiscreetField.this.product((Scalar) first, (Scalar) second);
			}

			@Override
			public String toString() {
				return "Multiplicative group of " + DiscreetField.this.getField().toString();
			}

			@Override
			public Element get(Number representant) {
				Vector ans = DiscreetField.this.getZero();
				for (int i = 0; i < representant.intValue(); i++) {
					ans = DiscreetField.this.addition(ans, DiscreetField.this.getOne());
				}
				return ans;
			}
		};

	}

}
