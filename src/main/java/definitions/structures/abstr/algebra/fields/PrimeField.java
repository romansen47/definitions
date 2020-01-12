/**
 * 
 */
package definitions.structures.abstr.algebra.fields;

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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	default Integer getDim() {
		return 1;
	}

}
