package definitions.structures.abstr.algebra.fields.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import definitions.Unweavable;
import definitions.structures.abstr.algebra.fields.IFieldGenerator;

@Service
public class FieldGenerator implements IFieldGenerator, Unweavable {

	@Autowired
	private RealLine realLine;

	@Override
	public RealLine getRealLine() {
		return this.realLine;
	}

	public void setRealLine(final RealLine realLine) {
		this.realLine = realLine;
	}

}
