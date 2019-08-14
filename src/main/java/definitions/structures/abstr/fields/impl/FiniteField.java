package definitions.structures.abstr.fields.impl;

import definitions.structures.abstr.fields.Field;

public interface FiniteField extends Field {

	int getCharacteristic();

	FiniteField getPrimeField();

}
