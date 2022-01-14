package definitions.structures.abstr.algebra.fields.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import definitions.Unweavable;
import definitions.structures.abstr.algebra.fields.IFieldGenerator;
import definitions.structures.euclidean.vectorspaces.impl.FiniteDimensionalVectorSpace;

@Service
public class FieldGenerator implements IFieldGenerator, Unweavable {

	private static IFieldGenerator instance;

	public static IFieldGenerator getInstance() {
		return FieldGenerator.instance;
	}

	public static IFieldGenerator getInstance(final FieldGenerator fieldGenerator) {
		return FieldGenerator.instance;
	}

	public static void setInstance(final FieldGenerator fieldGenerator) {
		FieldGenerator.instance = fieldGenerator;
		((FieldGenerator) FieldGenerator.instance).setRealLine(fieldGenerator.getRealLine());
	}

	@Autowired
	private RealLine realLine;

	public FieldGenerator() {
	}

	@Override
	public RealLine getRealLine() {
		return realLine;
	}

	public void setRealLine(final RealLine realLine) {
		this.realLine = realLine;
		RealLine.setInstance(realLine);
		ComplexPlane.setRealLine(realLine);
		((FiniteDimensionalVectorSpace) QuaternionSpace.getInstance()).setField(realLine);
	}

}
