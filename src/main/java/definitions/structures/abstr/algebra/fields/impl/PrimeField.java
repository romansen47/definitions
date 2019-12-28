/**
 * 
 */
package definitions.structures.abstr.algebra.fields.impl;

import definitions.structures.abstr.algebra.fields.Field;

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
		return this.getOrder();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Field getField() {
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default PrimeField getPrimeField() {
		return this;
	}

}
