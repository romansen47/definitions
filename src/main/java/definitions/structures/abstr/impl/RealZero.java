package definitions.structures.abstr.impl;

import definitions.structures.finitedimensional.real.vectors.Real;

public final class RealZero extends Real{


	private static Real zero;

	public static Real getZero() {
		if (zero == null) {
			zero = new RealZero();
		}
		return zero;
	};

	private RealZero() {
		super(0.);
	}

}
