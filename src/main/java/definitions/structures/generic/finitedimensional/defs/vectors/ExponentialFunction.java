package definitions.structures.generic.finitedimensional.defs.vectors;

import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.FunctionTuple;

public class ExponentialFunction extends FunctionTuple implements Function {

	public ExponentialFunction(double[] coordinates) throws Throwable {
		super(coordinates);
	}

	@Override
	public double value(double input) {
		return Math.exp(input);
	}
	
}
