package definitions.structures.abstr.fields.impl;

import definitions.structures.abstr.fields.Field;

/**
 * @author RoManski
 *
 */
public interface FiniteField extends Field {

	int getCharacteristic();

	PrimeField getPrimeField();

}
