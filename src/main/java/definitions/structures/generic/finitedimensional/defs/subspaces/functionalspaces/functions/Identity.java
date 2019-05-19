package definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces.functions;

import definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces.Function;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces.IFunction;

public class Identity extends Function implements IFunction {

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
