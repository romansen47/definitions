package definitions.structures.abstr.algebra.groups.impl;

import org.springframework.stereotype.Component;

import definitions.structures.abstr.algebra.fields.FinitePrimeField;

@Component
public class BinaryField extends FinitePrimeField {

	private static final long serialVersionUID = 1L;
	private static BinaryField instance;

	public static BinaryField getInstance() {
		return instance;
	}

	public static void setInstance(final BinaryField binaryField) {
		BinaryField.instance = binaryField;
	}

	public BinaryField() {
		super(2);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "the binary field";
	}
}
