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
		xmlString+="<characteristic "+getCharacteristic()+"/>";
		if (getPrimeField()==this) {
			xmlString+="<isPrimeField/>";
		}
		else {
			xmlString+="<primeField "+getPrimeField().toXml();
		}
		
		return xmlString;
	};
	
}
