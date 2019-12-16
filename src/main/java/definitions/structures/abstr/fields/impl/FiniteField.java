package definitions.structures.abstr.fields.impl;

import definitions.Proceed;
import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

/**
 * @author RoManski
 *
 */
public interface FiniteField extends Field {

	@Override 
	int getCharacteristic();

	@Override 
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
	default Vector product(Vector vec1, Vector vec2) {
		return (Vector) getMuliplicativeMonoid().operation(vec1, vec2);
	}

}
