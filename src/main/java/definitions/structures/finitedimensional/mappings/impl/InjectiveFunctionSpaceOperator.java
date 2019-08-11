package definitions.structures.finitedimensional.mappings.impl;

import java.util.Map;

import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.finitedimensional.functionspaces.EuclideanFunctionSpace;
import definitions.structures.finitedimensional.mappings.FiniteDimensionalEmbedding;

public class InjectiveFunctionSpaceOperator extends FiniteDimensionalLinearMapping
		implements FiniteDimensionalEmbedding {

	/**
	 * Constructor
	 * 
	 * @param source the source vector space.
	 * @param target the target vector space.
	 * @param matrix the matrix.
	 */
	public InjectiveFunctionSpaceOperator(EuclideanFunctionSpace source, EuclideanFunctionSpace target,
			Map<Vector, Map<Vector, Scalar>> matrix) {
		super(source, target, matrix);
	}

}
