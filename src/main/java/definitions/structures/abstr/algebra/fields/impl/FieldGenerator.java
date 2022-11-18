package definitions.structures.abstr.algebra.fields.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import definitions.Unweavable;
import definitions.structures.abstr.algebra.fields.IFieldGenerator;

@Component
public class FieldGenerator implements IFieldGenerator, Unweavable {

	@Autowired
	private RealLine realLine;

	@Autowired
	private ComplexPlane complexPlane;

	@Override
	public RealLine getRealLine() {
		return this.realLine;
	}

	public void setRealLine(final RealLine realLine) {
		this.realLine = realLine;
	}

	@Override
	public ComplexPlane getComplexPlane() {
		return complexPlane;
	}

	public void setComplexPlane(ComplexPlane complexPlane) {
		this.complexPlane = complexPlane;
	}

}
