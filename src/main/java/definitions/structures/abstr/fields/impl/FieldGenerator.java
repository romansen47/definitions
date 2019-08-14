package definitions.structures.abstr.fields.impl;

import definitions.structures.abstr.fields.IFieldGenerator;

public class FieldGenerator implements IFieldGenerator {

	private static IFieldGenerator instance;

	public IFieldGenerator getInstance() {
		if (instance == null) {
			instance = new FieldGenerator();
		}
		return instance;
	}

	private FieldGenerator() {
	}

}
