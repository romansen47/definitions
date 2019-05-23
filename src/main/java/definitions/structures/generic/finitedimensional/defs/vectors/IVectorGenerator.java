package definitions.structures.generic.finitedimensional.defs.vectors;

public interface IVectorGenerator {

	default FiniteVector getFiniteVector(int dim) {
		return new Tuple(dim);
	}

}
