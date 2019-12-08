package definitions.structures.abstr.groups.impl;

import org.springframework.stereotype.Component;

import definitions.structures.abstr.fields.impl.FinitePrimeField;

@Component
public class BinaryField extends FinitePrimeField {

	private static final long serialVersionUID = 1L;
	public static BinaryField instance;

	public BinaryField() {
		super(2);
	}

	public static BinaryField getInstance() {
		if (instance == null) {
			instance = new BinaryField();
		}
		return instance;
	} 
}
