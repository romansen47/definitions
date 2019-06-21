package definitions.structures.generic.finitedimensional.defs.mappings.impl;

import java.util.Map;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalInjectiveLinearMapping;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.IFiniteDimensionalFunctionSpace;

public class InjectiveFunctionSpaceOperator extends FiniteDimensionalLinearMapping implements IFiniteDimensionalInjectiveLinearMapping {

	public InjectiveFunctionSpaceOperator(IFiniteDimensionalFunctionSpace source,
			IFiniteDimensionalFunctionSpace target, Map<Vector, Map<Vector, Double>> matrix) throws Throwable {
		super(source, target, matrix);
	}

}
