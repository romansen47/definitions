package definitions.structures.generic.finitedimensional.defs.vectors;

public interface IVectorGenerator {

	default IFiniteVector getFiniteVector(int dim) {
		return new FiniteVector(dim);
	}

}
