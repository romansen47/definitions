package definitions.structures.generic.finitedimensional.defs.vectors;

import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.IFiniteVector;

public interface IVectorGenerator {

	default IFiniteVector getFiniteVector(int dim) {
		return new FiniteVector(dim);
	}

}
