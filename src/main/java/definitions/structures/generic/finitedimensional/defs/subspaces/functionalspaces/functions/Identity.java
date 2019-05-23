package definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces.functions;

import definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces.FunctionTuple;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces.Function;

public class Identity extends FunctionTuple implements Function {

	public Identity(double[] coordinates) throws Throwable {
		super(coordinates);
	}

	@Override
	public double value(double input) throws Throwable {
		return input;
	}

	@Override
	public String toString() {
		return "the identity";
	}

}
