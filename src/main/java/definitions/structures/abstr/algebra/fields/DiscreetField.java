package definitions.structures.abstr.algebra.fields;

import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.groups.DiscreetGroup;
import definitions.structures.abstr.algebra.groups.DiscreetRing;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

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

		final DiscreetGroup multiplicativeGroup = new DiscreetGroup() {

			@Override
			public Element getInverseElement(final Element element) {
				return DiscreetField.this.getMultiplicativeInverseElement((Scalar) element);
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
			public Element get(Double representant) {
				Vector ans=getZero();
				for (int i=0;i<representant;i++) {
					ans=addition(ans,getOne());
				}
				return ans;
			}
		};

		return multiplicativeGroup;
	}
	
}
