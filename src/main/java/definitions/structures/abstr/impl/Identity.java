package definitions.structures.abstr.impl;

import definitions.structures.abstr.Endomorphism;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;

public abstract class Identity extends LinearMapping implements Endomorphism {

	public Identity(VectorSpace source) {
		super(source, source);
	}

	@Override
	public Vector get(Vector vec) {
		return vec;
	}

}
