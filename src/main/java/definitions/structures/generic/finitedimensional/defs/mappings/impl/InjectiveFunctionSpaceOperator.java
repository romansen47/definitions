package definitions.structures.generic.finitedimensional.defs.mappings.impl;

import java.util.Map;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.mappings.FiniteDimensionalEmbedding;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.EuclideanFunctionSpace;

public class InjectiveFunctionSpaceOperator extends FiniteDimensionalLinearMapping
		implements FiniteDimensionalEmbedding {

	public InjectiveFunctionSpaceOperator(EuclideanFunctionSpace source, EuclideanFunctionSpace target,
			Map<Vector, Map<Vector, Double>> matrix) {
		super(source, target, matrix);
	}

}
