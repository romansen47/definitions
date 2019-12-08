/**
 * 
 */
package definitions.structures.abstr.fields.impl;

import definitions.structures.abstr.fields.Field;

/**
 * @author RoManski
 *
 */
public interface PrimeField extends Field {
	/**
	 * {@inheritDoc}
	 */
	@Override
	default int getCharacteristic() {
		return getOrder();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default PrimeField getPrimeField() {
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Field getField() {
		return this;
	}

}
