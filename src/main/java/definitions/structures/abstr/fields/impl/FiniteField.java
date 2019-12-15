package definitions.structures.abstr.fields.impl;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import settings.annotations.Proceed;

/**
 * @author RoManski
 *
 */
public interface FiniteField extends Field {

	@Override
	@Proceed
	int getCharacteristic();

	@Override
	@Proceed
	PrimeField getPrimeField();

	@Override
	default String toXml() {
		String xmlString = "<FiniteField>";
		xmlString += "<characteristic>" + getCharacteristic() + "</characteristic>";
		if (getPrimeField() == this) {
			xmlString += "<isPrimeField>yes</isPrimeField>";
		} else {
			xmlString += "<primeField>" + getPrimeField().toXml() + "</primeField>";
		}

		return xmlString;
	};

	@Override
	@Proceed
	default Vector product(Vector vec1, Vector vec2) {
		return (Vector) getMuliplicativeMonoid().operation(vec1, vec2);
	}

}
