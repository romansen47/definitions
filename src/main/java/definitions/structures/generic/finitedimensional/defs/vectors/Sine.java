package definitions.structures.generic.finitedimensional.defs.vectors;

import definitions.structures.generic.finitedimensional.defs.vectors.impl.FunctionTuple;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.Tuple;

public class Sine extends FunctionTuple implements Function {

	final double norm=1/Math.sqrt(Math.PI);
	
	public Sine(double[] coordinates) throws Throwable {
		super(coordinates);
	}

	@Override
	public double value(double input) throws Throwable {
		return norm*Math.sin(input);
	}

	@Override
	public String toString() {
		return "renormed sine";
	}

}
