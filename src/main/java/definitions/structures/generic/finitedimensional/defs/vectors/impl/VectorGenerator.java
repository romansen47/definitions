package definitions.structures.generic.finitedimensional.defs.vectors.impl;

import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.IVectorGenerator;

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
	public FiniteVector getFiniteVector(int dim) {
		return new Tuple(dim);
	}

}