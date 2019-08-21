/**
 * 
 */
package definitions.structures.abstr.fields.impl;

import definitions.structures.abstr.fields.Field;

/**
 * @author RoManski
 *
 */
public interface PrimeField extends FiniteField {

	@Override
	default int getCharacteristic() {
		return getOrder();
	}

	@Override
	default PrimeField getPrimeField() {
		return this;
	}

	@Override
	default Field getField() {
		return this;
	}

}
