package definitions.structures.generic.finitedimensional.defs.mappings.impl;

import java.util.Map;

import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalInjectiveLinearMapping;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.IFiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;

public class FunctionSpaceOperator implements IFiniteDimensionalInjectiveLinearMapping {

	final IFiniteDimensionalFunctionSpace source;
	final IFiniteDimensionalFunctionSpace target;
	private final double[][] genericMatrix;
	private final Map<Vector, Map<Vector, Double>> linearity;

	public FunctionSpaceOperator(final IFiniteDimensionalFunctionSpace source,
			final IFiniteDimensionalFunctionSpace target, final Map<Vector, Map<Vector, Double>> matrix)
			throws Throwable {
		this.source = source;
		this.target = target;
		this.linearity = matrix;
		this.genericMatrix = new double[target.dim()][source.dim()];
		int i = 0;
		for (final Vector vec1 : source.genericBaseToList()) {
			int j = 0;
			for (final Vector vec2 : target.genericBaseToList()) {
				this.genericMatrix[j][i] = target.product(vec1, vec2);
				j++;
			}
			i++;
		}
	}

	@Override
	public FiniteVector solve(final Vector image) throws Throwable {
		return null;
	}

	@Override
	public EuclideanSpace getSource() {
		return this.source;
	}

	@Override
	public VectorSpace getTarget() {
		return this.target;
	}

	@Override
	public Map<Vector, Map<Vector, Double>> getLinearity() {
		return this.linearity;
	}

	@Override
	public double[][] getGenericMatrix() throws Throwable {
		return this.genericMatrix;
	}

}
