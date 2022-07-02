package definitions.structures.abstr.algebra.fields;

import definitions.structures.abstr.algebra.groups.DiscreetGroup;
import definitions.structures.abstr.algebra.rings.FiniteRing;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

/**
 * @author ro
 *
 *         A finite field is a field with finite amount of elements
 *
 */
public interface FiniteField extends DiscreetField, FiniteRing {

	/**
	 * {@inheritDoc}
	 */
	@Override
	FieldElement operation(final Element first, final Element second);

	/**
	 * {@inheritDoc}
	 */
	@Override
	default FieldElement product(final Vector vec1, final Vector vec2) {
		Element ans = DiscreetField.super.product(vec1, vec2);
		if (ans == null) {
			ans = this.getMuliplicativeMonoid().operation(vec1, vec2);
		}
		return (FieldElement) ans;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default String toXml() {
		String xmlString = "<FiniteField>";
		xmlString += "<characteristic>" + this.getCharacteristic() + "</characteristic>";
		if (this.getPrimeField() == this) {
			xmlString += "<isPrimeField>yes</isPrimeField>";
		} else {
			xmlString += "<primeField>" + this.getPrimeField().toXml() + "</primeField>";
		}
		return xmlString;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	FieldElement getNeutralElement();

	/**
	 * {@inheritDoc}
	 */
	@Override
	default boolean isUnit(Element element) {
		return DiscreetField.super.isUnit(element);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default FieldElement getInverseElement(Element element) {
		return (FieldElement) FiniteRing.super.getInverseElement(element);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default DiscreetGroup getMuliplicativeMonoid() {
		return DiscreetField.super.getMuliplicativeMonoid();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Integer getOrder() {
		return FiniteRing.super.getOrder();
	}

}
