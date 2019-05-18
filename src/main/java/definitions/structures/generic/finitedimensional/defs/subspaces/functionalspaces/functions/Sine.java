package definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces.functions;

import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.IFiniteVector;

public class Sine extends FiniteVector implements IFunction {

	public Sine(double[] coordinates) throws Throwable {
		super(coordinates);
	}

	public double value(double input) throws Throwable {
		return Math.sin(input);
	}

	@Override
	public String toString() {
		return "sine function";
	}
}
