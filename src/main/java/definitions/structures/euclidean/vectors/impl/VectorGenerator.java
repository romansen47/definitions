package definitions.structures.euclidean.vectors.impl;

import definitions.structures.euclidean.vectors.FiniteVector;
import definitions.structures.euclidean.vectors.IVectorGenerator;

public class VectorGenerator implements IVectorGenerator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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