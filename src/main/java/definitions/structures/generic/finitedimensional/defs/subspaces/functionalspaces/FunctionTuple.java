package definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces;

import java.util.Map;

import definitions.structures.generic.finitedimensional.defs.vectors.Tuple;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;

public class FunctionTuple extends Tuple implements Function {

	public FunctionTuple(Map<FiniteVector, Double> coordinates) throws Throwable {
		super(coordinates);
	}

	public FunctionTuple(double[] coordinates) throws Throwable {
		super(coordinates);
	}

}
