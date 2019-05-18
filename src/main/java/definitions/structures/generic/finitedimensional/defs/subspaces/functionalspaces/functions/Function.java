package definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces.functions;

import java.util.Map;

import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.IFiniteVector;

public class Function extends FiniteVector implements IFunction {

	public Function(Map<IFiniteVector, Double> coordinates) throws Throwable {
		super(coordinates);
	}

	public Function(double[] coordinates) throws Throwable {
		super(coordinates);
	}

}
