package definitions.structures.abstr.algebra.fields;
 
import definitions.structures.abstr.algebra.rings.FiniteRing;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

/**
 * @author RoManski
 *
 */
public interface FiniteField extends Field, FiniteRing {
 
	/**
	 * {@inheritDoc}
	 */
	@Override
	default FieldElement operation(final Element first, final Element second) {
		return (FieldElement) Field.super.operation(first, second);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default FieldElement product(final Vector vec1, final Vector vec2) {
		return (FieldElement) this.getMuliplicativeMonoid().operation(vec1, vec2);
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

	@Override
	FieldElement getNeutralElement();

	@Override
	default boolean isUnit(Element element) { 
		return Field.super.isUnit(element);
	}

	@Override
	default Element getInverseElement(Element element) {
		return FiniteRing.super.getInverseElement(element);
	}

}
