package definitions.structures.abstr.fields.impl;

import definitions.structures.abstr.fields.Field;

/**
 * @author RoManski
 *
 */
public interface FiniteField extends Field {

	int getCharacteristic();

	PrimeField getPrimeField();

	@Override
	default String toXml(){
		String xmlString="<FiniteField>";
		xmlString+="<characteristic>"+getCharacteristic()+"</characteristic>";
		if (getPrimeField()==this) {
			xmlString+="<isPrimeField>yes</isPrimeField>";
		}
		else {
			xmlString+="<primeField>"+getPrimeField().toXml()+"</primeField>";
		}
		
		return xmlString;
	};
	
}
