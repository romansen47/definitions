package definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces.functions;


import java.util.Map;

import definitions.structures.generic.finitedimensional.defs.vectors.IFiniteVector;

public class Constant extends Function {

	final double value;
	
	public Constant(double[] coordinates,double value) throws Throwable {
		super(coordinates);
		this.value=value;
	}
	
	@Override
	public double value(double input) {
		return value;
	}

}
