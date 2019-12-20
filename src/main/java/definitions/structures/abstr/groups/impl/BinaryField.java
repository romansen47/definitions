package definitions.structures.abstr.groups.impl;

import org.springframework.stereotype.Component;
 
import definitions.structures.abstr.fields.impl.FinitePrimeField;

@Component
public class BinaryField extends FinitePrimeField{

	private static final long serialVersionUID = 1L;
	private static BinaryField instance;

	public BinaryField() {
		super(2);
	}

	public static BinaryField getInstance() {
		return instance;
	}

	public static void setInstance(BinaryField binaryField) {
		BinaryField.instance=binaryField;		
	}
}
