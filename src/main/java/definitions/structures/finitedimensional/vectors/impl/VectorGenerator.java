package definitions.structures.finitedimensional.vectors.impl;

import definitions.structures.finitedimensional.vectors.FiniteVector;
import definitions.structures.finitedimensional.vectors.IVectorGenerator;

public class VectorGenerator implements IVectorGenerator {

	private static VectorGenerator generator = null;

	public static IVectorGenerator getInstance() {
		if (generator == null) {
			generator = new VectorGenerator();
		}
		return generator;
	}

	private VectorGenerator() {
	}

	@Override
	public FiniteVector getFiniteVector(final int dim) {
		return new Tuple(dim);
	}

}