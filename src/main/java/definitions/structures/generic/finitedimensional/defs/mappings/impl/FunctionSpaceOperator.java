package definitions.structures.generic.finitedimensional.defs.mappings.impl;

import java.util.Map;

import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalInjectiveLinearMapping;
import definitions.structures.generic.finitedimensional.defs.spaces.CoordinateSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.IFiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;

public class FunctionSpaceOperator implements IFiniteDimensionalInjectiveLinearMapping {

	final IFiniteDimensionalFunctionSpace source;
	final IFiniteDimensionalFunctionSpace target;
	private double[][] genericMatrix;
	private Map<Vector, Map<Vector, Double>> linearity;

	public FunctionSpaceOperator(IFiniteDimensionalFunctionSpace source, IFiniteDimensionalFunctionSpace target,
			Map<Vector, Map<Vector, Double>> matrix) throws Throwable {
		this.source = source;
		this.target = target;
		this.linearity = matrix;
		genericMatrix = new double[target.dim()][source.dim()];
		int i = 0;
		for (final Vector vec1 : source.genericBaseToList()) {
			int j = 0;
			for (final Vector vec2 : target.genericBaseToList()) {
				genericMatrix[j][i] = target.product(vec1, vec2);
				j++;
			}
			i++;
		}
	}

	@Override
	public FiniteVector solve(Vector image) throws Throwable {
		return null;
	}

	@Override
	public CoordinateSpace getSource() {
		return source;
	}

	@Override
	public VectorSpace getTarget() {
		return target;
	}

	@Override
	public Map<Vector, Map<Vector, Double>> getLinearity() {
		return linearity;
	}

	@Override
	public double[][] getGenericMatrix() throws Throwable {
		return genericMatrix;
	}

}
