package definitions.structures.abstr.fields.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import definitions.structures.abstr.fields.IFieldGenerator;

@Component
public class FieldGenerator implements IFieldGenerator {

	private static IFieldGenerator instance;

	@Autowired
	private RealLine realLine;

	public IFieldGenerator getInstance() {
		return instance;
	}

	public FieldGenerator() {
	}

	public RealLine getRealLine() {
		return realLine;
	}

	public void setRealLine(RealLine realLine) {
		this.realLine = realLine;
		RealLine.setInstance(realLine);
		ComplexPlane.setRealLine(realLine);
		QuaternionSpace.setInstance(realLine);
	}

	public static void setInstance(FieldGenerator fieldGenerator) {
		instance = fieldGenerator;
		((FieldGenerator) instance).setRealLine(fieldGenerator.getRealLine());
	}

}
